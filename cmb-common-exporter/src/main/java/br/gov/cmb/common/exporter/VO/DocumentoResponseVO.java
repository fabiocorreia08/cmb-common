package br.gov.cmb.common.exporter.VO;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

public class DocumentoResponseVO implements Serializable {

    private static final long serialVersionUID = 5181276498921010920L;

    private String nomeArquivo;

    private String mimeType;

    private byte[] arquivo;

    public DocumentoResponseVO() {

    }

    public DocumentoResponseVO(DocumentoRequestVO documentoRequestVO, byte[] arquivo) {
        this.nomeArquivo = documentoRequestVO.getNomeArquivoSaida();
        this.mimeType = documentoRequestVO.getTipoDocumento().getMimeType();
        this.arquivo = arquivo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @XmlTransient
    public InputStream getInputStream() {
        return new ByteArrayInputStream(arquivo);
    }

}
