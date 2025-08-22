package fieg.modulos.compartilhado;

import java.io.Serializable;

@SuppressWarnings("all")
public class Direito implements Serializable {

	private String alterar;
	private String cadastrar;
	private String consultar;
	private String excluir;
	private Integer idMenu;
	private Integer idPerfil;
	private Integer idSistema;
	private String liberado;
	private String unico;
	private Integer acesso;
	public String getAlterar() {
		return alterar;
	}

	public void setAlterar(final String alterar) {
		this.alterar = alterar;
	}

	public String getCadastrar() {
		return cadastrar;
	}

	public void setCadastrar(final String cadastrar) {
		this.cadastrar = cadastrar;
	}

	public String getConsultar() {
		return consultar;
	}

	public void setConsultar(final String consultar) {
		this.consultar = consultar;
	}

	public String getExcluir() {
		return excluir;
	}

	public void setExcluir(final String excluir) {
		this.excluir = excluir;
	}

	public Integer getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(final Integer idMenu) {
		this.idMenu = idMenu;
	}

	public Integer getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(final Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public Integer getIdSistema() {
		return idSistema;
	}

	public void setIdSistema(final Integer idSistema) {
		this.idSistema = idSistema;
	}

	public String getLiberado() {
		return liberado;
	}

	public void setLiberado(final String liberado) {
		this.liberado = liberado;
	}

	public String getUnico() {
		return unico;
	}

	public void setUnico(final String unico) {
		this.unico = unico;
	}

	public Integer getAcesso() {
		return acesso;
	}

	public void setAcesso(final Integer acesso) {
		this.acesso = acesso;
	}



}
