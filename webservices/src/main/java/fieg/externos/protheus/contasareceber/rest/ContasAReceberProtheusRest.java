package fieg.externos.protheus.contasareceber.rest;

import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.exceptions.NaoEncontradoException;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.externos.protheus.contasareceber.dto.ContasAReceberProtheusDTO;
import fieg.externos.protheus.contasareceber.service.ContasAReceberProtheusService;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.cobrancacliente.service.CobrancaClienteService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Authenticated
@Path("protheus/contas-a-receber")
@Tag(name = "Contas a receber Protheus")
@PermissaoNecessaria({Acessos.CONCILIACAO_CR5_X_PROTHEUS})
public class ContasAReceberProtheusRest {

    @Inject
    ContasAReceberProtheusService contasAReceberProtheusService;

    @Inject
    CobrancaClienteService cobrancaClienteService;

    @GET
    @Path("cobranca-cliente/{idCobranca}")
    @Operation(description = """
            Busca informações de contas a receber do Protheus juntamente com as movimentações bancárias associadas.
            Endpoint administrativo.""")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Cobrança não existe"),
    })
    public ContasAReceberProtheusDTO buscaContasAReceberDaCobranca(
            @PathParam("idCobranca") Integer idCobrancaCliente
    ) {
        CobrancaCliente cobrancaCliente = cobrancaClienteService.getByIdOptional(idCobrancaCliente).orElseThrow(
                () -> new NaoEncontradoException("Cobranca clietne de id %d não existe")
        );
        return contasAReceberProtheusService.getByCobrancaCliente(cobrancaCliente).orElse(null);
    }

}
