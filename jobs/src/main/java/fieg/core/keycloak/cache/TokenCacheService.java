package fieg.core.keycloak.cache;


import fieg.core.keycloak.dto.ResponseToken;

public interface TokenCacheService {

	ResponseToken getTokenGetin() throws Exception;

}
