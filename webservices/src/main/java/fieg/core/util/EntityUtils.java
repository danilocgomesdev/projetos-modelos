package fieg.core.util;

import jakarta.persistence.*;

import java.lang.reflect.Field;

public class EntityUtils {

    public static <T> T copiarSemId(T entidade) {
        try {
            @SuppressWarnings("unchecked")
            T copia = (T) entidade.getClass().getDeclaredConstructor().newInstance();

            Field[] campos = entidade.getClass().getDeclaredFields();

            for (Field campo : campos) {
                // Ignora o campo anotado com @Id
                if (campo.isAnnotationPresent(Id.class)) {
                    continue;
                }

                campo.setAccessible(true);
                Object valor = campo.get(entidade);
                campo.set(copia, valor);
            }

            return copia;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao copiar entidade via reflexão", e);
        }
    }

    public static <T> T copiarSemIdEEntidade(T entidade) {
        try {
            @SuppressWarnings("unchecked")
            T copia = (T) entidade.getClass().getDeclaredConstructor().newInstance();

            Field[] campos = entidade.getClass().getDeclaredFields();

            for (Field campo : campos) {
                // Ignora campos anotados com @Id ou mapeados com JPA
                if (campo.isAnnotationPresent(Id.class) ||
                        campo.isAnnotationPresent(OneToOne.class) ||
                        campo.isAnnotationPresent(OneToMany.class) ||
                        campo.isAnnotationPresent(ManyToOne.class) ||
                        campo.isAnnotationPresent(ManyToMany.class)) {
                    continue;
                }

                campo.setAccessible(true);
                Object valor = campo.get(entidade);
                campo.set(copia, valor);
            }

            return copia;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao copiar entidade via reflexão", e);
        }
    }
}
