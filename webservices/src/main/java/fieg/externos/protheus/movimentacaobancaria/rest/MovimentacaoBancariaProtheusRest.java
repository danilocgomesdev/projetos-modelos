package fieg.externos.protheus.movimentacaobancaria.rest;

import fieg.core.annotations.PermissaoNecessaria;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import io.quarkus.security.Authenticated;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Authenticated
@Path("protheus/movimentacao-bancaria")
@Tag(name = "Movimentação bancária Protheus")
@PermissaoNecessaria({Acessos.CONCILIACAO_CR5_X_PROTHEUS})
public class MovimentacaoBancariaProtheusRest {

    @POST
    @Path("/cobranca-cliente/data-alteracao-protheus/{idCobranca}")
    @Operation(summary = "Altera a dataAlteracaoProtheus de uma Cobranca Cliente. Faz alterações em cascata")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Cobrança não existe"),
    })
    public String alteraCarimboCobrancaCliente() {
        return "";
    }

    @POST
    @Path("/amortiza-boleto-pago/data-alteracao-protheus/{codigoAmortiza}")
    @Operation(summary = "Altera a dataAlteracaoProtheus de um Amortiza Boleto Pago. Faz alterações em cascata")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Amortiza boleto pago não existe"),
    })
    public String alteraCarimboAmortizaBoletoPago() {
        return "";
    }

    @POST
    @Path("/rateio-origem-cadin/data-alteracao-protheus/{idRateio}")
    @Operation(summary = "Altera a dataAlteracaoProtheus de um Rateio Origem Cadin. Faz alterações em cascata")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Rateio origem Cadin não existe"),
    })
    public String alteraCarimboRateioOrigemCadin() {
        return "";
    }
}
