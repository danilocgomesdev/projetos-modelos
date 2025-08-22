package fieg.modulos.services;

import fieg.core.exceptions.ErrorUtilException;
import fieg.core.pagination.PageResult;
import fieg.core.restclient.SmsRestClientSenai;
import fieg.core.restclient.SmsRestClientSesi;
import fieg.modulos.dto.*;
import fieg.modulos.dto.response.ResponseSMSDTO;
import fieg.modulos.dto.response.SendSmsMultiResponseDTO;
import fieg.modulos.dto.request.SendSmsRequestListDTO;
import fieg.modulos.dto.request.SendSmsRequestUnicoDTO;
import fieg.modulos.dto.response.SendSmsResponseDTO;
import fieg.modulos.dto.response.SendSmsUnicoResponseDTO;
import fieg.modulos.mapper.SendSmsRequestMapper;
import fieg.modulos.mapper.SmsMapper;
import fieg.modulos.model.Sms;
import fieg.modulos.model.SmsEnvio;
import fieg.modulos.repository.SmsEnvioRepository;
import fieg.modulos.uteis.ErrorUtil;
import fieg.modulos.uteis.Utilidade;
import io.quarkus.logging.Log;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;

@ApplicationScoped
public class SmsServiceImpl implements SmsService{

    @Inject
    @RestClient
    SmsRestClientSenai smsRestClientSenai;

    @Inject
    @RestClient
    SmsRestClientSesi smsRestClientSesi;

    @Inject
    SmsEnvioRepository smsEnvioRepository;

    private static final Integer SESI = 2;
    private static final Integer SENAI = 3;

    @Override
    public SendSmsUnicoResponseDTO sendSmsUnico(Integer idEntidade, Integer sistemaId, SendSmsDTO sendSmsDTO) throws ErrorUtilException {
        Sms sms = SmsMapper.INSTANCE.toModel(sendSmsDTO);

        SmsEnvio smsEnvio = regrasAntesSalvarSmsEnvio(idEntidade, sistemaId, sendSmsDTO, sms);
        List<SmsEnvio> listaSalvaSms = salvarAlterarSmsEnvio(smsEnvio);
        sendSmsDTO.setId(listaSalvaSms.get(0).getSms().getIdZEnvia());

        SendSmsRequestUnicoDTO sendSmsRequestUnicoDTO = new SendSmsRequestUnicoDTO();
        sendSmsRequestUnicoDTO.setSendSmsRequest(SendSmsRequestMapper.INSTANCE.smsToSendSmsRequestDTO(sms));
        ResponseSMSDTO responseSMSDTO = obeterEnvioPorEntidade(idEntidade, sendSmsRequestUnicoDTO, null);
        alterarSmsEnvioTrueCaseSucesso(responseSMSDTO.getSendSmsResponse().getSendSmsResponse(), listaSalvaSms.get(0));
        return responseSMSDTO.getSendSmsResponse();
    }

    @Override
    public SendSmsMultiResponseDTO sendSmsMult(Integer idEntidade, Integer sistemaId, List<SendSmsDTO> sendSmsDTOs) throws ErrorUtilException {
        List<Sms> smsList = SmsMapper.INSTANCE.toModelList(sendSmsDTOs);
        List<SmsEnvio> smsEnvios = new ArrayList<>();
        for (int i = 0; i < smsList.size(); i++) {
            // adicionado para sempre manter o mesmo lote no sms multiplo
            sendSmsDTOs.get(0).setAggregateId(sendSmsDTOs.get(0).getAggregateId());
            SmsEnvio smsEnvio = regrasAntesSalvarSmsEnvio(idEntidade, sistemaId, sendSmsDTOs.get(i), smsList.get(i));
            smsEnvios.add(smsEnvio);
        }
        SmsEnvio[] smsVarArgs = converteListSmsEnvioToVarArgs(smsEnvios);
        List<SmsEnvio> listaSalvaSmsEnviado = this.salvarAlterarSmsEnvio(smsVarArgs);
        SendSmsRequestListDTO sendSmsRequestListDTO = SendSmsRequestMapper.INSTANCE.listSmsEnvioToSendSmsMultiRequestDto(listaSalvaSmsEnviado);
        ResponseSMSDTO responseSMSDTO = obeterEnvioPorEntidade(idEntidade,null, sendSmsRequestListDTO);
        alterarSmsEnvioTrueCaseSucessoMult(responseSMSDTO, listaSalvaSmsEnviado);
        return responseSMSDTO.getSendSmsMultiResponse();
    }

    @Override
    public void validationSmsEnvio(SmsEnvio smsEnvio) throws ErrorUtilException {

        if (!smsEnvio.getCpfCnpj().isEmpty()) {
            if (!Utilidade.isValidCpfOuCnpj(smsEnvio.getCpfCnpj())) {
                ErrorUtil errorUtil = new ErrorUtil();
                errorUtil.setErrorDescricao("CPF ou CNPJ invalido");
                errorUtil.setStatusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
                errorUtil.setObject(smsEnvio);
                throw new ErrorUtilException(errorUtil);
            }
        }
        if (smsEnvio.getEntidadeId() != 2 && smsEnvio.getEntidadeId()  != 3){
            ErrorUtil errorUtil = new ErrorUtil();
            errorUtil.setErrorDescricao("Entidades Validas São SESI(2) e SENAI(3)");
            errorUtil.setStatusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
            errorUtil.setObject(smsEnvio);
            throw new ErrorUtilException(errorUtil);
        }
    }
    
