package br.gov.cmb.common.security.rest.request;

import java.io.Serializable;

public class UsuarioRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String login;
	private String senha;
	private Long idSistema;

	public UsuarioRequest(String login, String senha, Long idSistema) {
		this.login = login;
		this.senha = senha;
		this.idSistema = idSistema;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getIdSistema() {
		return idSistema;
	}

	public void setIdSistema(Long idSistema) {
		this.idSistema = idSistema;
	}
}
