package fieg.modulos.cieloecommerce.service;

import fieg.core.pagination.PageResult;
import fieg.externos.cielocheckout.MockCieloCheckoutRestClient;
import fieg.externos.cielocheckout.dto.PagamentoRecorrenteCielo;
import fieg.externos.cielocheckout.dto.PagamentoRecorrenteCieloUpdateDTO;
import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaCompletaDTO;
import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaDTO;
import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaFilterDTO;
import fieg.modulos.cieloecommerce.repository.CieloRepository;
import fieg.modulos.operacaocielo.model.DetalheOperacaoCielo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationScoped
class CieloEcommerceServiceImpl implements CieloEcommerceService {

    private static final Logger logger = LoggerFactory.getLogger(DetalheOperacaoCielo.class);

    @Inject
    MockCieloCheckoutRestClient cieloEcommerceClient;

    @Inject
    CieloRepository cieloRepository;

    @Override
    public PagamentoRecorrenteCielo buscarRecorrencia(Integer idEntidade, String idRecorrencia) {
        return cieloEcommerceClient.consultaPagamentoRecorrente(idEntidade, idRecorrencia);
    }


    @Override
    public String atualizarRecorrencia(Integer idEntidade, PagamentoRecorrenteCieloUpdateDTO dto) {
        return cieloEcommerceClient.atualizaPagamentoRecorrente(idEntidade, dto);
    }

    @Override
    public Response desativarRecorrencia(Integer idEntidade, String idRecorrencia) {
        logger.warn("Aviso: Antes de entrar no procedimento de cancelar RECORRÊNCIA. idRecorrencia: "
                    + idRecorrencia + " -  idEntidade : " + idEntidade);
        try {
            Response response = cieloEcommerceClient.desativaPagamentoRecorrente(idEntidade, idRecorrencia);
            if ((response.getStatus() == HttpStatus.SC_OK)) {
                Integer quantidadeTransacoes = cieloRepository.cancelaRecorrencia(idRecorrencia);
                if (quantidadeTransacoes <= 0) {
                    logger.error("Erro ao alterar na tabela de recorrencia o status para cancelada. idRecorrencia: "
                                 + idRecorrencia + " idEntidade : " + idEntidade, response.getStatusInfo());
                }
                return Response.ok().build();

            } else {
                logger.error("Erro ao cancelar RECORRÊNCIA. idRecorrencia: "
                             + idRecorrencia + " idEntidade : " + idEntidade, response.getStatusInfo());
                return Response.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).build();
            }
        } catch (Exception e) {
            logger.error("Erro de execeção ao cancelar RECORRÊNCIA. idRecorrencia: "
                         + idRecorrencia + " idEntidade : " + idEntidade, e.getMessage());
            return Response.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).build();
        }


    }

    @Override
    public PageResult<ConsultaRecorrenciaDTO> pesquisaPaginadoRecorrencia(ConsultaRecorrenciaFilterDTO consultaRecorrenciaFilterDTO) {
        return cieloRepository.pesquisaRecorrenciaPaginado(consultaRecorrenciaFilterDTO);
    }

    @Override
    public PageResult<ConsultaRecorrenciaCompletaDTO> pesquisaPaginadoRecorrenciaCompleta(ConsultaRecorrenciaFilterDTO consultaRecorrenciaFilterDTO) {
        return cieloRepository.pesquisaRecorrenciaPaginadoCompleto(consultaRecorrenciaFilterDTO);
    }


}
