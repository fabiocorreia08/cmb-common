package br.gov.cmb.common.ejb.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PerfilCpaVO {
	
	@JsonProperty("id_perfil")
	private Long id;
	@JsonProperty("nm_perfil")
	private String nome;
	@JsonProperty("permissoes")
	private List<Integer> permissoes;
	
	public PerfilCpaVO(){}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<Integer> getPermissoes() {
		return permissoes;
	}
	
	public void setPermissoes(List<Integer> permissoes) {
		this.permissoes = permissoes;
	}
	
	
}
