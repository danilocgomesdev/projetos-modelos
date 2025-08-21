package fieg.modulos.protheuscontrato.service;

import fieg.core.util.UtilData;
import fieg.externos.protheus.dto.AlterarVencimentoProtheusDTO;
import fieg.externos.protheus.restclient.ProtheusRestClient;
import fieg.modulos.cobrancaagrupada.dto.AlterarDataVencimentoDTO;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@ApplicationScoped
class ProtheusContratoServiceImpl implements ProtheusContratoService {

    @Inject
    ProtheusRestClient protheusRestClient;

    public void validarConsumirProtheusAlterarDataVencimentoCobranca(AlterarDataVencimentoDTO dto, CobrancaCliente cobrancaCliente, Integer idOperadorAlteracao) {

        AlterarVencimentoProtheusDTO alterarVencimentoProtheusDTO = getAlterarVencimentoProtheusDTO(cobrancaCliente.getDataVencimento(), dto, idOperadorAlteracao);
        if (cobrancaCliente.getRecno() != null) {
            protheusRestClient.alterarVencimentoDeTitulosNoProtheus(alterarVencimentoProtheusDTO);
        } else if (dto.getIdProtheusContrato() == null && dto.getDtInclusaoProtheus() != null) {
            protheusRestClient.alterarVencimentoDeTitulosNoProtheus(alterarVencimentoProtheusDTO);
        }
    }


    private static AlterarVencimentoProtheusDTO getAlterarVencimentoProtheusDTO(LocalDateTime dataVencimento, AlterarDataVencimentoDTO dto, Integer idOperadorAlteracao) {
        AlterarVencimentoProtheusDTO alterarVencimentoProtheusDTO = new AlterarVencimentoProtheusDTO();
        alterarVencimentoProtheusDTO.setVencimento(UtilData.converteLocalDateTimeToString(dataVencimento));
        alterarVencimentoProtheusDTO.setFilial(dto.getFilialErp());
        alterarVencimentoProtheusDTO.setRecno(dto.getRecno());
        alterarVencimentoProtheusDTO.setNumero(dto.getContId());
        alterarVencimentoProtheusDTO.setParcela(dto.getParcela());
        alterarVencimentoProtheusDTO.setPrefixo(dto.getIdSistema());
        alterarVencimentoProtheusDTO.setCpfCnpj(dto.getCpfCnpj());
        alterarVencimentoProtheusDTO.setIdOperador(idOperadorAlteracao);
        return alterarVencimentoProtheusDTO;
    }

}
