package fieg.core.util;

import fieg.core.interfaces.EnumBanco;
import fieg.core.interfaces.Mapper;
import fieg.modulos.boleto.enums.SituacaoBoleto;
import fieg.modulos.cobrancaagrupada.enums.SituacaoCobrancaAgrupada;
import fieg.modulos.cobrancacliente.enums.SituacaoCobrancaCliente;
import fieg.modulos.interfacecobranca.enums.StatusInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class UtilMapperTupleConverter {

    // Método genérico para criar o mapeador
    public static <T extends Tuple, D> Mapper<T, D> criarMapper(Class<T> tupleClass, Class<D> dtoClass) {
        return tuple -> {
            D dtoInstance;
            try {
                dtoInstance = dtoClass.getDeclaredConstructor().newInstance(); // Criando uma nova instância do DTO

                // Iterando sobre os campos do DTO
                List<Field> fields = Arrays.asList(dtoClass.getDeclaredFields());

                for (Field field : fields) {
                    // Tornando os campos acessíveis, caso sejam privados
                    field.setAccessible(true);

                    // Removendo a primeira letra em maiúsculo para combinar com o nome da coluna
                    String fieldName = field.getName();
                    fieldName = decapitalize(fieldName);

                    // Acessando o valor da Tuple pela chave do campo
                    Object fieldValue = tuple.get(fieldName);

                    // Convertendo o valor conforme necessário
                    if (fieldValue != null) {
                        fieldValue = convertValue(fieldValue, field.getType()); // Convertendo para o tipo correto
                        field.set(dtoInstance, fieldValue); // Atribuindo valor diretamente ao campo
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao mapear os campos da Tuple para o DTO", e);
            }

            return dtoInstance;
        };
    }

    public static <T extends Tuple, D> Mapper<T, D> criarMapperNovo(Class<T> tupleClass, Class<D> dtoClass) {
        return tuple -> {
            D dtoInstance;
            try {
                dtoInstance = dtoClass.getDeclaredConstructor().newInstance();

                // Map com os campos do DTO por nome
                Map<String, Field> dtoFieldsMap = Arrays.stream(dtoClass.getDeclaredFields())
                        .collect(Collectors.toMap(Field::getName, f -> {
                            f.setAccessible(true);
                            return f;
                        }));

                // Iterando sobre os aliases (nomes das colunas) da Tuple
                for (TupleElement<?> element : tuple.getElements()) {
                    String alias = element.getAlias();
                    if (alias == null) continue;

                    // Se o DTO tiver um campo com esse nome
                    Field dtoField = dtoFieldsMap.get(alias);
                    if (dtoField != null) {
                        Object value = tuple.get(alias);

                        if (value != null) {
                            Object convertedValue = convertValue(value, dtoField.getType());
                            dtoField.set(dtoInstance, convertedValue);
                        } else if (dtoField.getType().equals(String.class)) {
                            dtoField.set(dtoInstance, "");
                        }
                    }
                    // Se não tiver campo correspondente no DTO, simplesmente ignora
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao mapear os campos da Tuple para o DTO", e);
            }

            return dtoInstance;
        };
    }

    // Método para capitalizar a primeira letra de uma string
    private static String decapitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    private static Object convertValue(Object value, Class<?> targetType) {
        if (value == null) {
            return null;
        }

        // Conversão de String para LocalDate
        if (targetType.equals(LocalDate.class) && value instanceof String) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse((String) value, formatter);
        }

        // Conversão de java.sql.Date para LocalDate
        if (targetType.equals(LocalDate.class) && value instanceof java.sql.Date) {
            return ((java.sql.Date) value).toLocalDate();
        }

        // Conversão de java.sql.Timestamp para LocalDate
        if (targetType.equals(LocalDate.class) && value instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) value).toLocalDateTime().toLocalDate();
        }

        // Conversão de String para LocalDateTime
        if (targetType.equals(LocalDateTime.class) && value instanceof String) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse((String) value, formatter);
        }

        // Conversão de Timestamp para LocalDateTime
        if (targetType.equals(LocalDateTime.class) && value instanceof Timestamp) {
            return ((Timestamp) value).toLocalDateTime();
        }

        // Conversão de Double para BigDecimal
        if (targetType.equals(BigDecimal.class) && value instanceof Double) {
            return BigDecimal.valueOf((Double) value);
        }

        // Conversão de String para Enum customizado
        if (EnumBanco.class.isAssignableFrom(targetType) && value instanceof String) {
            if (targetType.equals(SituacaoCobrancaCliente.class)) {
                return SituacaoCobrancaCliente.fromString((String) value);
            }
            if (targetType.equals(SituacaoBoleto.class)) {
                return SituacaoBoleto.fromString((String) value);
            }
            if (targetType.equals(StatusInterface.class)) {
                return StatusInterface.fromString((String) value);
            }
            if (targetType.equals(SituacaoCobrancaAgrupada.class)) {
                return SituacaoCobrancaAgrupada.fromString((String) value);
            }
        }

        // Conversão de String para Integer
        if (targetType.equals(Integer.class) && value instanceof String) {
            return Integer.valueOf((String) value);
        }

        // Conversão de Character para String
        if (targetType.equals(String.class) && value instanceof Character) {
            return value.toString();
        }

        // Retorna o valor sem modificação
        return value;
    }


}


