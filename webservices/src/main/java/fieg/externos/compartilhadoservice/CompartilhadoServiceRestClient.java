package fieg.externos.compartilhadoservice;

import fieg.core.keycloak.GetinServicesHeadersFactory;
import fieg.core.pagination.PageResult;
import fieg.externos.compartilhadoservice.endereco.dto.EnderecoResponseDTO;
import fieg.externos.compartilhadoservice.pais.dto.PaisFilterDTO;
import fieg.externos.compartilhadoservice.pais.dto.PaisResponseDTO;
import fieg.externos.compartilhadoservice.pessoa.dto.PessoaCIFilterDTO;
import fieg.externos.compartilhadoservice.pessoa.requestresponse.CIPessoaResponseDTO;
import fieg.externos.compartilhadoservice.sistema.SistemaRequestDTO;
import fieg.externos.compartilhadoservice.sistema.SistemaResponseDTO;
import fieg.externos.compartilhadoservice.unidade.UnidadeCIRequestDTO;
import fieg.externos.compartilhadoservice.unidade.UnidadeCIResponseDTO;
import fieg.modulos.operadordireitos.dto.OperadorDireitoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "compartilhado-service")
@RegisterClientHeaders(GetinServicesHeadersFactory.class)
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface CompartilhadoServiceRestClient {

    @GET
    @Path("/unidades/paginado")
    PageResult<UnidadeCIResponseDTO> findUnidadesPaginado(@Valid @BeanParam UnidadeCIRequestDTO unidadeCIRequestDTO);

    @GET
    @Path("/unidades/detalhado")
    List<UnidadeCIResponseDTO> findUnidades(@Valid @BeanParam UnidadeCIRequestDTO unidadeCIRequestDTO);

    @GET
    @Path("/cipessoas/{idPessoas}")
    CIPessoaResponseDTO findPessoaById(@PathParam("idPessoas") Integer idPessoas);

    @GET
    @Path("/cipessoas")
    PageResult<CIPessoaResponseDTO> findPessoaPaginado(@Valid @BeanParam PessoaCIFilterDTO pessoaCIFilterDTO);

    @GET
    @Path("/sistemas")
    List<SistemaResponseDTO> findSistemas(@Valid @BeanParam SistemaRequestDTO sistemaRequestDTO);

    @GET
    @Path("/sistemas/paginado")
    PageResult<SistemaResponseDTO> findSistemasPaginado(@Valid @BeanParam SistemaRequestDTO sistemaRequestDTO);

    @GET
    @Path("/pais")
    List<PaisResponseDTO> findPaises();

    @GET
    @Path("/pais/paginado")
    PageResult<PaisResponseDTO> findPaisesPaginado(@Valid @BeanParam PaisFilterDTO paisFilterDTO);

    @GET
    @Path("/operadores/pessoas")
    List<OperadorDireitoDTO> findAcessos(@QueryParam("pessoa") Integer idPessoas, @QueryParam("sistema") Integer idSistema);

    @GET
    @Path("/endereco/cep/{cep}")
    EnderecoResponseDTO findEnderecoPorCep(@PathParam("cep") String cep);
}
