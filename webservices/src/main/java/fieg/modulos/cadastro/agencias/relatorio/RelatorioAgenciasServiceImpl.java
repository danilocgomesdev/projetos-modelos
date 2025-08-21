package fieg.modulos.cadastro.agencias.relatorio;


import fieg.core.relatorios.AbstractRelatorioService;
import fieg.modulos.cadastro.agencias.dto.AgenciaDTO;
import fieg.modulos.entidade.enums.Entidade;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
class RelatorioAgenciasServiceImpl extends AbstractRelatorioService implements RelatorioAgenciasService {


    @Override
    public byte[] gerarRelatorioAgenciasPdf(List<AgenciaDTO> agencias, Integer idEntidade, String operador) {
        Entidade entidade = Entidade.getByCodigo(idEntidade);
        String caminho = "relatorios/RelatorioAgencias.jrxml";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("OPERADOR", operador);
        parametros.put("ENTIDADE", entidade.descDetalhada);
        parametros.put("IMAGEM_LOGO", entidade.urlLogoColorida);

        return gerarPdf(caminho, parametros, agencias);
    }

    @Override
    public byte[] gerarRelatorioAgenciasXls(List<AgenciaDTO> agencias, Integer idEntidade, String operador) {
        Entidade entidade = Entidade.getByCodigo(idEntidade);

        String caminho = "relatorios/RelatorioAgencias.jrxml";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("OPERADOR", operador);
        parametros.put("ENTIDADE", entidade.descDetalhada);
        parametros.put("IMAGEM_LOGO", entidade.urlLogoColorida);
        return gerarXls(caminho, parametros, agencias);
    }

}
