
package fieg.modulos.cieloJobs.conciliador.v14;

import fieg.modulos.cieloJobs.arquivo.ArquivoRetornoDTO;
import fieg.modulos.cieloJobs.conciliador.ConciliadorService;
import fieg.modulos.cieloJobs.dto.TransacaoCieloDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ConciliacaoVendaBC extends ConciliacaoAbstract {

    @Inject
    ConciliadorService conciliadorService;

    protected void registrarConciliacao(ArquivoRetornoDTO arquivoRetornoDTO) {
        if (arquivoRetornoDTO == null) {
            throw new IllegalArgumentException("Parametro inválido! Arquivo de retorno não informado.");
        }

        if (!"03".equals(arquivoRetornoDTO.getCabecalho().getOpcaoExtrato())) {
            String msg = "Parametro inválido! O tipo de arquivo " + arquivoRetornoDTO.getCabecalho().getOpcaoExtrato() + " é inválido para a conciliação de venda.";
            throw new IllegalArgumentException(msg);
        }

        List<TransacaoCieloDTO> transacoes = processar(arquivoRetornoDTO);

        for (TransacaoCieloDTO dto : transacoes) {
            conciliadorService.registrarConciliacaoVenda(dto);
        }
    }

}
