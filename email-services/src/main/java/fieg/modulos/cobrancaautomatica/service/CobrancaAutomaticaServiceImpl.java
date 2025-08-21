package fieg.modulos.cobrancaautomatica.service;

import fieg.core.exceptions.NegocioException;
import fieg.modulos.cobrancaautomatica.dto.DadosEmailCobrancaAtumoticaDTO;
import fieg.modulos.cobrancaautomatica.dto.DadosEmailCobrancaMultipartDTO;
import fieg.modulos.cobrancaautomatica.dto.DadosEmailNotificacaoCobrancaAtumoticaDTO;
import fieg.modulos.cobrancaautomatica.dto.DadosEmailNotificacaoCobrancaAtumoticaGestorDTO;
import fieg.modulos.tool_email.Email;
import fieg.modulos.tool_email.EmailManagerService;
import fieg.modulos.tool_email.ResultadoEmail;
import fieg.modulos.tool_email.template.TemplateEmailType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
class CobrancaAutomaticaServiceImpl implements CobrancaAutomaticaService {


    @Inject
    EmailManagerService emailManagerService;

    @Inject
    Logger logger;

    @Override
    public void enviarEmailCobrancaAutomatica(DadosEmailCobrancaMultipartDTO dto) {
        verificarContemDadosEmail(dto);

        DadosEmailCobrancaAtumoticaDTO dadosEmail = dto.getDadosEmail();
        String subject = "Portal Pagamentos - " + dto.getDadosEmail().getEntidade() ;
        Email email = emailManagerService.createEmailByTemplateComAnexo(
                dadosEmail.getEmailResponsavel(),
                subject,
                TemplateEmailType.COBRANCA_AUTOMATICA,
                dadosEmail,
                dto.getAnexo(),
                dadosEmail.getNomeArquivo(),
                dto.getDadosEmail().getEntidade()
        );
        ResultadoEmail resultadoEmail = emailManagerService.sendEmail(email);
        if (resultadoEmail.isEnviado()) {
            logger.info("Email de cobrança enviado: " + resultadoEmail.getMensagem());
        } else {
            logger.warn("Email de cobrança não foi enviado: {}", resultadoEmail.getMensagem(), resultadoEmail.getThrowable());
        }

    }

    @Override
    public void enviarEmailCobrancaAutomaticaAgrupada(DadosEmailCobrancaMultipartDTO dto) {
        verificarContemDadosEmail(dto);

        DadosEmailCobrancaAtumoticaDTO dadosEmail = dto.getDadosEmail();
        String subject = "Portal Pagamentos - " + dto.getDadosEmail().getEntidade() ;
        Email email = emailManagerService.createEmailByTemplateComAnexo(
                dadosEmail.getEmailResponsavel(),
                subject,
                TemplateEmailType.COBRANCA_AUTOMATICA_AGRUPADA,
                dadosEmail,
                dto.getAnexo(),
                dadosEmail.getNomeArquivo(),
                dto.getDadosEmail().getEntidade()
        );
        ResultadoEmail resultadoEmail = emailManagerService.sendEmail(email);
        if (resultadoEmail.isEnviado()) {
            logger.info("Email de cobrança enviado: " + resultadoEmail.getMensagem());
        } else {
            logger.warn("Email de cobrança não foi enviado: {}", resultadoEmail.getMensagem(), resultadoEmail.getThrowable());
        }

    }

    @Override
    public void enviarEmailNotificaoCobrancaAutomatica(DadosEmailNotificacaoCobrancaAtumoticaDTO dto) {

        String subject = "Portal Pagamentos - " + dto.getEntidade() ;
        Email email = emailManagerService.createEmailByTemplate(
                dto.getEmailResponsavel(),
                subject,
                TemplateEmailType.NOTIFICACAO_COBRANCA_AUTOMATICA,
                dto,
                dto.getEntidade()
        );
        ResultadoEmail resultadoEmail = emailManagerService.sendEmail(email);
        if (resultadoEmail.isEnviado()) {
            logger.info("Email de cobrança enviado: " + resultadoEmail.getMensagem());
        } else {
            logger.warn("Email de Notificação da cobrança não foi enviado: {}", resultadoEmail.getMensagem(), resultadoEmail.getThrowable());
        }

    }


    @Override
    public void enviarEmailNotificaoCobrancaAutomaticaGestor(List<DadosEmailNotificacaoCobrancaAtumoticaGestorDTO> dto) {
        Map<String, Object> context = new HashMap<>();
        context.put("contratos", dto);
        String subject = "Portal Pagamentos - " + dto.getFirst().getEntidade() ;
        Email email = emailManagerService.createEmailByTemplate(
                dto.getFirst().getEmailGestorResponsavel(),
                subject,
                TemplateEmailType.NOTIFICACAO_COBRANCA_AUTOMATICA_GESTOR,
                context,
                dto.getFirst().getEntidade()
        );
        ResultadoEmail resultadoEmail = emailManagerService.sendEmail(email);
        if (resultadoEmail.isEnviado()) {
            logger.info("Email de cobrança enviado: " + resultadoEmail.getMensagem());
        } else {
            logger.warn("Email de Notificação do Gestor cobrança não foi enviado: {}", resultadoEmail.getMensagem(), resultadoEmail.getThrowable());
        }

    }



    private void verificarContemDadosEmail(DadosEmailCobrancaMultipartDTO dto) {
        if (dto == null || dto.getDadosEmail() == null) {
            logger.warn("Email de cobrança não foi enviado: Dados do email não informados");
            throw new NegocioException("Dados do email não informados");
        }
        if (dto.getDadosEmail().getEmailResponsavel() == null || dto.getDadosEmail().getEmailResponsavel().isEmpty()) {
            logger.warn("Email de cobrança não foi enviado: Email do responsável não informado");
            throw new NegocioException("Email do responsável não informado");
        }

        if (dto.getAnexo() == null || dto.getAnexo().length == 0) {
            logger.warn("Email de cobrança não foi enviado: Anexo não informado");
            throw new NegocioException("Anexo não informado");
        }
    }

}
