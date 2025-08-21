package fieg.modulos.cadastro.agencias.relatorio;

import fieg.modulos.cadastro.agencias.dto.AgenciaDTO;

import java.util.List;

public interface RelatorioAgenciasService {

    byte[] gerarRelatorioAgenciasPdf(List<AgenciaDTO> agencias, Integer idEntidade, String operador);

    byte[] gerarRelatorioAgenciasXls(List<AgenciaDTO> agencias, Integer idEntidade, String operador);
}
