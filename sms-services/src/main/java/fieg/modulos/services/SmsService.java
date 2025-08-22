package fieg.modulos.services;

import fieg.core.exceptions.ErrorUtilException;
import fieg.core.pagination.PageResult;
import fieg.modulos.dto.*;
import fieg.modulos.dto.response.SendSmsMultiResponseDTO;
import fieg.modulos.dto.response.SendSmsUnicoResponseDTO;
import fieg.modulos.model.SmsEnvio;

import java.util.List;

public interface SmsService {

    SendSmsUnicoResponseDTO sendSmsUnico(Integer idEntidade, Integer sistemaId, SendSmsDTO sendSmsDTO) throws ErrorUtilException;

    SendSmsMultiResponseDTO sendSmsMult(Integer idEntidade, Integer sistemaId, List<SendSmsDTO> sendSmsDTOs) throws ErrorUtilException;

    void validationSmsEnvio(SmsEnvio smsEnvio) throws ErrorUtilException;

    SmsEnvio[] converteListSmsEnvioToVarArgs(List<SmsEnvio> smsList);

    SmsEnvio findSmsEnvioByIdSms(Integer idSms);

    SmsEnvio getUltimoRegistroSMS();

    Long findCountSmsByParametros(SmsEnvioFiltroDTO smsEnvioFiltroDTO);

    List<SmsEnvio> findCountSmsByParametrosPaginado(SmsEnvioFiltroDTO smsEnvioFiltroDTO);
}
