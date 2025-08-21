package fieg.modulos.administrativo.pagamentosnaobaixados.rest;

import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.interfaces.Mapper;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.modulos.administrativo.pagamentosnaobaixados.dto.FiltroBuscaPagamentoNaoBaixadoDTO;
import fieg.modulos.administrativo.pagamentosnaobaixados.dto.PagamentoNaoBaixadoComUnidadeDTO;
import fieg.modulos.administrativo.pagamentosnaobaixados.service.PagamentoNaoBaixadoService;
import fieg.modulos.unidade.dto.UnidadeDTO;
import fieg.modulos.visao.visaounidade.model.VisaoUnidade;
import fieg.modulos.visao.visaounidade.repository.VisaoUnidadeRepository;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

// TODO validar permissão
@Authenticated
@Path("/pagamento-nao-baixado")
@Tag(name = "Pagamento não Baixado")
@PermissaoNecessaria({Acessos.ADMINISTRAR_FINANCIAMENTO})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagamentoNaoBaixadoRest {

    @Inject
    PagamentoNaoBaixadoService pagamentoNaoBaixadoService;

    @Inject
    VisaoUnidadeRepository visaoUnidadeRepository;

    @Inject
    Mapper<VisaoUnidade, UnidadeDTO> unidadeUnidadeDTOMapper;

    @GET
    @Operation(summary = "Busca formas de pagamento não baixadas. Por enquanto apeans implementado para Cartão TEF")
    public List<PagamentoNaoBaixadoComUnidadeDTO> buscaPagamentosNaoBaixados(
            @BeanParam FiltroBuscaPagamentoNaoBaixadoDTO filtro
    ) {
        return pagamentoNaoBaixadoService.buscaPagamentosNaoBaixados(filtro)
                .stream()
                .map(pagamentoSemUnidade -> {
                    var comUnidade = new PagamentoNaoBaixadoComUnidadeDTO();
                    VisaoUnidade unidade = visaoUnidadeRepository.getByIdOrThrow(pagamentoSemUnidade.getIdUnidade());

                    comUnidade.setUnidade(unidadeUnidadeDTOMapper.map(unidade));
                    comUnidade.setPagamentoNaoBaixado(pagamentoSemUnidade);

                    return comUnidade;
                }).toList();
    }
}
