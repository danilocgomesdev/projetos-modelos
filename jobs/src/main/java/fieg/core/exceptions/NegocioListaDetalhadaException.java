package fieg.core.exceptions;

import java.util.List;

public class NegocioListaDetalhadaException extends ApplicationExceptionBase {

	private List<DadoErro> erroList;

	/**
	 * Utilizado somente para iniciar a causa desta exception
	 *
	 * @param erroList
	 */
	NegocioListaDetalhadaException(List<DadoErro> erroList) {
		super("Vários erros ocorreram ao realizar alguma operação com lista de dados");
		this.erroList = erroList;
	}

	public NegocioListaDetalhadaException(int code, List<DadoErro> erroList) {
		super(code, "Vários erros foram encontrados, veja a causa", new NegocioListaDetalhadaException(erroList));
	}

	public static DadoErro crieErro(String item, String mensagemErro) {
		return DadoErro.crieCom(item, mensagemErro);
	}

	@Override
	public String getLocalizedMessage() {
		if (erroList != null)
			return erroList.toString();

		return null;
	}

	public List<DadoErro> getErroList() {
		NegocioListaDetalhadaException cause = (NegocioListaDetalhadaException) getCause();

		return erroList == null ? cause.getErroList() : erroList;
	}

	@Override
	public String toString() {
		return "NegocioListaDetalhadaException{" +
				"erroList=" + erroList +
				'}';
	}

	public static class DadoErro {
		private String item;
		private String mensagemErro;

		static DadoErro crieCom(String item, String mensagemErro) {
			DadoErro dado = new DadoErro();
			dado.item = item;
			dado.mensagemErro = mensagemErro;

			return dado;
		}

		public String getItem() {
			return item;
		}

		public String getMensagemErro() {
			return mensagemErro;
		}

		@Override
		public String toString() {
			return "DadoErro{" +
					"item='" + item + '\'' +
					", mensagemErro='" + mensagemErro + '\'' +
					'}';
		}
	}
}
