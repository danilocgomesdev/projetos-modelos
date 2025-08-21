package fieg.modulos.tool_email;


import fieg.modulos.cobrancaautomatica.enums.EntidadeEnum;
import fieg.modulos.tool_email.exception.TemplateNaoEncontradoException;
import fieg.modulos.tool_email.template.TemplateEmailType;
import io.quarkus.mailer.Mailer;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;

import java.util.Arrays;

@ApplicationScoped
public class EmailManagerServiceIpml implements EmailManagerService {

    @Inject
    Mailer mailer;

    @Location("templateCobrancaAutomaticaNotificacaoFieg")
    Template cobrancaAutomaticaNotificacaoFieg;

    @Location("templateCobrancaAutomaticaNotificacaoSenai")
    Template cobrancaAutomaticaNotificacaoSenai;

    @Location("templateCobrancaAutomaticaNotificacaoSesi")
    Template cobrancaAutomaticaNotificacaoSesi;

    @Location("templateCobrancaAutomaticaNotificacaoIel")
    Template cobrancaAutomaticaNotificacaoIel;

    @Location("templateCobrancaAutomaticaNotificacaoGestorFieg")
    Template cobrancaAutomaticaNotificacaoGestorFieg;

    @Location("templateCobrancaAutomaticaNotificacaoGestorSenai")
    Template cobrancaAutomaticaNotificacaoGestorSenai;

    @Location("templateCobrancaAutomaticaNotificacaoGestorSesi")
    Template cobrancaAutomaticaNotificacaoGestorSesi;

    @Location("templateCobrancaAutomaticaNotificacaoGestorIel")
    Template cobrancaAutomaticaNotificacaoGestorIel;

    @Location("templateCobrancaAutomaticaFieg")
    Template cobrancaAutomaticaFieg;

    @Location("templateCobrancaAutomaticaSesi")
    Template cobrancaAutomaticaSesi;

    @Location("templateCobrancaAutomaticaSenai")
    Template cobrancaAutomaticaSenai;

    @Location("templateCobrancaAutomaticaIel")
    Template cobrancaAutomaticaIel;

    @Location("templateCobrancaAutomaticaGrupoFieg")
    Template cobrancaAutomaticaGrupoFieg;

    @Location("templateCobrancaAutomaticaGrupoSesi")
    Template cobrancaAutomaticaGrupoSesi;

    @Location("templateCobrancaAutomaticaGrupoSenai")
    Template cobrancaAutomaticaGrupoSenai;

    @Location("templateCobrancaAutomaticaGrupoIel")
    Template cobrancaAutomaticaGrupoIel;

    @Inject
    Logger logger;

    String emailCopia = "wilton@fieg.com.br";

