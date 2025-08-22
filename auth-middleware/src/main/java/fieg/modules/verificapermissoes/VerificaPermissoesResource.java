package fieg.modules.verificapermissoes;

import fieg.core.requests.OriginalRequestInfoHolder;
import fieg.modules.compartilhadoservice.dtos.OperadorDireitoDTO;
import fieg.modules.redirecionador.RedirecionadorRestClient;
import io.quarkus.logging.Log;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

/**
 * Ver {@link fieg.core.filters.RequestFilters}. Esse controller recebe todas as requisições feitas para o serviço e redireciona
 * para o serviço configurado usando as informações da requsição original.
 */
@Path(VerificaPermissoesResource.BASE_PATH)
@Authenticated
public class VerificaPermissoesResource {

    public static final String BASE_PATH = "/verifica-permissoes";

    public static final String PATH_INVALIDA_CACHE = "/invalida-caches";

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    OriginalRequestInfoHolder originalRequestInfo;

    @Inject
    VerificaPermissoesService verificaPermissoesService;

    @Inject
    RedirecionadorRestClient restClient;

    @GET
    public Response verificaPermissoes(String body) {
        originalRequestInfo.setBodyOriginal(body);

        var jwt = (JsonWebToken) securityIdentity.getPrincipal();
        String username = jwt.getClaim(Claims.preferred_username);
        // Talvez aumentar esse tempo de caache já que existe forma de invalidar
        Long expiresAt = jwt.getClaim(Claims.exp);

        Log.infof("%s: validando direitos do usuario '%s' que fez %s em %s",
                originalRequestInfo.getIdentificador(),
                username,
                originalRequestInfo.getMetodoOriginal().name(),
                originalRequestInfo.getPathOriginal()
        );

        Integer idPessoa = getIdPessoa();
        List<OperadorDireitoDTO> direitos = verificaPermissoesService.findDireitos(idPessoa, expiresAt);

        Log.infof("%s: redirecionando para o servico", originalRequestInfo.getIdentificador());
        return restClient.redirecionarRequest(originalRequestInfo, idPessoa, direitos.get(0).getIdOperador());
    }

    @POST
    @Path(PATH_INVALIDA_CACHE)
    @Produces(MediaType.TEXT_PLAIN)
    public Response invalidaCaches() {
        Integer idPessoa = getIdPessoa();

        Log.infof("Limpando cache de direitos da pessoa de id %d", idPessoa);
        verificaPermissoesService.limpaCacheDireitos(idPessoa);
        return Response.ok("Cache de direitos limpo!").build();
    }

    private Integer getIdPessoa() {
        var jwt = (JsonWebToken) securityIdentity.getPrincipal();
        // chega no formato "idpessoas": "id = 1234"
        return Integer.parseInt(jwt.getClaim("idpessoas").toString().replaceAll("\\D+", ""));
    }
}
