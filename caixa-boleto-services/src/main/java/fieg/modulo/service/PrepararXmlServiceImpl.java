package fieg.modulo.service;


import fieg.core.exceptions.NegocioException;
import fieg.core.util.AutenticacoService;
import fieg.modulo.dto.*;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDateTime;

@ApplicationScoped
class PrepararXmlServiceImpl implements PrepararXmlService {

    @Location("templateIncluirBoleto.xml")
    Template incluirBoletoTemplate;

    @Location("templateConsultaBoleto.xml")
    Template consultaBoletoTemplate;

    @Location("templateAlteraBoleto.xml")
    Template alteraBoletoTemplate;

    @Location("templateBaixaBoleto.xml")
    Template baixaBoletoTemplate;

    @ConfigProperty(name = "caixa-boleto-services.usuario-servico")
    String usuarioServico;

    @ConfigProperty(name = "caixa-boleto-services.sistema-origem")
    String sistemaOrigem;

    @ConfigProperty(name = "caixa-boleto-services.versao-api-soap")
    String versaoApiSoap;

    @Override
    public String gerarXmlTemplateIncluirBoleto(IncluiBoletoDTO incluiBoletoDTO) {
        try {
            return incluirBoletoTemplate.render(incluiBoletoDTO);
        } catch (Exception e) {
            throw new NegocioException("Não foi possível gerar o template xml de incluir boleto", e);
        }
    }

    @Override
    public String gerarXmlTemplateAlteraBoleto(AlteraBoletoDTO alteraBoletoDTO) {
        try {
            return alteraBoletoTemplate.render(alteraBoletoDTO);
        } catch (Exception e) {
            throw new NegocioException("Não foi possível gerar o template xml de altera boleto", e);
        }
    }

    @Override
    public String gerarXmlTemplateBaixaBoleto(BaixaBoletoDTO baixaBoletoDTO) {
        try {
            return baixaBoletoTemplate.render(baixaBoletoDTO);
        } catch (Exception e) {
            throw new NegocioException("Não foi possível gerar o template xml de baixa boleto", e);
        }
    }

    @Override
    public String gerarXmlTemplateConsultaBoleto(ConsultaBoletoDTO dto) {
        try {
            return consultaBoletoTemplate.render(dto);
        } catch (Exception e) {
            throw new NegocioException("Não foi possível gerar o template xml de consulta boleto", e);
        }
    }

    @Override
    public HeaderDTO gerarHeader(String autenticaoHash) {
        HeaderDTO header = new HeaderDTO();
        AutenticacoService autenticacoService = new AutenticacoService();
        header.setAutenticacao(autenticaoHash);
        header.setVersao(versaoApiSoap);
        header.setUsuarioServico(usuarioServico);
        header.setSistemaOrigem(sistemaOrigem);
        header.setDataHora(autenticacoService.getDataConvertida(LocalDateTime.now()));
        return header;
    }
}
