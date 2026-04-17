package br.gov.cmb.common.exporter.enums;

public enum TipoEntradaEnum {
    
    J("json");
    
    private String extensao;
    
    private TipoEntradaEnum(String extensao) {
        this.extensao = extensao;
    }

    public String getExtensao() {
        return extensao;
    }
}
