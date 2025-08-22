package fieg.core.keycloak.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseToken {

    public String access_token;
    public String expires_in;
    public String token_type;
    public String scope;
    public String refresh_token;

}
