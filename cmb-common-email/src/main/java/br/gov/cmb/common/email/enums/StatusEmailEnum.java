package br.gov.cmb.common.email.enums;

public enum StatusEmailEnum {
	
	N("Não enviado"), S("Sucesso"), E("Erro"), C("Cancelado");
	
	private String descricao;
	
	StatusEmailEnum(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
