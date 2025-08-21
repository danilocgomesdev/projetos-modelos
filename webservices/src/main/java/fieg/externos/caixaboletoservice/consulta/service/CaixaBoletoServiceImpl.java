package fieg.externos.caixaboletoservice.consulta.service;


import fieg.core.exceptions.NegocioException;
import fieg.externos.caixaboletoservice.baixa.dto.ManutencaoBoletoResponseDTO;
import fieg.externos.caixaboletoservice.consulta.MockCaixaBoletoServiceRestClient;
import fieg.externos.caixaboletoservice.consulta.dto.ConsultaBoletoCaixaResponseDTO;
import fieg.modulos.boleto.dto.ConsultaBoletoCaixa.BoletoFilterDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ApplicationScoped
class CaixaBoletoServiceImpl implements CaixaBoletoService {


    @Inject
    MockCaixaBoletoServiceRestClient caixaBoletoServiceRestClient;


    @Override
    public ConsultaBoletoCaixaResponseDTO consultaBoletoCaixa(BoletoFilterDTO filter) {
        ConsultaBoletoCaixaResponseDTO consultaBoletoResponseDTO = null;

        try {
            consultaBoletoResponseDTO = caixaBoletoServiceRestClient.consultaBoletoCaixa(filter);
        } catch (WebApplicationException e) {
            log.error("Erro ao consultaBoletoCaixa", e);
            var response = e.getResponse();

        }
        return consultaBoletoResponseDTO;
    }

    @Override
    public ManutencaoBoletoResponseDTO baixaBoletoCaixa(BoletoFilterDTO filter) {
        ManutencaoBoletoResponseDTO manutencaoBoletoResponseDTO = null;

        try {
            manutencaoBoletoResponseDTO = caixaBoletoServiceRestClient.baixaBoletoCaixa(filter);
        } catch (WebApplicationException e) {
            var response = e.getResponse();
            throw new NegocioException("Problema para baxiar o boleto "+ response);
        }
        return manutencaoBoletoResponseDTO;
    }

}
