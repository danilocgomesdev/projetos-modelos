package fieg.core.exceptions.mapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fieg.core.exceptions.ApplicationExceptionBase;
import fieg.core.exceptions.CodigoErro;
import fieg.core.util.UtilExeption;
import fieg.core.util.UtilRest;
import io.smallrye.openapi.runtime.io.JsonUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;

import java.util.Optional;

@SuppressWarnings("unused")
@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<Throwable> {

    @Inject
    Logger logger;

    @Override
    public Response toResponse(Throwable throwable) {
        // Se for um erro web do quarkus, mas não customizado pelo , retornamos a resposta padrão
        if (!(throwable instanceof ApplicationExceptionBase) && throwable instanceof WebApplicationException webApplicationException) {
            logger.error("Erro base ApplicationExceptionBase foi capturado. Retornandor esposta padrão", throwable);
            return webApplicationException.getResponse();
        }

        ObjectNode jsonNodes = JsonUtil.objectNode();
        ExceptionInfoHolder exceptionInfoHolder;

        if (throwable instanceof ApplicationExceptionBase applicationExceptionBase) {
            exceptionInfoHolder = ExceptionInfoHolder.fromApplicationExceptionBase(applicationExceptionBase);

            applicationExceptionBase.addToJsonResponse(jsonNodes);
        } else {
            Optional<ApplicationExceptionBase> causa = UtilExeption.getCausa(throwable);

            exceptionInfoHolder = causa
                    .map(ExceptionInfoHolder::fromApplicationExceptionBase)
                    .orElseGet(() -> new ExceptionInfoHolder(
                            HttpStatus.SC_INTERNAL_SERVER_ERROR,
                            "Erro interno do servidor: " + throwable.getMessage(),
                            CodigoErro.INTERNO_DO_SERVIDOR
                    ));
        }

        String error = UtilRest.createErrorJson(jsonNodes, exceptionInfoHolder, throwable);

        logger.error("Gerando resposta para exception com status %d".formatted(exceptionInfoHolder.httpCode()), throwable);

        return Response.status(exceptionInfoHolder.httpCode()).entity(error).build();
    }
}