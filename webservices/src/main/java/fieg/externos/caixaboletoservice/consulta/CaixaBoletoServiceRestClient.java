package fieg.externos.caixaboletoservice.consulta;


import fieg.core.keycloak.GetinServicesHeadersFactory;
import fieg.externos.caixaboletoservice.baixa.dto.ManutencaoBoletoResponseDTO;
import fieg.externos.caixaboletoservice.consulta.dto.ConsultaBoletoCaixaResponseDTO;
import fieg.modulos.boleto.dto.ConsultaBoletoCaixa.BoletoFilterDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@RegisterRestClient(configKey = "caixa-boleto-services")
@RegisterClientHeaders(GetinServicesHeadersFactory.class)
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Caixa Boleto Rest Client")
@ApplicationScoped
public interface CaixaBoletoServiceRestClient {

    @POST
    @Path("/boleto/consulta")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    ConsultaBoletoCaixaResponseDTO consultaBoletoCaixa(
            @RequestBody @NotNull @Valid BoletoFilterDTO dto
    );

    @POST
    @Path("/boleto/baixa")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    ManutencaoBoletoResponseDTO baixaBoletoCaixa(
            @RequestBody @NotNull @Valid BoletoFilterDTO dto
    );

}
