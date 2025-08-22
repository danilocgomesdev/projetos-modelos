package fieg.core.util;

public class UtilString {

    public static String substitueCaracterespecial(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        value = value.replaceAll("[ãâàáä]", "a")
                .replaceAll("[êèéë&]", "e")
                .replaceAll("[îìíï]", "i")
                .replaceAll("[õôòóö]", "o")
                .replaceAll("[ûúùü]", "u")
                .replaceAll("[ÃÂÀÁÄ]", "A")
                .replaceAll("[ÊÈÉË]", "E")
                .replaceAll("[ÎÌÍÏ]", "I")
                .replaceAll("[ÕÔÒÓÖ]", "O")
                .replaceAll("[ÛÙÚÜ]", "U")
                .replace('ç', 'c')
                .replace('Ç', 'C')
                .replace('ñ', 'n')
                .replace('Ñ', 'N')
                .replaceAll("[^a-zA-Z0-9]", " ") // Aceitar letras e números
                .replaceAll("[-+=*&;%$#@!_°ºª§¬¢£³²¹₢º°]", "")
                .replaceAll("['\"]", "")
                .replaceAll("[<>()\\{\\}]", "")
                .replaceAll("['\\\\.,()|/]", "")
                .replaceAll("[^!-ÿ]{1}[^ -ÿ]{0,}[^!-ÿ]{1}|[^!-ÿ]{1}", " ")
                .toUpperCase();

        return value;
    }

    public static String substitueCaracterEspeciaisCnpjCpf(String value) {
        if(value == null || value.isEmpty()){
            return null;
        }
        return value.replaceAll("[.-]", "").trim();
    }

}
