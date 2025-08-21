package fieg.modulos.tool_email;

import fieg.modulos.cobrancaautomatica.enums.EntidadeEnum;
import fieg.modulos.tool_email.exception.TemplateNaoEncontradoException;
import fieg.modulos.tool_email.template.TemplateEmailType;

public interface EmailManagerService {

	ResultadoEmail sendEmail(Email email);

	Email createEmailByTemplateComAnexo(
        String to,
        String subject,
        TemplateEmailType templateEmailType,
        Object dadosTemplate,
        byte[] anexo,
        String nomeAnexo,
		EntidadeEnum entidade
    ) throws TemplateNaoEncontradoException;

	@SuppressWarnings("SwitchStatementWithTooFewBranches")
	Email createEmailByTemplate(
			String to,
			String subject,
			TemplateEmailType templateEmailType,
			Object dadosTemplate,
			EntidadeEnum entidade
	) throws TemplateNaoEncontradoException;
}
