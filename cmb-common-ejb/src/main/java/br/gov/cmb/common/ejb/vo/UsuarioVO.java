package br.gov.cmb.common.ejb.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.gov.cmb.common.util.CollectionUtils;

@Named
@SessionScoped
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String login;
	private String senha;
	private String nome;
	private String email;
	private String matricula;
	private List<Integer> permissao;
	private final Map<String, PerfilVO> acessos = new HashMap<String, PerfilVO>();
	private String idLotacao;
	private String funcao;
	private List<String> lotacoes;
	private String cpf;
	private List<PerfilCpaVO> perfis = new ArrayList<>();

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public List<Integer> getPermissao() {
		return permissao;
	}

	public void setPermissao(List<Integer> permissao) {
		this.permissao = permissao;
	}

	public void adicionarAcesso(PerfilVO perfil) {
		acessos.put(perfil.getNome(), perfil);
	}

	public Map<String, PerfilVO> getAcessos() {
		return acessos;
	}
	
	public String getIdLotacao() {
		return idLotacao;
	}

	public void setIdLotacao(String idLotacao) {
		this.idLotacao = idLotacao;
	}

	public boolean verificaAcesso(String permissao) {
	    return getAcessos().containsKey(permissao);
	}

	public boolean isLogado() {
		return CollectionUtils.isNotEmpty(getPermissao());
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public List<String> getLotacoes() {
		return lotacoes;
	}

	public void setLotacoes(List<String> lotacoes) {
		this.lotacoes = lotacoes;
	}

	public String getCpf() {
		return cpf;
	}	

	public List<PerfilCpaVO> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<PerfilCpaVO> perfis) {
		this.perfis = perfis;
	}

	@Override
	public String toString() {
		return "UsuarioVO [login=" + login + ", senha=" + senha + ", nome=" + nome + ", email=" + email + ", matricula="
				+ matricula + ", permissao=" + permissao + ", acessos=" + acessos + ", lotacao=" + idLotacao + ", funcao=" + funcao + ", lotacao= " + cpf + "]";
	}

}
