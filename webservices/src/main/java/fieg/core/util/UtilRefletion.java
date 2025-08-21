package fieg.core.util;

import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;

public class UtilRefletion {

    private static final Logger logger = LoggerFactory.getLogger(UtilRefletion.class);

    private static final String MENSAGEM_ERRO_SETTER = "Nao foi possivel mapear automaticamente o objeto. Setter: ";

    private static final Map<String, Map<String, Method>> METHOD_CACHE = new HashMap<>();

    public static <T> T mapTupleToObject(Tuple tuple, T object) throws RuntimeException {
        return mapTupleToObject(tuple, object, false);
    }

    public static <T> T mapTupleToObject(Tuple tuple, T object, boolean ignorarDesconhecidos) throws RuntimeException {
        Class<?> clazz = object.getClass();
        String className = clazz.getSimpleName();

        for (TupleElement<?> tupleElement : tuple.getElements()) {
            String fieldName = tupleElement.getAlias();
            Object valueInTuple = tuple.get(fieldName);

            if (valueInTuple == null) {
                continue;
            }

            String setterName = getSetterName(fieldName);

            try {
                Method setter = findMethodByName(clazz, setterName);
                invokeSetter(setter, object, valueInTuple);
            } catch (NoSuchMethodException e) {
                if (!ignorarDesconhecidos) {
                    throw new RuntimeException(MENSAGEM_ERRO_SETTER + className + "::" + setterName + " nao existe", e);
                }
            } catch (InvocationTargetException | IllegalArgumentException e) {
                throw new RuntimeException(MENSAGEM_ERRO_SETTER + className + "::" + setterName
                        + " tem tipo de argumento diferente da propriedade: " + valueInTuple.getClass(), e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(MENSAGEM_ERRO_SETTER + className + "::" + setterName + " nao e publico", e);
            }
        }

        return object;
    }

    public static <T> List<T> mapTuplesToObjects(Collection<Tuple> tuples, Class<T> clazz) throws RuntimeException {
        return mapTuplesToObjects(tuples, clazz, false);
    }

    public static <T> List<T> mapTuplesToObjects(Collection<Tuple> tuples, Class<T> clazz, boolean ignorarDesconhecidos) throws RuntimeException {
        long start = System.currentTimeMillis();
        List<T> mapped = new ArrayList<>();
        Constructor<T> constructor;
        try {
            constructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Classe " + clazz.getSimpleName() + " nao tem um construtor publico sem argumentos", e);
        }

        for (Tuple tuple : tuples) {
            try {
                T newObj = constructor.newInstance();
                mapped.add(mapTupleToObject(tuple, newObj, ignorarDesconhecidos));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        long tempoGasto = System.currentTimeMillis() - start;
        if (tempoGasto > 20) {
            logger.debug("Tempo para mapear %d entidades: %d ms".formatted(tuples.size(), tempoGasto));
        }
        return mapped;
    }

    private static String getSetterName(String propertyName) {
        return "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }

    private static Method findMethodByName(Class<?> clazz, String setterName) throws NoSuchMethodException {
        METHOD_CACHE.putIfAbsent(clazz.getName(), new HashMap<>());
        Map<String, Method> methodMap = METHOD_CACHE.get(clazz.getName());

        if (methodMap.containsKey(setterName)) {
            Method method = methodMap.get(setterName);
            if (method == null) {
                throw new NoSuchMethodException(clazz.getName() + "::" + setterName);
            }
            return method;
        }

        Method method = Arrays.stream(clazz.getMethods())
                .filter(it -> it.getName().equals(setterName))
                .findFirst()
                .orElse(null);

        methodMap.put(setterName, method);

        if (method == null) {
            throw new NoSuchMethodException(clazz.getName() + "::" + setterName);
        }

        return method;
    }

    private static void invokeSetter(Method setter, Object object, Object value) throws InvocationTargetException, IllegalAccessException {
        if (setter.getParameterTypes().length != 1) {
            throw new IllegalArgumentException("Metodo %s deve ter um e somente um argumento!".formatted(setter.getName()));
        }

        Class<?> setterType = setter.getParameterTypes()[0];

        if (setterType != value.getClass() && !setterType.isAssignableFrom(value.getClass())) {
            value = converterTipo(value, setterType);
        }

        setter.invoke(object, value);
    }

    private static ZonedDateTime dateToZonedDateTime(Date date) {
        return new Date(date.getTime()).toInstant().atZone(ZoneId.systemDefault());
    }

    private static Object converterTipo(Object value, Class<?> classeDesejada) {
        return switch (value) {
            case Timestamp timestamp when classeDesejada.isAssignableFrom(LocalDateTime.class) ->
                    timestamp.toLocalDateTime();

            case Timestamp timestamp when classeDesejada.isAssignableFrom(LocalDate.class) ->
                    timestamp.toLocalDateTime().toLocalDate();

            case Date date when classeDesejada.isAssignableFrom(LocalDateTime.class) ->
                    dateToZonedDateTime(date).toLocalDateTime();

            case Date date when classeDesejada.isAssignableFrom(LocalDate.class) ->
                    dateToZonedDateTime(date).toLocalDate();

            case String string when classeDesejada.isEnum() ->
                    Enum.valueOf((Class<? extends Enum>) classeDesejada, string);

            case Long longValue when classeDesejada == Integer.class -> longValue.intValue();

            // Tratando casos de conversão de nativo (int) para classe (Integer)
            case Boolean bool when classeDesejada == boolean.class -> bool;
            case Integer integer when classeDesejada == int.class -> integer;
            case Long longValue when classeDesejada == long.class -> longValue;
            case Character character when classeDesejada == char.class -> character;

            default -> throw new IllegalArgumentException(
                    "Nao foi possível converter o valor %s de classe %s para a classe %s".formatted(
                            value,
                            value.getClass(),
                            classeDesejada
                    )
            );
        };
    }

}