    @Override
    public ResultadoEmail sendEmail(Email email) {
        try {
            mailer.send(email.getDadosEmail());
        } catch (Throwable e) {
            String msgErro = "Erro ao enviar email";
            logger.error(msgErro, e);
            return new ResultadoEmail(e, msgErro);
        }

        return new ResultadoEmail("Email enviado!");
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public Email createEmailByTemplateComAnexo(
            String to,
            String subject,
            TemplateEmailType templateEmailType,
            Object dadosTemplate,
            byte[] anexo,
            String nomeAnexo,
            EntidadeEnum entidade
    ) throws TemplateNaoEncontradoException {
        return switch (templateEmailType) {
            case COBRANCA_AUTOMATICA -> {
                if (entidade == EntidadeEnum.FIEG) {
                    String htmlEmail = cobrancaAutomaticaFieg.data(dadosTemplate).render();
                    yield Email.crieEmailHTMLComAnexo(to, subject, htmlEmail, anexo, nomeAnexo + ".pdf", emailCopia);
                }
                if (entidade == EntidadeEnum.SESI) {
                    String htmlEmail = cobrancaAutomaticaSesi.data(dadosTemplate).render();
                    yield Email.crieEmailHTMLComAnexo(to, subject, htmlEmail, anexo, nomeAnexo + ".pdf", emailCopia);
                }
                if (entidade == EntidadeEnum.SENAI) {
                    String htmlEmail = cobrancaAutomaticaSenai.data(dadosTemplate).render();
                    yield Email.crieEmailHTMLComAnexo(to, subject, htmlEmail, anexo, nomeAnexo + ".pdf", emailCopia);
                }
                if (entidade == EntidadeEnum.IEL) {
                    String htmlEmail = cobrancaAutomaticaIel.data(dadosTemplate).render();
                    yield Email.crieEmailHTMLComAnexo(to, subject, htmlEmail, anexo, nomeAnexo + ".pdf", emailCopia);
                } else {
                    throw new TemplateNaoEncontradoException("Template não encontrado para a entidade " + entidade);
                }
            }
            case COBRANCA_AUTOMATICA_AGRUPADA -> {
                if (entidade == EntidadeEnum.FIEG) {
                    String htmlEmail = cobrancaAutomaticaGrupoFieg.data(dadosTemplate).render();
                    yield Email.crieEmailHTMLComAnexo(to, subject, htmlEmail, anexo, nomeAnexo + ".pdf", emailCopia);
                }
                if (entidade == EntidadeEnum.SESI) {
                    String htmlEmail = cobrancaAutomaticaGrupoSesi.data(dadosTemplate).render();
                    yield Email.crieEmailHTMLComAnexo(to, subject, htmlEmail, anexo, nomeAnexo + ".pdf", emailCopia);
                }
                if (entidade == EntidadeEnum.SENAI) {
                    String htmlEmail = cobrancaAutomaticaGrupoSenai.data(dadosTemplate).render();
                    yield Email.crieEmailHTMLComAnexo(to, subject, htmlEmail, anexo, nomeAnexo + ".pdf", emailCopia);
                }
                if (entidade == EntidadeEnum.IEL) {
                    String htmlEmail = cobrancaAutomaticaGrupoIel.data(dadosTemplate).render();
                    yield Email.crieEmailHTMLComAnexo(to, subject, htmlEmail, anexo, nomeAnexo + ".pdf", emailCopia);
                } else {
                    throw new TemplateNaoEncontradoException("Template não encontrado para a entidade " + entidade);
                }
            }
            case null -> throw new TemplateNaoEncontradoException("Template não encontrado: " + templateEmailType);
            default -> throw new TemplateNaoEncontradoException("Template não suportado: " + templateEmailType);
        };
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public Email createEmailByTemplate(
            String to,
            String subject,
            TemplateEmailType templateEmailType,
            Object dadosTemplate,
            EntidadeEnum entidade
    ) throws TemplateNaoEncontradoException {
        return switch (templateEmailType) {
            case NOTIFICACAO_COBRANCA_AUTOMATICA -> {
                if (entidade == EntidadeEnum.FIEG) {
                    String htmlEmail = cobrancaAutomaticaNotificacaoFieg.data(dadosTemplate).render();
                    yield Email.crieEmailHTML(to, subject, htmlEmail, emailCopia);
                }
                if (entidade == EntidadeEnum.SESI) {
                    String htmlEmail = cobrancaAutomaticaNotificacaoSesi.data(dadosTemplate).render();
                    yield Email.crieEmailHTML(to, subject, htmlEmail, emailCopia);
                }
                if (entidade == EntidadeEnum.SENAI) {
                    String htmlEmail = cobrancaAutomaticaNotificacaoSenai.data(dadosTemplate).render();
                    yield Email.crieEmailHTML(to, subject, htmlEmail, emailCopia);
                }
                if (entidade == EntidadeEnum.IEL) {
                    String htmlEmail = cobrancaAutomaticaNotificacaoIel.data(dadosTemplate).render();
                    yield Email.crieEmailHTML(to, subject, htmlEmail, emailCopia);
                } else {
                    throw new TemplateNaoEncontradoException("Template não encontrado para a entidade " + entidade);
                }
            }
            case NOTIFICACAO_COBRANCA_AUTOMATICA_GESTOR -> {
                if (entidade == EntidadeEnum.FIEG) {
                    String htmlEmail = cobrancaAutomaticaNotificacaoGestorFieg.data(dadosTemplate).render();
                    yield Email.crieEmailHTML(to, subject, htmlEmail, emailCopia);
                }
                if (entidade == EntidadeEnum.SESI) {
                    String htmlEmail = cobrancaAutomaticaNotificacaoGestorSesi.data(dadosTemplate).render();
                    yield Email.crieEmailHTML(to, subject, htmlEmail, emailCopia);
                }
                if (entidade == EntidadeEnum.SENAI) {
                    String htmlEmail = cobrancaAutomaticaNotificacaoGestorSenai.data(dadosTemplate).render();
                    yield Email.crieEmailHTML(to, subject, htmlEmail, emailCopia);
                }
                if (entidade == EntidadeEnum.IEL) {
                    String htmlEmail = cobrancaAutomaticaNotificacaoGestorIel.data(dadosTemplate).render();
                    yield Email.crieEmailHTML(to, subject, htmlEmail, emailCopia);
                } else {
                    throw new TemplateNaoEncontradoException("Template não encontrado para a entidade " + entidade);
                }
            }
            case null -> throw new TemplateNaoEncontradoException("Template não encontrado: " + templateEmailType);
            default -> throw new TemplateNaoEncontradoException("Template não suportado: " + templateEmailType);
        };
    }

}
