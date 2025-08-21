package fieg.modulos.tool_email.exception;

import fieg.core.exceptions.ApplicationExceptionBase;
import fieg.core.exceptions.CodigoErro;

public class TemplateNaoEncontradoException extends ApplicationExceptionBase {

	private static final String MSG_PADRAO = "Template para email não encontrado: ";

	public TemplateNaoEncontradoException(String template) {
		super(MSG_PADRAO + template);
	}

	public TemplateNaoEncontradoException(String template, CodigoErro e) {
		super(MSG_PADRAO + template, e);
	}
}
