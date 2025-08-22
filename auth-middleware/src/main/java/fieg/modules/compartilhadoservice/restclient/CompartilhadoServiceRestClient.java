package fieg.modules.compartilhadoservice.restclient;

import fieg.core.keycloak.GetinServicesHeadersFactory;
import fieg.modules.compartilhadoservice.dtos.CIPessoaDTO;
import fieg.modules.compartilhadoservice.dtos.OperadorDireitoDTO;
import fieg.modules.compartilhadoservice.dtos.OperadorDireitoFilterDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.Cache;

import java.util.List;

@RegisterRestClient(configKey = "compartilhado-service")
@RegisterClientHeaders(GetinServicesHeadersFactory.class)
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface CompartilhadoServiceRestClient {

    @GET
    @Path("/operadores/direitos")
    List<OperadorDireitoDTO> findDireitos(@BeanParam OperadorDireitoFilterDTO filterDTO);

    @GET
    @Path("/operadores/direitos/{acesso}")
    OperadorDireitoDTO findDireitoByAcesso(@BeanParam OperadorDireitoFilterDTO filterDTO, @PathParam("acesso") Integer acesso);

    @GET
    @Path("/cipessoas/{idPessoas}")
    @Cache(sMaxAge = 1800)
    CIPessoaDTO findPessoaById(@PathParam("idPessoas") Integer idPessoas);
}
