package fieg.core.exceptions.mapper;

import fieg.core.exceptions.ApplicationExceptionBase;
import fieg.core.exceptions.CodigoErroCr5;

public record ExceptionInfoHolder(int httpCode, String mensagem, CodigoErroCr5 codigoErroCr5) {

    public static ExceptionInfoHolder fromApplicationExceptionBase(ApplicationExceptionBase exceptionBase) {
        return new ExceptionInfoHolder(exceptionBase.getHttpCode(), exceptionBase.getMessage(), exceptionBase.getCodigoErroCr5());
    }
}
