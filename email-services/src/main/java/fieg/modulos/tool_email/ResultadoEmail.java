package fieg.modulos.tool_email;

import lombok.Data;

@Data
public class ResultadoEmail {
	private String mensagem;
	private boolean enviado;
	private Throwable throwable;
	private Object dados;

	public ResultadoEmail(Throwable e, String msgErro) {
		throwable = e;
		mensagem = msgErro;
	}

	public ResultadoEmail(String msgSucesso) {
		enviado = true;
		mensagem = msgSucesso;
	}

}
