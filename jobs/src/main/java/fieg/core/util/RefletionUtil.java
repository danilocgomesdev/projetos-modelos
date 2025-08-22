package fieg.core.util;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class RefletionUtil {

    private static final String MENSAGEM_ERRO_SETTER = "Nao foi possivel mapear automaticamente o objeto. Setter: ";

    private static final Map<String, Map<String, Method>> methodCache = new HashMap<>();

    public static <T> T mapTupleToObject(Tuple tuple, T object) throws RuntimeException {
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
                throw new RuntimeException(MENSAGEM_ERRO_SETTER + className + "." + setterName + " nao existe ou tem tipo diferente", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(MENSAGEM_ERRO_SETTER + className + "." + setterName + " tem tipo de argumento diferente da propriedade", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(MENSAGEM_ERRO_SETTER + className + "." + setterName + " nao e publico", e);
            }
        }

        return object;
    }

    public static <T> List<T> mapTuplesToObjects(Collection<Tuple> tuples, Class<T> clazz) throws RuntimeException {
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
                mapped.add(mapTupleToObject(tuple, newObj));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                // Nunca deve ocorrer
                throw new RuntimeException(e);
            }
        }

        return mapped;
    }

    private static String getSetterName(String propertyName) {
        return "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }

    private static Method findMethodByName(Class<?> clazz, String setterName) throws NoSuchMethodException {
        methodCache.putIfAbsent(clazz.getName(), new HashMap<>());
        Map<String, Method> methodMap = methodCache.get(clazz.getName());

        if (methodMap.containsKey(setterName)) {
            return methodMap.get(setterName);
        }

        Method method = Arrays.stream(clazz.getMethods())
                .filter(it -> it.getName().equals(setterName))
                .findFirst()
                .orElseThrow(NoSuchMethodException::new);

        methodMap.put(setterName, method);

        return method;
    }

    private static void invokeSetter(Method setter, Object object, Object setee) throws InvocationTargetException, IllegalAccessException {
        if (setter.getParameterTypes().length != 1) {
            throw new IllegalArgumentException("Metodo %s deve ter somente um argumento!".formatted(setter.getName()));
        }

        // TODO transformar objetos transformáveis aqui (como Date para LocalDate)
//        Class<?> setterType = setter.getParameterTypes()[0];
//
//        if (setterType != setee.getClass()) {
//        }

        setter.invoke(object, setee);
    }

}
