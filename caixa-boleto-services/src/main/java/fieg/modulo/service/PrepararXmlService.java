package fieg.modulo.service;

import fieg.modulo.dto.*;
import fieg.modulo.dto.resquest.RequestManutencaoBoletoDTO;

public interface PrepararXmlService {

    String gerarXmlTemplateIncluirBoleto(IncluiBoletoDTO incluiBoletoDTO);

    String gerarXmlTemplateAlteraBoleto(AlteraBoletoDTO alteraBoletoDTO);

    String gerarXmlTemplateBaixaBoleto(BaixaBoletoDTO baixaBoletoDTO);

    String gerarXmlTemplateConsultaBoleto(ConsultaBoletoDTO dto);

    HeaderDTO gerarHeader(String autenticaoHash);
}
