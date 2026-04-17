package br.gov.cmb.common.email.entidade;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.gov.cmb.common.email.VO.EmailVO;
import br.gov.cmb.common.exception.runtime.CMBRuntimeException;

@Entity
@Table(name = "EMAIL")
public class Email implements Serializable {

	private static final long serialVersionUID = 6999933549406347432L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMAIL", unique = true, nullable = false)
	private Long idEmail;
	
	@Column(name = "NM_REMETENTE", length = 300)
	private String remetente;

	@Column(name = "DS_DESTINATARIOS")
	private String destinatarios;

	@Column(name = "DS_DESTINATARIOS_COPIA")
	private String destinatariosCopia;

	@Column(name = "DS_DESTINATARIOS_COPIA_OCULTA")
	private String destinatariosCopiaOculta;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "DT_CADASTRO")
	private Date dataCadastro = new Date();

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "DT_PARA_ENVIO")
	private Date dataParaEnvio;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_EMAIL")
	private TipoEmail tipoEmail;
		
	@OneToMany(mappedBy="email", cascade = CascadeType.ALL)
	@MapKey(name="chave")
	private Map<String, ParametroEmail> parametros;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "email", cascade=CascadeType.ALL)
    private Set<HistoricoEnvioEmail> historicoEnvioEmails;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "email", cascade = CascadeType.ALL)
	private Set<Anexo> anexos = new HashSet<Anexo>();
	
	@Column(name = "FL_ATIVO")
    private Boolean ativo;
	
	public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Email(){};
	
	public Email(EmailVO emailVO, TipoEmail tipoEmail)throws ParseException{
	    setRemetente(emailVO.getRemetente());
        setDestinatarios(emailVO.getDestinatario());
        setTipoEmail(tipoEmail);
        setAtivo(Boolean.TRUE);
        gerarAgendamento();
        criarParametros(emailVO, this);
	}
	
	public Long getIdEmail() {
        return idEmail;
    }

    public void setIdEmail(Long idEmail) {
        this.idEmail = idEmail;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(String destinatarios) {
        this.destinatarios = destinatarios;
    }

    public String getDestinatariosCopia() {
        return destinatariosCopia;
    }

    public void setDestinatariosCopia(String destinatariosCopia) {
        this.destinatariosCopia = destinatariosCopia;
    }

    public String getDestinatariosCopiaOculta() {
        return destinatariosCopiaOculta;
    }

    public void setDestinatariosCopiaOculta(String destinatariosCopiaOculta) {
        this.destinatariosCopiaOculta = destinatariosCopiaOculta;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataParaEnvio() {
        return dataParaEnvio;
    }

    public void setDataParaEnvio(Date dataParaEnvio) {
        this.dataParaEnvio = dataParaEnvio;
    }

    public TipoEmail getTipoEmail() {
        return tipoEmail;
    }

    public void setTipoEmail(TipoEmail tipoEmail) {
        this.tipoEmail = tipoEmail;
    }

    public Map<String, ParametroEmail> getParametros() {
        return parametros;
    }

    public void setParametros(Map<String, ParametroEmail> parametros) {
        this.parametros = parametros;
    }

    public Set<HistoricoEnvioEmail> getHistoricoEnvioEmails() {
        return historicoEnvioEmails;
    }

    public void setHistoricoEnvioEmails(Set<HistoricoEnvioEmail> historicoEnvioEmails) {
        this.historicoEnvioEmails = historicoEnvioEmails;
    }

    public void gerarAgendamento() {
		try{
		    this.dataParaEnvio = tipoEmail.recuperarProximoAgendamento();
		}
		catch(ParseException e){
		    throw new CMBRuntimeException(e); 
		}
        
	}

	private void criarParametros(EmailVO emailVO, Email email) {
        Map<String, ParametroEmail> mapa = new HashMap<String, ParametroEmail>();
        for (String key : emailVO.getParametros().keySet()) {
            mapa.put(key, criarParametroEmail(emailVO, email, key));
        }
        email.setParametros(mapa);
    }

    private ParametroEmail criarParametroEmail(EmailVO emailVO, Email email, String key) {
        ParametroEmail parametroEmail = new ParametroEmail();
        parametroEmail.setEmail(email);
        parametroEmail.setChave(key);
        criarParametroPorInstancia(emailVO.getParametros().get(key), parametroEmail);
        return parametroEmail;
    }

    private void criarParametroPorInstancia(Object parametro, ParametroEmail parametroEmail) {
        if (parametro instanceof String) {
            criarParametros(parametro.toString(), parametroEmail);
        } else if (parametro instanceof Collection) {
            criarParametrosLista((Collection<?>) parametro, parametroEmail);
            parametroEmail.setLista(true);
        }
    }

    private void criarParametros(String parametro, ParametroEmail parametroEmail) {
        parametroEmail.getValoresParametrosEmail().add(new ValorParametroEmail(parametroEmail, parametro));
    }

    private void criarParametrosLista(Collection<?> parametros, ParametroEmail parametroEmail) {
        for (Object parametro : parametros) {
            criarParametros(parametro.toString(), parametroEmail);
        }
    }
    
    public boolean hasAnexo() {
    	return anexos != null && !anexos.isEmpty();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEmail == null) ? 0 : idEmail.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Email other = (Email) obj;
		if (idEmail == null) {
			if (other.idEmail != null)
				return false;
		} else if (!idEmail.equals(other.idEmail))
			return false;
		return true;
	}

    public void gerarNovaDataDeEnvio() {
        if(!getTipoEmail().getRotina()){
            setAtivo(false);
        }
        else{
        	setAtivo(true);
            gerarAgendamento();
        }
    }

	public Set<Anexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(Set<Anexo> anexos) {
		this.anexos = anexos;
	}

}
