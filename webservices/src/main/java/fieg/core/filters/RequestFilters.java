package fieg.core.filters;

import fieg.core.exceptions.ApplicationExceptionBase;
import fieg.core.exceptions.CodigoErroCr5;
import fieg.core.requests.RequestInfoHolder;
import fieg.core.util.UtilCriptografiaSimetrica;
import fieg.core.util.UtilRest;
import fieg.modulos.operadordireitos.dto.OperadorDireitoDTO;
import fieg.modulos.operadordireitos.repository.OperadorDireitosRepository;
import io.vertx.ext.web.RoutingContext;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.apache.http.HttpStatus;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("unused")
public class RequestFilters {

    @Inject
    Logger logger;

    @Inject
    RequestInfoHolder requestInfoHolder;

    @Inject
    UtilCriptografiaSimetrica utilCriptografiaSimetrica;

    @Inject
    OperadorDireitosRepository operadorDireitosRepository;

    @Inject
    RoutingContext context;

    @ServerRequestFilter
    @Priority(Priorities.HEADER_DECORATOR)
    public void postMatchingFilter(ContainerRequestContext requestContext) {
        preencheUUID(requestContext);
        UUID uuid = requestInfoHolder.getIdentificador();

        String externalIP = Optional.ofNullable(requestContext.getHeaderString("X-Forwarded-For"))
                .orElse(context.request().host());
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        String preferedUsername = UtilRest.getPreferedUsername(requestContext).orElse("não identificado!");

        preencheInformacoesDireitoOperador(requestContext);

        logger.info("%s: %s em %s feito pelo ip: '%s' pelo usuario: '%s'".formatted(uuid, method, path, externalIP, preferedUsername));
    }

    private void preencheUUID(ContainerRequestContext requestContext) {
        requestInfoHolder.setIdentificador(
                Optional.ofNullable(requestContext.getHeaderString("uuid"))
                        .flatMap(uuidString -> {
                            try {
                                return Optional.of(UUID.fromString(uuidString));
                            } catch (IllegalArgumentException ignored) {
                                logger.warn("Header uuid estava presente mas não era um uuid: " + uuidString);
                                return Optional.empty();
                            }
                        })
                        .orElse(UUID.randomUUID())
        );
    }

    private void preencheInformacoesDireitoOperador(ContainerRequestContext requestContext) {
        var operadorIdCrypto = requestContext.getHeaderString("operadorId");
        var pessoaIdCrypto = requestContext.getHeaderString("idPessoa");
        // Aqui assumimos que, se não existe o header idOperador, veio de um serviço que não o Auth-Middleware (front end)
        if (operadorIdCrypto == null) {
            return;
        }

        String operadorId = utilCriptografiaSimetrica.descriptografar(operadorIdCrypto).orElseThrow(() ->
                criaExcecaoPermissao("Header operadorId estava presente mas não foi possível descriptografar")
        );

        String idPessoa = utilCriptografiaSimetrica.descriptografar(pessoaIdCrypto).orElseThrow(() ->
                criaExcecaoPermissao("Header idPessoa estava presente mas não foi possível descriptografar")
        );

        try {
            int operadorIdInt = Integer.parseInt(operadorId);
            requestInfoHolder.setIdOperador(operadorIdInt);

            int idPessoaInt = Integer.parseInt(idPessoa);
            requestInfoHolder.setIdPessoa(idPessoaInt);

            List<OperadorDireitoDTO> direitosCache = operadorDireitosRepository.getCr5(operadorIdInt);

            if (!direitosCache.isEmpty()) {
                requestInfoHolder.setDireitos(direitosCache);
            } else {
                throw criaExcecaoPermissao("nao encontrados direitos para o operador %d".formatted(operadorIdInt));
            }
        } catch (NumberFormatException ignored) {
            throw criaExcecaoPermissao("Header operadorId estava presente mas nao era um numero: %s".formatted(operadorId));
        }
    }

    private ApplicationExceptionBase criaExcecaoPermissao(String mensagem) {
        return new ApplicationExceptionBase(requestInfoHolder.getIdentificador() + ": " + mensagem, HttpStatus.SC_FORBIDDEN, CodigoErroCr5.SEM_PERMISSAO);
    }
}
