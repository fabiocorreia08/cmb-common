package br.gov.cmb.common.exporter.enums;

public enum TipoSaidaEnum {
    
    P("pdf", "application/pdf");
    
    private String extensao;
    
    private String mimeType;
    
    private TipoSaidaEnum(String extensao, String mimeType) {
        this.extensao = extensao;
        this.mimeType = mimeType;
    }

    public String getExtensao() {
        return extensao;
    }
    
    public String getMimeType() {
        return mimeType;
    }
}
