package fieg.modulos.uteis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;

public class Utilidade {

    public Utilidade() {
    }

    public static byte[] objectToByteArray(Object obj) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        byte[] data = bos.toByteArray();
        return data;
    }

    public static Object byteArrayToObject(byte[] data) throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    public static boolean isValidCpfOuCnpj(String value) {
        return value == null || value.isEmpty() || isCpf(value) || isCnpj(value);
    }

    public static boolean isCpf(String cpf) {
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");

        try {
            Long.parseLong(cpf);
        } catch (NumberFormatException var9) {
            return false;
        }

        Integer d2 = 0;
        Integer d1 = 0;
        Integer resto = 0;
        Integer digito2 = 0;
        Integer digito1 = 0;

        for(Integer nCount = 1; nCount < cpf.length() - 1; ++nCount) {
            Integer digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount));
            d1 += (11 - nCount) * digitoCPF;
            d2 += (12 - nCount) * digitoCPF;
        }

        resto = d1 % 11;
        if (resto < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }

        d2 += 2 * digito1;
        resto = d2 % 11;
        if (resto < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }

        String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());
        String nDigResult = digito1 + String.valueOf(digito2);
        return nDigVerific.equals(nDigResult);
    }

    public static boolean isCnpj(String cnpj) {
        cnpj = cnpj.replace(".", "");
        cnpj = cnpj.replace("-", "");
        cnpj = cnpj.replace("/", "");

        try {
            Long.parseLong(cnpj);
        } catch (NumberFormatException var9) {
            return false;
        }

        if (!cnpj.equals("00000000000000") && !cnpj.equals("11111111111111") && !cnpj.equals("22222222222222") && !cnpj.equals("33333333333333") && !cnpj.equals("44444444444444") && !cnpj.equals("55555555555555") && !cnpj.equals("66666666666666") && !cnpj.equals("77777777777777") && !cnpj.equals("88888888888888") && !cnpj.equals("99999999999999") && cnpj.length() == 14) {
            try {
                Integer sm = 0;
                Integer peso = 2;

                Integer i;
                Integer num;
                for(i = 11; i >= 0; --i) {
                    num = cnpj.charAt(i) - 48;
                    sm += num * peso;
                    ++peso;
                    if (peso == 10) {
                        peso = 2;
                    }
                }

                Integer r = sm % 11;
                char dig13;
                if (r != 0 && r != 1) {
                    dig13 = (char)(11 - r + 48);
                } else {
                    dig13 = '0';
                }

                sm = 0;
                peso = 2;

                for(i = 12; i >= 0; --i) {
                    num = cnpj.charAt(i) - 48;
                    sm += num * peso;
                    ++peso;
                    if (peso == 10) {
                        peso = 2;
                    }
                }

                r = sm % 11;
                char dig14;
                if (r != 0 && r != 1) {
                    dig14 = (char)(11 - r + 48);
                } else {
                    dig14 = '0';
                }

                return dig13 == cnpj.charAt(12) && dig14 == cnpj.charAt(13);
            } catch (InputMismatchException var10) {
                return false;
            }
        } else {
            return false;
        }
    }
}
