package fieg.core.util;

public class UtilExeption {

    public static Throwable getCausaOriginal(Throwable throwable) {
        Throwable causa = throwable;

        while (causa.getCause() != null) {
            causa = causa.getCause();
        }

        return causa;
    }
}
