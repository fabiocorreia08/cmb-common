package br.gov.cmb.common.exporter.enums;

public enum StatusHistoricoEnum {
	
	E("Erro"), 
	S("Sucesso");
    
    private String descricao;
    
	StatusHistoricoEnum(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }

}
