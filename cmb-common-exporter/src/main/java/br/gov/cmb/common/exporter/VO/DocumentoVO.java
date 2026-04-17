package br.gov.cmb.common.exporter.VO;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import br.gov.cmb.common.exporter.entidade.TipoDocumento;

import com.google.common.base.Strings;

public class DocumentoVO implements Serializable {

    private static final long serialVersionUID = -8877307217339809027L;

    private String matricula;

    private String nomeArquivo;

    private String nomeTipoDocumento;

    private String parametros;

    private TipoDocumento tipoDocumento;

    private byte[] arquivo;
    
    public DocumentoVO(){

    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }
    
    @XmlTransient
    public String getNomeArquivoSaida() {
        if(Strings.isNullOrEmpty(nomeArquivo)) {
            return tipoDocumento.getNomeArquivo();
        } else {
            return nomeArquivo;
        }
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getNomeTipoDocumento() {
        return nomeTipoDocumento;
    }

    public void setNomeTipoDocumento(String nomeTipoDocumento) {
        this.nomeTipoDocumento = nomeTipoDocumento;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

}
