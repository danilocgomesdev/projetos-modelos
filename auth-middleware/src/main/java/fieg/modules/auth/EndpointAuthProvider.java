package fieg.modules.auth;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EndpointAuthProvider implements IEndpointAuthProvider {

    @Override
    public String getRolesForEndpoint(String endpoint) {
        return null;
    }
}
