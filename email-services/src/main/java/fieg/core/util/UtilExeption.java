package fieg.core.util;

import fieg.core.exceptions.ApplicationExceptionBase;

import java.util.Optional;

public class UtilExeption {

    public static Optional<ApplicationExceptionBase> getCausa(Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();

            if (throwable instanceof ApplicationExceptionBase applicationExceptionBase) {
                return Optional.of(applicationExceptionBase);
            }
        }

        return Optional.empty();
    }
}
