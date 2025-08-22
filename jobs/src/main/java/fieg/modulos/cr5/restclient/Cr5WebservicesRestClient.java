package fieg.modulos.cr5.restclient;

import fieg.core.keycloak.GetinHeadrsFactory;
import fieg.modulos.cr5.cobrancaautomatica.dto.BoletoDTO;
import fieg.modulos.cr5.dto.*;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RegisterRestClient(configKey = "cr5-webservices")
@RegisterClientHeaders(GetinHeadrsFactory.class)
@Path("/servicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface Cr5WebservicesRestClient {

    @GET
    @Path("/protheus/pesquisaTitulosNaoBaixadosProtheusResumo")
    Map<String, List<Integer>> pesquisaTitulosNaoBaixadosProtheus(@QueryParam("limite") Integer limite);

    @GET
    @Path("/protheus/pesquisaTitulosCadinNaoBaixadosProtheusResumo")
    Map<String, List<Integer>> pesquisaTitulosCadinNaoBaixadosProtheus(@QueryParam("limite") Integer limite);

    @POST
    @Path("/protheus/baixa-protheus-async")
    Response baixarContratoProtheus(ParcelasBaixarProtheusDTO parcelasBaixarProtheusDTO);

    @GET
    @Path("boleto/consultarStatus")
    Response consultarStatusBoleto(
            @QueryParam("idSistema") Integer idSistema,
            @QueryParam("contId") Integer contId,
            @QueryParam("numParcela") Integer numParcela
    );

    @GET
    @Path("pix/baixa-pagamento-manual")
    Response validaTransacaoPixAberto();

    @POST
    @Path("/cobrancas/contratos/cancelar")
    Response cancelarContratoNovo(SituacaoContratoDTO situacaoContratoDTO);

    @GET
    @Path("/calendario-feriados-nacionais/soma-dias-uteis/{dias}/timestamp/{timestamp}")
    LocalDate somaDiasUteis(@PathParam("dias") Integer dias, @PathParam("timestamp") Long timestamp);


    @POST
    @Path("/protheus/incluirTituloProtheus")
    Response incluirTituloProtheus(ParcelasCieloDTO parcelasCieloDTO);

    @POST
    @Path("/protheus/incluirTituloProtheus_novo")
    Response incluirTituloProtheus_novo(List<TransacoesCieloDTO> transacoesCieloDTO);

    @GET
    @Path("protheus/periodoAbertoProtheus/{filialERP}/{modulo}")
    @Produces("application/json")
    RetornoPeriodoAbertoProtheus periodoAbertoProtheus(
            @PathParam("filialERP") String filialERP,
            @PathParam("modulo") String modulo
    );

    @POST
    @Path("/protheus/baixarParcelasCieloProtheus")
    Response baixarParcelasCieloProtheus(List<TransacoesCieloDTO> transacoesCieloDTO);


    @POST
    @Path("/cobrancas/agrupadas-protheus")
    Response agruparParcelasProtheus(AgrupamentoCobrancaAutomaticaEmRedeDTO dto);

    @POST
    @Path("/cobrancas/agrupadas")
    Response agruparParcelas(List<AgrupamentoCobrancaAutomaticaDTO> dto);

    @POST
    @Path("/cobrancas/parcelas/boletos")
    byte[] gerarBoletoEImprimir(BoletoDTO dto);
}
