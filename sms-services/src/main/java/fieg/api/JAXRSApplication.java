package fieg.api;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "GETIN QUARKUS", version = "1.0.0", description = "# Introdução \n"
    + "Documentação de serviços de integração do Educa com o projeto Exemplo GETIN QUARKUS." + "\n # Autenticação \n"
    + "<p>As requisi\u00e7\u00f5es feitas na API  <i> webservices</i> devem ser feitas por meio de autentica\u00e7\u00e3o do tipo Bearer token (JWT), por meio da tela do Login corporativo</p> "
    + "<p></hr></p>"))
@ApplicationPath("/api")
public class JAXRSApplication extends Application {
}
