package fieg.core.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class UtilString {

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isCharBlank(Character chr) {
        return chr == null || Character.isWhitespace(chr);
    }

    public static boolean isCharNotBlank(Character chr) {
        return !isCharBlank(chr);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String removeCaracterEspecial(String str) {
        if (str != null) {
            str = str.replaceAll("[^a-zA-Z0-9]", "");
        }
        return str;
    }

    public static String removerAcentos(String texto) {
        if (texto == null) {
            return null;
        }
        String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(textoNormalizado).replaceAll("");
    }

}
