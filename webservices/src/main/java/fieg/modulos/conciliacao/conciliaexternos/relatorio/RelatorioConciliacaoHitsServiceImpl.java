package fieg.modulos.conciliacao.conciliaexternos.relatorio;

import fieg.core.relatorios.AbstractRelatorioService;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoHitsDTO;
import fieg.modulos.entidade.enums.Entidade;
import jakarta.enterprise.context.ApplicationScoped;
import net.sf.jasperreports.engine.JRParameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
class RelatorioConciliacaoHitsServiceImpl extends AbstractRelatorioService implements RelatorioConciliacaoHitsService {


    @Override
    public byte[] gerarRelatorioConciliacaoHitsPdf(List<ConciliacaoHitsDTO> conciliacaoHitsDTOS, Integer idEntidade, String operador) {
        Entidade entidade = Entidade.getByCodigo(idEntidade);
        String caminho = "relatorios/RelatorioConciliaHits.jrxml";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("OPERADOR", operador);
        parametros.put("ENTIDADE", entidade.descDetalhada);
        parametros.put("IMAGEM_LOGO", entidade.urlLogoColorida);

        return gerarPdf(caminho, parametros, conciliacaoHitsDTOS);
    }

    @Override
    public byte[] gerarRelatorioConciliacaoHitsXls(List<ConciliacaoHitsDTO> conciliacaoHitsDTOS, Integer idEntidade, String operador) {
        Entidade entidade = Entidade.getByCodigo(idEntidade);

        String caminho = "relatorios/RelatorioConciliaHits.jrxml";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("OPERADOR", operador);
        parametros.put("ENTIDADE", entidade.descDetalhada);
        parametros.put("IMAGEM_LOGO", entidade.urlLogoColorida);
        parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);

        return gerarXls(caminho, parametros, conciliacaoHitsDTOS);
    }

}
