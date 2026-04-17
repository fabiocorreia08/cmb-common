package br.gov.cmb.common.email.VO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.gov.cmb.common.email.enums.NomeTipoEmailEnum;


public class EmailVO implements Serializable {
   
    private static final long serialVersionUID = 1L;
        
    private String nomeTipoEmail;
	private String remetente;
	private String destinatario;
	private Map<String, Object> parametros = new HashMap<String, Object>();
	private List<AnexoVO> anexos = new ArrayList<AnexoVO>();
	
	public EmailVO(){
	};
	
	/**
	 * @deprecated Removeremos o enum NomeTipoEmail. Passa-se a utilizar EmailVO(String nomeTipoEmail)
	 * @param nomeTipoEmail
	 */
	@Deprecated
	public EmailVO(NomeTipoEmailEnum nomeTipoEmail) {
		setNomeTipoEmail(nomeTipoEmail.getDescricao());
	}

	public EmailVO(String nomeTipoEmail) {
		this.nomeTipoEmail = nomeTipoEmail;
	}
		
    public String getNomeTipoEmail() {
        return nomeTipoEmail;
    }
    public void setNomeTipoEmail(String nomeTipoEmail) {
        this.nomeTipoEmail = nomeTipoEmail;
    }
    public String getRemetente() {
        return remetente;
    }
    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }
    public String getDestinatario() {
        return destinatario;
    }
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
    public Map<String, Object> getParametros() {
        return parametros;
    }
    public void setParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
    }
    
    public void adicionarParametro(String nomeParamentro, Object paramentro){
        getParametros().put(nomeParamentro, paramentro);
    }
    
    public void adicionarDestinatario(String destinatario){
        if(this.destinatario == null){
            this.destinatario = destinatario + ", ";
        }else{
            this.destinatario = this.destinatario + destinatario + ", ";
        }
    }

	public List<AnexoVO> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<AnexoVO> anexos) {
		this.anexos = anexos;
	}

}
