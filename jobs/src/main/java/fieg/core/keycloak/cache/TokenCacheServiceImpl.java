package fieg.core.keycloak.cache;

import fieg.core.keycloak.dto.RequestTokenBody;
import fieg.core.keycloak.dto.ResponseToken;
import fieg.core.keycloak.service.AuthTokenKeycloakApi;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Form;
import java.time.Instant;

@ApplicationScoped
public class TokenCacheServiceImpl implements TokenCacheService {

    private static ResponseToken CACHE_TOKEN_GETIN = null;

    @Inject
    @RestClient
    AuthTokenKeycloakApi api;

    @ConfigProperty(name = "service.keycloak.cr5-webservices.username")
    String usernameCr5;
    @ConfigProperty(name = "service.keycloak.cr5-webservices.password")
    String passwordCr5;
    @ConfigProperty(name = "service.keycloak.cr5-webservices.client_id")
    String client_idCr5;

    private Instant dataParaExpiracao = null;

    @Override
    @Transactional
    public ResponseToken
    getTokenGetin() throws Exception {
        if (isTokenExpirado()) {
            try {
                Log.info("Realizando requisicao para obter token das APIs Getin");

                RequestTokenBody body = crieBody(client_idCr5, usernameCr5, passwordCr5);
                Form form = body.getForm();
                Log.debug("Foi feito Login no keyclock de servicos Getin");
                CACHE_TOKEN_GETIN = api.obterTokenParaServicesGetin(form);
                dataParaExpiracao = Instant.now().plusSeconds(Long.parseLong(CACHE_TOKEN_GETIN.expires_in));

                Log.info("Token gerado e ira expirar em: " + dataParaExpiracao);
            } catch (Exception e) {
                String msg = "Erro ao tentar obter token para as APIs Getin";
                Log.error(msg + ": " + e.getMessage(), e);
                throw new Exception(msg, e);
            }
        }
        return CACHE_TOKEN_GETIN;
    }

    private RequestTokenBody crieBody(String clientId, String user, String password) {
        return new RequestTokenBody(clientId, user, password);
    }

    /**
     * @return se ‘token’ está expirado ou ainda não foi iniciado
     */
    private boolean isTokenExpirado() {
        return CACHE_TOKEN_GETIN == null || dataParaExpiracao.isBefore(Instant.now());
    }


}
