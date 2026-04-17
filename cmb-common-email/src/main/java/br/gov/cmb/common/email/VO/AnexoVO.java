package br.gov.cmb.common.email.VO;

import java.io.Serializable;


public class AnexoVO implements Serializable {
   
    private static final long serialVersionUID = 1L;
        
    private String nomeArquivo;
	private byte[] bytes;
	
	public AnexoVO(){
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	};
	
	

}
