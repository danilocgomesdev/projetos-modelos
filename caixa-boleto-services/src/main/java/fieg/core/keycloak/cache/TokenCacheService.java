package fieg.core.keycloak.cache;


import fieg.core.keycloak.DTO.RequestTokenBody;
import fieg.core.keycloak.DTO.ResponseToken;
import fieg.core.keycloak.service.AuthTokenKeycloakApi;
import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Form;

import java.time.Instant;

public abstract class TokenCacheService {

	private AuthTokenKeycloakApi api;

	private String username;
	private String password;
	private String clientId;

	public TokenCacheService(AuthTokenKeycloakApi api, String username, String password, String clientId) {
		this.api = api;
		this.username = username;
		this.password = password;
		this.clientId = clientId;
	}

	// Necessário para o quarkus construir o bean e injetar no de cima.
	@SuppressWarnings("unused")
	public TokenCacheService() {
	}

	private ResponseToken cacheToken = null;

	private Instant dataParaExpiracao = null;

	public ResponseToken getToken() {
		if (cacheToken == null || dataParaExpiracao.isBefore(Instant.now())) {
			try {
				Log.info("Realizando requisicao para obter token do client " + clientId);

				RequestTokenBody body = new RequestTokenBody(clientId, username, password);
				Form form = body.getForm();
				cacheToken = api.obterTokenParaServicesGetin(form);
				dataParaExpiracao = Instant.now().plusSeconds(Long.parseLong(cacheToken.expires_in));

				Log.info("Foi feito Login para o client " + clientId + ". Token gerado e ira expirar em: " + dataParaExpiracao);
			} catch (Exception e) {
				String msg = "Erro ao tentar obter token para " + clientId;
				Log.error(msg + ": " + e);
				throw new RuntimeException(msg, e);
			}
		}
		return cacheToken;
	}

}
