package fieg.modulos.cadastro.cliente.relatorio;

import fieg.modulos.cadastro.cliente.dto.ConsultaSituacaoClienteFilterDTO;
import fieg.modulos.cadastro.cliente.dto.SituacaoClienteDTO;


import java.util.List;

public interface RelatorioClienteService {

    byte[] gerarRelatorioSituacaoClientePdf(List<SituacaoClienteDTO> situacaoClienteDTO, Integer idEntidade, String operador, ConsultaSituacaoClienteFilterDTO dto);

    public byte[] gerarRelatorioSituacaoClienteXls(List<SituacaoClienteDTO> situacaoCliente, Integer idEntidade, String operador, ConsultaSituacaoClienteFilterDTO dto);


}
