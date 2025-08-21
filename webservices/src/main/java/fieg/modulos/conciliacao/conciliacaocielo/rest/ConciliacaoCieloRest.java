package fieg.modulos.conciliacao.conciliacaocielo.rest;

import fieg.core.annotations.PermissaoNecessaria;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.modulos.conciliacao.conciliacaocielo.dto.FiltroConciliacaoCIeloDTO;
import fieg.modulos.administrativo.pagamentosnaobaixados.dto.PagamentoNaoBaixadoComUnidadeDTO;
import io.quarkus.security.Authenticated;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;

// TODO validar permissão
@Authenticated
@Path("/conciliacao-cielo")
@Tag(name = "Conciliação Cielo x CR5")
@PermissaoNecessaria({Acessos.ADMINISTRAR_FINANCIAMENTO})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConciliacaoCieloRest {

    @POST
    @Path("/cr5")
    @Operation(summary = "Busca informações das transações de cartão e suas parcelas do lado do CR5")
    public List<PagamentoNaoBaixadoComUnidadeDTO> buscaDadosLadoCR5(
            @RequestBody FiltroConciliacaoCIeloDTO filtro
    ) {
        return new ArrayList<>();
    }

    @POST
    @Path("/cielo")
    @Operation(summary = "Busca informações das transações de cartão e suas parcelas do lado da Cielo")
    public List<PagamentoNaoBaixadoComUnidadeDTO> buscaDadosLadoCielo(
            @RequestBody FiltroConciliacaoCIeloDTO filtro
    ) {
        return new ArrayList<>();
    }
}
