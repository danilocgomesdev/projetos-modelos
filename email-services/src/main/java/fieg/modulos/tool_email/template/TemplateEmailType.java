package fieg.modulos.tool_email.template;

/**
 * Tipos de templates para emails de notificação.
 */
public enum TemplateEmailType {

	COBRANCA_AUTOMATICA("cobrancaAutomatica"),
	COBRANCA_AUTOMATICA_AGRUPADA("cobrancaAutomaticaAgrupada"),
	NOTIFICACAO_COBRANCA_AUTOMATICA("notificacaoCobrancaAutomatica"),
	NOTIFICACAO_COBRANCA_AUTOMATICA_GESTOR("notificacaoCobrancaAutomaticaGestor");

	private final String key;

	TemplateEmailType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
