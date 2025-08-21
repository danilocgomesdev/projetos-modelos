package fieg.modulos.cieloecommerce.service;

import fieg.core.pagination.PageResult;
import fieg.externos.cielocheckout.dto.PagamentoRecorrenteCielo;
import fieg.externos.cielocheckout.dto.PagamentoRecorrenteCieloUpdateDTO;
import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaCompletaDTO;
import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaDTO;
import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaFilterDTO;
import jakarta.ws.rs.core.Response;

public interface CieloEcommerceService {

    PagamentoRecorrenteCielo buscarRecorrencia(Integer idEntidade, String idRecorrencia);

    String atualizarRecorrencia(Integer idEntidade, PagamentoRecorrenteCieloUpdateDTO dto);

    Response desativarRecorrencia(Integer idEntidade, String idRecorrencia);

    PageResult<ConsultaRecorrenciaDTO> pesquisaPaginadoRecorrencia(ConsultaRecorrenciaFilterDTO consultaRecorrenciaFilterDTO);

    PageResult<ConsultaRecorrenciaCompletaDTO> pesquisaPaginadoRecorrenciaCompleta(ConsultaRecorrenciaFilterDTO consultaRecorrenciaFilterDTO);
}
