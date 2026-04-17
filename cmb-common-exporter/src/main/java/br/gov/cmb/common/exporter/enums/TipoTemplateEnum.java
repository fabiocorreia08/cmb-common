package br.gov.cmb.common.exporter.enums;

public enum TipoTemplateEnum {
	
	JASPER("Jasper");
    
    private String descricao;
    
	TipoTemplateEnum(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }

}
