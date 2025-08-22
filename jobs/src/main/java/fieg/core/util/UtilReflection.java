package fieg.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public class UtilReflection {
    public UtilReflection() {
    }

    public static Object invocarMetodo(Object entidade, String nomeMetodo) {
        try {
            Object valor = entidade.getClass().getMethod(nomeMetodo).invoke(entidade);
            return valor;
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static <T> boolean permiteAlteracaoValor(T vlNovo, T vlAtual) {
        if (vlNovo == null) {
            return false;
        } else if (vlAtual == null) {
            return true;
        } else {
            if (Date.class.isAssignableFrom(vlNovo.getClass())) {
                vlNovo = (T) UtilData.converterData(String.valueOf(vlNovo));
                vlAtual = (T) UtilData.converterData(String.valueOf(vlAtual));
            }

            boolean iguais = !vlNovo.equals(vlAtual);
            return iguais;
        }
    }

    public static <T, E> void preencher(T objParametros, E objPreencher) {
        Method[] var2 = objParametros.getClass().getMethods();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Method metodo = var2[var4];

            try {
                if (metodo.getName().startsWith("get") && !"getClass".equals(metodo.getName())) {
                    Object vl1 = metodo.invoke(objParametros);

                    Object vl2;
                    try {
                        vl2 = objPreencher.getClass().getMethod(metodo.getName()).invoke(objPreencher);
                    } catch (NoSuchMethodException var12) {
                        continue;
                    }

                    boolean permiteAlteracao = permiteAlteracaoValor(vl1, vl2);
                    if (permiteAlteracao) {
                        String fieldName = metodo.getName().substring(4, metodo.getName().length());
                        String primeiraLetra = metodo.getName().substring(3, 4).toLowerCase();
                        fieldName = primeiraLetra + fieldName;
                        Field field = objPreencher.getClass().getDeclaredField(fieldName);
                        field.setAccessible(true);
                        field.set(objPreencher, vl1);
                    }
                }
            } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | InvocationTargetException | IllegalAccessException var13) {
                throw new RuntimeException(var13);
            }
        }

    }
}
