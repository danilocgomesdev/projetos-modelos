package fieg.modulos.conciliacao.conciliaexternos.relatorio;

import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoHitsDTO;

import java.util.List;

public interface RelatorioConciliacaoHitsService {

    byte[] gerarRelatorioConciliacaoHitsPdf(List<ConciliacaoHitsDTO> conciliacaoHitsDTOS, Integer idEntidade, String operador);

    byte[] gerarRelatorioConciliacaoHitsXls(List<ConciliacaoHitsDTO> conciliacaoHitsDTOS, Integer idEntidade, String operador);
}
