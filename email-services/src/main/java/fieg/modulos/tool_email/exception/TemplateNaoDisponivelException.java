package fieg.modulos.tool_email.exception;

import fieg.core.exceptions.CodigoErro;

public class TemplateNaoDisponivelException extends TemplateNaoEncontradoException {
	private static final String MSG_PADRAO = "Template para email não esta disponível ou configurado: ";

	public TemplateNaoDisponivelException(String template, CodigoErro e) {
		super(MSG_PADRAO + template, e);
	}
}
