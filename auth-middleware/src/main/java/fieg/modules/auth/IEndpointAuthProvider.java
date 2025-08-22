package fieg.modules.auth;

public interface IEndpointAuthProvider {

    String getRolesForEndpoint(String endpoint);
}
