package br.gov.cmb.common.email.enums;

public enum NomeTipoEmailEnum {
	EMAIL_CURRICULO("Email Curriculo", "Email Curriculo"), 
	EMAIL_ALTERACAO_AREA_INTERESSE("Email alteracao area interesse", "Email alteracao area interesse"), 
	EMAIL_INTERESSE_ATUAR_NA_AREA("Email interesse atuar na area", "Email interesse atuar na area");

	private final String chave;

	private final String descricao;

	NomeTipoEmailEnum(String descricao, String chave) {
		this.chave = chave;
		this.descricao = descricao;
	}

	public String getChave() {
		return chave;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
