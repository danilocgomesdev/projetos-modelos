package fieg.core.exceptions.mapper;

import fieg.core.exceptions.ApplicationExceptionBase;
import fieg.core.exceptions.CodigoErro;

public record ExceptionInfoHolder(int httpCode, String mensagem, CodigoErro codigoErro) {

    public static ExceptionInfoHolder fromApplicationExceptionBase(ApplicationExceptionBase exceptionBase) {
        return new ExceptionInfoHolder(exceptionBase.getHttpCode(), exceptionBase.getMessage(), exceptionBase.getCodigoErro());
    }
}