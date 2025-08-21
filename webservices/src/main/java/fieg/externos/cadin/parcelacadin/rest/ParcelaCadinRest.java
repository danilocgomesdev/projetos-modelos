package fieg.externos.cadin.parcelacadin.rest;

import fieg.core.annotations.NaoLoggarResposta;
import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.pagination.PageQuery;
import fieg.core.pagination.PageResult;
import fieg.externos.cadin.parcelacadin.dto.BuscaParcelaCadinDTO;
import fieg.externos.cadin.parcelacadin.dto.ParcelaCadinDTO;
import fieg.externos.cadin.parcelacadin.service.ParcelaCadinService;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.modulos.cobrancacliente.dto.ParcelaDTO;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.cobrancacliente.service.CobrancaClienteService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Authenticated
@Path("/parcela-cadin")
@Tag(name = "Parcelas Cadin")
@PermissaoNecessaria(value = {Acessos.ADMNISTRADOR_CADIN})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParcelaCadinRest {

    @Inject
    ParcelaCadinService parcelaCadinService;

    @Inject
    CobrancaClienteService cobrancaClienteService;

    // Tem que ser post, pois nem todos os filtros cabem na URL
    @POST
    @Path("busca-pagas-no-intervalo")
    @Operation(description = """
            Busca informações de parcelas pagas Cadin em um intervalo máximo de um mês.
            Retorna somente as informações da parcela Cadin.
            Endpoint administrativo.""")
    @APIResponses(value = {
            @APIResponse(responseCode = "422", description = "Filtros inválidos"),
    })
    @NaoLoggarResposta
    public PageResult<ParcelaDTO> buscaPagasNoIntervaloSomenteParcelas(
            @BeanParam PageQuery pageQuery,
            @RequestBody @NotNull @Valid BuscaParcelaCadinDTO buscaParcelaCadinDTO
    ) {
        return parcelaCadinService.getParcelasCadinSomenteParcelas(pageQuery, buscaParcelaCadinDTO);
    }

    @GET
    @Path("informacoes-origem/{idCobrancaCliente}")
    @Operation(description = """
            Retorna as Informações das parcelas de origem e do cadin: As parcelas em si, Amortiza Boleto Pago, Rateio\
            Origem Cadin e Contas a Pagar/Movimentação Bancária no Protheus.
            Endpoint administrativo.""")
    @APIResponses(value = {
            @APIResponse(responseCode = "404", description = "Cobrança não existe"),
            @APIResponse(responseCode = "422", description = "Cobrança não é cadin"),
    })
    public ParcelaCadinDTO buscaInformacoesOrigem(
            @PathParam("idCobrancaCliente") Integer idCobrancaCliente
    ) {
        CobrancaCliente cobrancaCliente = cobrancaClienteService.getByIdOptional(idCobrancaCliente).orElseThrow(
                () -> new NaoEncontradoException("Não existe cobranca cliente com id " + idCobrancaCliente)
        );

        return parcelaCadinService.getParcelaCadinDTO(cobrancaCliente);
    }
}
