package fieg.core.util;

import javax.swing.text.MaskFormatter;
import java.math.BigDecimal;
import java.text.ParseException;

public class Mascaras {

    // MASCARAS PADRAO
    public static String MASK_TELEFONE = "(99) 9999-9999";
    public static String MASK_CPF = "999.999.999-99";
    public static String MASK_CNPJ = "99.999.999/9999-99";
    public static String MASK_RG = "99.999.999-99";
    public static String MASK_DATA = "99/99/9999";
    public static String MASK_DATA_BANCO = "99/99/9999";
    public static String MASK_CEP = "99999-999";
    public static String MASK_INT = "9?99999999999999";
    public static String MASK_DOUBLE = "9?9.999";
    public static String MASK_CEDENTE_CAIXA = "9999.999.99999999-9";

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static boolean isValidCPF(String cpf) {
        if ((cpf == null) || (cpf.length() != 11)) {
            return false;
        }

        Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    @Deprecated
    public static boolean validaCpfCnpj(String opcao) {
        if (opcao != null && opcao.trim().length() >= 11) {
            return opcao.length() > 11 ? isValidCNPJ(opcao) : isValidCPF(opcao);
        } else {
            return false;
        }
    }

    public static String validarCpfCnpj(String cpfCnpj) {
        if (cpfCnpj == null || cpfCnpj.trim().length() < 11) {
            throw new IllegalArgumentException("Parametro inválido CPF/CNPJ: " + cpfCnpj);
        }
        String cpfCnpjSemMask = Mascaras.removerMascaraCPFCNPJ(cpfCnpj);
        if (cpfCnpjSemMask.length() != 11 && cpfCnpjSemMask.length() != 14) {
            throw new IllegalArgumentException("Parametro inválido CPF/CNPJ: " + cpfCnpj);
        }
        boolean valido = cpfCnpjSemMask.length() == 11 ? isValidCPF(cpfCnpjSemMask) : isValidCNPJ(cpfCnpjSemMask);
        if (!valido) {
            throw new IllegalArgumentException("O CPF/CNPJ é inválido: " + cpfCnpj);
        }
        return cpfCnpjSemMask;
    }

    public static boolean isValidCNPJ(String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14)) {
            return false;
        }

        Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }

    public static String doMascaraPessoa(String entrada) {
        String teste = null, mascara = null;
        if (entrada != null) {
            teste = entrada.replaceAll("\\D", "");
            if (teste != null) {
                if (teste.length() > 11) {
                    mascara = MASK_CNPJ;
                } else {
                    mascara = MASK_CPF;
                }
            } else {
                mascara = null;
            }
        } else {
            mascara = null;
        }

        return mascara;
    }

    public static String formataCpfCnpj(String value) {
        MaskFormatter mask;
        try {
            mask = new MaskFormatter(retornaMascara(value));
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static String retornaMascara(String entrada) {
        String teste = null, mascara = null;
        if (entrada != null) {
            teste = entrada.replaceAll("\\D", "");
            if (teste != null) {
                if (teste.length() > 11) {
                    mascara = "##.###.###/####-##";
                } else {
                    mascara = "###.###.###-##";
                }
            } else {
                mascara = null;
            }
        } else {
            mascara = null;
        }

        return mascara;
    }

    public static Double retornaPercetual(BigDecimal vlPercentual, BigDecimal vlTotal) {
        if (vlPercentual != null && vlTotal != null) {
            return (vlPercentual.doubleValue() * 100) / vlTotal.doubleValue();
        }
        return (vlTotal == null) ? 0 : vlTotal.doubleValue();
    }

    public static BigDecimal retornaValorPercentual(Double percentual, BigDecimal vlTotal) {
//        && percentual > 0
        if (vlTotal != null && percentual != null) {
            return new BigDecimal((percentual.floatValue() / 100) * vlTotal.floatValue());
        }
        return (vlTotal == null) ? BigDecimal.ZERO : vlTotal;
    }

    public static BigDecimal retornaValorDeString(String entrada) {
        String sufixo = entrada.substring(entrada.length() - 2, entrada.length()), prefixo = entrada.substring(0, entrada.length() - 2);
        String valor = Integer.valueOf(prefixo) + "." + sufixo;

        return new BigDecimal(valor);
    }

    public static String formatarCEP(String cep) {
        StringBuilder mascara = new StringBuilder(cep);
        mascara.insert(5, "-");
        return mascara.toString();
    }

    public static String removerMascaraCep(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return "";
        }
        String valorRetorno = valor.replace("-", "").replace("_", "");
        return valorRetorno;
    }

    public static String removerMaskCPF(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return "";
        }
        return texto.replace(".", "").replace("-", "").replace("_", "").trim();
    }

    public static String removerMascaraCNPJ(String cnpj) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return "";
        }
        return cnpj.replace(".", "").replace("/", "").replace("_", "").replace("-", "").trim();
    }

    public static String removerMascaraCPFCNPJ(String cnpj) {
        String semMask = removerMaskCPF(cnpj);
        semMask = removerMascaraCNPJ(semMask);
        return semMask;
    }

    public static Boolean mesmoCpfCnpj(String cpfCnpj1, String cpfCnpj2) {
        String semMask1 = removerMascaraCPFCNPJ(cpfCnpj1);
        String semMask2 = removerMascaraCPFCNPJ(cpfCnpj2);
        return semMask1.equals(semMask2);
    }

    public static String removerMascaraTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            return "";
        }
        return telefone.replace("(", "").replace(")", "").replace(" ", "").replace("-", "").replace("_", "").trim();
    }

    public static String removerCaracteresException(String texto) {
        texto = texto.replace("'", "").replace("}", "").replace("]", "");
        return texto;
    }
}
