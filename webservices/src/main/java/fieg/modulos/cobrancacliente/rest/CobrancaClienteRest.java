package fieg.modulos.cobrancacliente.rest;

import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageQuery;
import fieg.core.pagination.PageResult;
import fieg.core.requests.RequestInfoHolder;
import fieg.modulos.cobrancaagrupada.dto.CobrancaParaContratoEmRedeDTO;
import fieg.modulos.cobrancacliente.dto.CobrancaClienteAdicionarDTO;
import fieg.modulos.cobrancacliente.dto.FiltroAdicionarParcelaDTO;
import fieg.modulos.cobrancacliente.dto.FiltroCobrancasDTO;
import fieg.modulos.cobrancacliente.dto.ParcelaDTO;
import fieg.modulos.cobrancacliente.mapper.CobrancaClienteMapStruct;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.cobrancacliente.service.CobrancaClienteService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Authenticated
@Path("/cobranca-cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Cobranca Cliente - Parcela")
public class CobrancaClienteRest {

    @Inject
    @Named("cobrancaClienteParcelaDTOMapper")
    Mapper<CobrancaCliente, ParcelaDTO> cobrancaClienteParcelaDTOMapper;

    @Inject
    CobrancaClienteService cobrancaClienteService;

    @Inject
    RequestInfoHolder requestInfoHolder;

    @POST
    @Path("/pesquisar-parcelas")
    public PageResult<ParcelaDTO> pesquisaParcelas(
            @BeanParam PageQuery pageQuery,
            @RequestBody @NotNull @Valid FiltroCobrancasDTO filtroCobrancas
    ) {
        PageResult<CobrancaCliente> cobrancas = cobrancaClienteService.pesquisaUsandoFiltro(pageQuery, filtroCobrancas);
        return cobrancas.map(cobrancaClienteParcelaDTOMapper::map);
    }

    @GET
    @Path("/pesquisar-parcelas-contrato")
    public PageResult<CobrancaClienteAdicionarDTO> pesquisaParcelasContrato(
            @BeanParam PageQuery pageQuery,
            @QueryParam("contId") Integer contId,
            @QueryParam("idSistema") Integer sistemaId
    ) {
        PageResult<CobrancaCliente> cobrancas = cobrancaClienteService.pesquisaUsandoFiltroContratoESistema(pageQuery, contId, sistemaId);
        return cobrancas.map(CobrancaClienteMapStruct.INSTANCE::toDto);
    }

    @POST
    @Path("/adicionar-parcela")
    public Response adicionarParcela(
            @RequestBody @NotNull @Valid FiltroAdicionarParcelaDTO adicionarParcelaDTO
    ) {
        requestInfoHolder.getIdOperador().ifPresent(adicionarParcelaDTO::setIdOperadorInclusao);
        cobrancaClienteService.adicionarParcela(adicionarParcelaDTO);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{idCobrancaCliente}")
    @Operation(summary = "Excluir Cobranca Cliente")
    public void excluirAgencia(
            @Parameter(description = "Id da Cobranca Cliente", example = "99")
            @PathParam("idCobrancaCliente") @NotNull Integer idCobrancaCliente
    ) {
        Integer idOperador = requestInfoHolder.getIdOperador().orElse(null);
        cobrancaClienteService.excluirCobrancaCliente(idCobrancaCliente, idOperador);
    }

}