    @Override
    public SmsEnvio[] converteListSmsEnvioToVarArgs(List<SmsEnvio> smsList) {
        SmsEnvio[] smsVarArgs = new SmsEnvio[smsList.size()];
        for (int i = 0; i < smsList.size(); i++) {
            smsVarArgs[i] = smsList.get(i);
        }
        return smsVarArgs;
    }

    @Override
    public SmsEnvio findSmsEnvioByIdSms(Integer idSms){
        return smsEnvioRepository.findById(idSms);
    }

    @Override
    public SmsEnvio getUltimoRegistroSMS(){
        return smsEnvioRepository.getLastSMS();
    }

    @Override
    public Long findCountSmsByParametros(SmsEnvioFiltroDTO smsEnvioFiltroDTO){
        return smsEnvioRepository.findCountSmsByParametros(smsEnvioFiltroDTO);
    }

    @Override
    public List<SmsEnvio> findCountSmsByParametrosPaginado(SmsEnvioFiltroDTO smsEnvioFiltroDTO){
        return smsEnvioRepository.findSmsByParametros(smsEnvioFiltroDTO);
    }

    /***
     *  muda para true em caso de sucesso de eenvio do SMS
     */
    @Transactional
    private void alterarSmsEnvioTrueCaseSucesso(SendSmsResponseDTO sendSmsResponse, SmsEnvio smsEnvio) {
        Integer statusCode = sendSmsResponse.getStatusCode();
        if (statusCode == 0) {
            smsEnvioRepository.update("envio = true where id = ?1", smsEnvio.getId());
        }
    }

    /**
     * altera para true os sms que deram sucesso no envio multiplo
     */
    public void alterarSmsEnvioTrueCaseSucessoMult(ResponseSMSDTO responseSMSDTO, List<SmsEnvio> smsList) {
        SendSmsMultiResponseDTO sendSmsMultiResponse = responseSMSDTO.getSendSmsMultiResponse();
        for (int i = 0; i < sendSmsMultiResponse.getSendSmsMultiResponse().getSendSmsResponseList().size(); i++) {
            SendSmsResponseDTO sendSmsResponseDTO = sendSmsMultiResponse.getSendSmsMultiResponse().getSendSmsResponseList().get(i);
            alterarSmsEnvioTrueCaseSucesso(sendSmsResponseDTO, smsList.get(i));
        }
    }

    private List<SmsEnvio> salvarAlterarSmsEnvio(SmsEnvio... smsEnvio) {
        List<SmsEnvio> smsEnvioList = Arrays.asList(smsEnvio);
        for(SmsEnvio dto : smsEnvioList){
            smsEnvioRepository.persist(dto);
        }
        return smsEnvioList;
    }

    private ResponseSMSDTO obeterEnvioPorEntidade(Integer idEntidade, SendSmsRequestUnicoDTO sendSmsRequestUnicoDTO, SendSmsRequestListDTO sendSmsRequestListDTO){
        ResponseSMSDTO responseSMSDTO = new ResponseSMSDTO();
        try {
            if (Objects.equals(idEntidade, SENAI)) {
                if (sendSmsRequestUnicoDTO != null) {
                    responseSMSDTO.setSendSmsResponse(smsRestClientSenai.enviarSmsUnico(sendSmsRequestUnicoDTO));
                }else {
                    responseSMSDTO.setSendSmsMultiResponse(smsRestClientSenai.enviarSmsMult(sendSmsRequestListDTO));
                }
            }

            if (Objects.equals(idEntidade, SESI)) {
                    if (sendSmsRequestUnicoDTO != null) {
                        responseSMSDTO.setSendSmsResponse(smsRestClientSesi.enviarSmsUnico(sendSmsRequestUnicoDTO));
                    }else {
                        responseSMSDTO.setSendSmsMultiResponse(smsRestClientSesi.enviarSmsMult(sendSmsRequestListDTO));
                    }
            }
        }catch (Exception e){
            //caso não seja uma entidade valida ou diferente de SESI e SENAI
            Log.error("idEntidade invalido, somente e aceito 3 - SENAI e 2 - SESI");
        }
        return responseSMSDTO;
    }

   private SmsEnvio regrasAntesSalvarSmsEnvio(Integer idEntidade, Integer sistemaId, SendSmsDTO sendSmsDTO, Sms sms) throws ErrorUtilException {
        sms.setIdZEnvia(UUID.randomUUID().toString());
        SmsEnvio smsEnvio = new SmsEnvio(new Date(), false, sms);
        smsEnvio.setCpfCnpj(sendSmsDTO.getCpfCnpj().replaceAll("[^\\d ]", ""));
        smsEnvio.setNome(sendSmsDTO.getNome());
        smsEnvio.setSistemaId(sistemaId);
        smsEnvio.setEntidadeId(idEntidade);
        validationSmsEnvio(smsEnvio);
        return smsEnvio;
    }

}
