package br.gov.cmb.common.email.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.gov.cmb.common.email.enums.StatusEmailEnum;

@Entity
@Table(name = "HISTORICO_ENVIO_EMAIL")
public class HistoricoEnvioEmail implements Serializable {

	private static final long serialVersionUID = 6999933549406347432L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HISTORICO_ENVIO_EMAIL", unique = true, nullable = false)
	private Long idHistoricoEnvioEmail;
	
	@Column(name = "DS_ASSUNTO", length = 300)
    private String assunto;
	
	@Column(name = "DS_CORPO_EMAIL")
    private String corpo;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "DT_ENVIO")
	private Date dataDeEnvio;
	
	@Column(name = "DS_ERRO")
    private String erro;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "DS_STATUS_EMAIL", length = 1)
	private StatusEmailEnum statusEmail = StatusEmailEnum.N;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name = "ID_EMAIL")
	private Email email;
	
	public HistoricoEnvioEmail() {
        this.dataDeEnvio = new Date();
    }
	
    public Long getIdHistoricoEnvioEmail() {
        return idHistoricoEnvioEmail;
    }

    public void setIdHistoricoEnvioEmail(Long idHistoricoEnvioEmail) {
        this.idHistoricoEnvioEmail = idHistoricoEnvioEmail;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public Date getDataDeEnvio() {
        return dataDeEnvio;
    }

    public void setDataDeEnvio(Date dataDeEnvio) {
        this.dataDeEnvio = dataDeEnvio;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public StatusEmailEnum getStatusEmail() {
        return statusEmail;
    }

    public void setStatusEmail(StatusEmailEnum statusEmail) {
        this.statusEmail = statusEmail;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
    
    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }
    
    public void alterarStatusErro(String erro) {
        this.statusEmail = StatusEmailEnum.E;
        this.dataDeEnvio = new Date();
        this.erro = erro;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idHistoricoEnvioEmail == null) ? 0 : idHistoricoEnvioEmail.hashCode());
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
        HistoricoEnvioEmail other = (HistoricoEnvioEmail) obj;
        if (idHistoricoEnvioEmail == null) {
            if (other.idHistoricoEnvioEmail != null)
                return false;
        } else if (!idHistoricoEnvioEmail.equals(other.idHistoricoEnvioEmail))
            return false;
        return true;
    }
	
}
