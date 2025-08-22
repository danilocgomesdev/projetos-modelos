package fieg.core.util;


import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Base64;

@ApplicationScoped
public class AutenticacoService {

    public String gerarHash(
            int codBeneficiario,
            String data,
            String valor,
            long nossoNumero,
            String cnpjCpf) {

        String hash = gerarStringHash(
                Integer.toString(codBeneficiario),
                data,
                valor,
                String.valueOf(nossoNumero),
                cnpjCpf);

        MessageDigest MD;
        byte[] HASH;
        try {
            MD = MessageDigest.getInstance("SHA-256");
            HASH = MD.digest(hash.getBytes("ISO8859-1"));
            return Base64.getEncoder().encodeToString(HASH);
        } catch (Exception EX) {
            return null;
        }
    }

    private String gerarStringHash(String codBeneficiario,
                                   String data,
                                   String valor,
                                   String nossoNumero,
                                   String cnpjCpf) {
        String hash = null;
        if (codBeneficiario.length() < 7) {
            for (int i = codBeneficiario.length(); codBeneficiario.length() < 7; i++) {
                codBeneficiario = "0" + codBeneficiario;
            }
        }

        if (nossoNumero.length() < 17) {
            for (int i = nossoNumero.length(); nossoNumero.length() < 17; i++) {
                nossoNumero = "0" + nossoNumero;
            }
        }

        if (cnpjCpf.length() < 14) {
            for (int i = cnpjCpf.length(); cnpjCpf.length() < 14; i++) {
                cnpjCpf = "0" + cnpjCpf;
            }
        }

        BigDecimal valorBigdecima = new BigDecimal(valor);
        valor = valorBigdecima.setScale(2, BigDecimal.ROUND_UP).toString();
        String valorString = String.valueOf(valor).replace(".", "");
        if (valorString.length() < 15) {
            for (int i = valorString.length(); valorString.length() < 15; i++) {
                valorString = "0" + valorString;
            }
        }

        String[] split = data.split("", 9);
        String dataString = "";
        for (String dt : split) {
            if (dataString.length() < 8) {
                dataString += dt;
            } else {
                break;
            }
        }

        if (codBeneficiario.length() != 7 || nossoNumero.length() != 17 || cnpjCpf.length() != 14 || valorString.length() != 15 || dataString.length() != 8) {
            return null;
        }

        hash = codBeneficiario + nossoNumero + dataString + valorString + cnpjCpf;
        return hash;
    }

    public String getDataConvertida(LocalDateTime data) {
//        LocalDateTime data = LocalDateTime.now();
        String mes = String.valueOf(data.getMonthValue());
        if (mes.length() < 2) {
            mes = "0" + mes;
        }
        String dia = String.valueOf(data.getDayOfMonth());
        if (dia.length() < 2) {
            dia = "0" + dia;
        }
        String dataEditada = dia + "" + mes + "" +
                             data.getYear();
//        + "" + data.getHour() + "" + data.getMinute() + "" + data.getSecond();

//        System.out.println(dataEditada);
        return dataEditada;
    }
}
