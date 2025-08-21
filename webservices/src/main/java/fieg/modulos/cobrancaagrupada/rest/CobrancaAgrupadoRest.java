package fieg.modulos.cobrancaagrupada.rest;

import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.core.requests.RequestInfoHolder;
import fieg.modulos.cobrancaagrupada.dto.*;
import fieg.modulos.cobrancaagrupada.service.CobrancaAgrupadaService;
import fieg.modulos.cobrancacliente.dto.CobrancaProtheusFiltroDTO;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.cobrancacliente.service.CobrancaClienteService;
import fieg.modulos.interfacecobranca.model.InterfaceCobranca;
import fieg.modulos.interfacecobranca.service.InterfaceCobrancaService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;

import java.util.List;

@Authenticated
@Path("/agrupadas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Cobranca Agrupada")
public class CobrancaAgrupadoRest {

    @Inject
    Logger logger;

    @Inject
    CobrancaAgrupadaService cobrancaAgrupadaService;

    @Inject
    InterfaceCobrancaService interfaceCobrancaService;

    @Inject
    CobrancaClienteService cobrancaClienteService;

    @Inject
    Mapper<CobrancaCliente, CobrancaParaContratoEmRedeDTO> cobrancaParaContratoEmRedeDTOMapper;

    @Inject
    Mapper<CobrancaCliente, DadosCobrancasGrupoDTO> cobrancaParaDadosCobrancasGrupoMapper;

    @Inject
    RequestInfoHolder requestInfoHolder;

    @POST
    @Path("/pesquisar-cobrancas-protheus")
    public PageResult<CobrancaParaContratoEmRedeDTO> pesquisaCobrancasProtheus(
            @QueryParam("page") int page,
            @QueryParam("pageSize") int pageSize,
            @RequestBody @Valid CobrancaProtheusFiltroDTO cobrancaProtheusFiltroDTO
    ) {
        // Configura paginação no DTO
        cobrancaProtheusFiltroDTO.setPage(page);
        cobrancaProtheusFiltroDTO.setPageSize(pageSize);
        PageResult<CobrancaCliente> cobrancas = cobrancaAgrupadaService.pesquisaUsandoFiltroProposta(cobrancaProtheusFiltroDTO);
        return cobrancas.map(cobrancaParaContratoEmRedeDTOMapper::map);
    }

    @POST
    @Path("/agrupadas-protheus")
    public Response agruparParcelasProtheus(@RequestBody AgruparParcelasProtheusDTO agruparParcelasProtheusDTO) {
        requestInfoHolder.getIdOperador().ifPresent(agruparParcelasProtheusDTO::setIdOperadorInclusao);
        cobrancaAgrupadaService.agruparParcelasProtheus(
                agruparParcelasProtheusDTO.getListaIdInterfaces(), agruparParcelasProtheusDTO.getDataVencimento(), agruparParcelasProtheusDTO.getIdOperadorInclusao());
        return Response.ok().build();
    }


    @POST
    @Path("/cobrancas")
    public PageResult<CobrancasGrupoDTO> pesquisaCobrancasParaAgrupar(
            @QueryParam("page") int page,
            @QueryParam("pageSize") int pageSize,
            @RequestBody CobrancasGrupoFiltroDTO cobrancasGrupoFiltroDTO
    ) {
        logger.info(String.valueOf(cobrancasGrupoFiltroDTO).toString());
        cobrancasGrupoFiltroDTO.setPage(page);
        cobrancasGrupoFiltroDTO.setPageSize(pageSize);
        return cobrancaAgrupadaService.pesquisaUsandoFiltroGrupo(cobrancasGrupoFiltroDTO);
    }

    @POST
    @Path("/agrupada-parcelas")
    public Response agruparParcelas(@RequestBody AgruparParcelasDTO parcelasDTO) {
        logger.info(parcelasDTO.toString());
        requestInfoHolder.getIdOperador().ifPresent(parcelasDTO::setIdOperadorInclusao);
        cobrancaAgrupadaService.agruparParcelas(parcelasDTO.getListaIdCobrancaCliente(), parcelasDTO.getDataVencimento(), parcelasDTO.getIdOperadorInclusao());
        return Response.ok().build();
    }

    @PUT
    @Path("/altera-dados-grupo/{id}")
    public Response alterarVencimento(@PathParam("id") Integer idCobrancaAgrupada, @RequestBody AlteraDadosGrupoDTO alterarDadosGrupoDTO) {
        requestInfoHolder.getIdOperador().ifPresent(alterarDadosGrupoDTO::setIdOperador);

        cobrancaAgrupadaService.alterarDadosGrupo(idCobrancaAgrupada, alterarDadosGrupoDTO);
        return Response.ok().build();
    }

    @PUT
    @Path("/desfazer-grupo/{id}")
    public Response desagruparParcelasPorGrupo(@PathParam("id") Integer idCobrancaAgrupada, @QueryParam("idOperador") Integer idOperador) {

        if (idOperador == null) {
            idOperador = requestInfoHolder.getIdOperador().orElse(null);
        }

        cobrancaAgrupadaService.desfazerGrupo(idCobrancaAgrupada, idOperador);
        return Response.ok().build();
    }

    @GET
    @Path("/cobrancas-do-grupo/{id}")
    public List<DadosCobrancasGrupoDTO> cobrancasDoGrupo(@PathParam("id") Integer idCobrancaAgrupada) {
        List<CobrancaCliente> listaCobrancasClientes = cobrancaAgrupadaService.obterCobracasClienteIdGrupo(idCobrancaAgrupada);
        return listaCobrancasClientes.stream().map(cobrancaParaDadosCobrancasGrupoMapper::map).toList();
    }

    @DELETE
    @Path("/exclui-boleto-grupo/{idGrupo}")
    public Response excluirBoletoGrupo(@PathParam("idGrupo") Integer idCobrancaAgrupada, @QueryParam("idOperador") Integer idOperador) {

        if (idOperador == null) {
            idOperador = requestInfoHolder.getIdOperador().orElse(null);
        }

        logger.info(idCobrancaAgrupada.toString());
        cobrancaAgrupadaService.cancelarBoletoGrupo(idCobrancaAgrupada, idOperador);
        return Response.ok().build();
    }

    @DELETE
    @Path("/exclui-boleto/{idCobrancaCliente}")
    public Response excluirBoletoSimples(@PathParam("idCobrancaCliente") Integer idCobrancaCliente, @QueryParam("idOperador") Integer idOperador) {

        if (idOperador == null) {
            idOperador = requestInfoHolder.getIdOperador().orElse(null);
        }

        logger.info(idCobrancaCliente.toString());
        cobrancaClienteService.cancelarBoleto(idCobrancaCliente, idOperador);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/busca")
    public Response buscaInteface(List<Integer> listaIdInterface) {
        List<InterfaceCobranca> listaInterfaces = interfaceCobrancaService.selectInterfacesByIds(listaIdInterface);
        return Response.ok().build();
    }


}
