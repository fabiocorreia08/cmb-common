package br.gov.cmb.common.email.entidade;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.gov.cmb.common.email.util.CronUtils;


@Entity
@Table(name = "TIPO_EMAIL")
public class TipoEmail implements Serializable {

	private static final long serialVersionUID = 218583455894491937L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_EMAIL", unique = true, nullable = false)
	private Long idTipoEmail;

	@Column(name = "NM_TIPO_EMAIL", length = 100)
	private String nome;

	@Column(name = "DS_TIPO_EMAIL", length = 100)
	private String descricao;

	@Column(name = "DS_HORARIO_AGENDAMENTO_CRON", length = 30)
	private String cron;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TEMPLATE")
	private Template template;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "ASSC_TIPO_EMAIL_SISTEMA",
            joinColumns = { @JoinColumn(name = "ID_TIPO_EMAIL", nullable = false, updatable = false) }, 
            inverseJoinColumns = { @JoinColumn(name = "ID_SISTEMA", nullable = false, updatable = false) })
	private List<Sistema> sistemas = new ArrayList<Sistema>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoEmail")
	private List<Email> emails = new ArrayList<Email>();
	
	@Column(name = "DS_CLASSE_FORMATADOR", length = 100)
    private String classeFormatador;
	
	@Column(name = "FL_ROTINA")
    private Boolean rotina;

    public Long getIdTipoEmail() {
        return idTipoEmail;
    }

    public void setIdTipoEmail(Long idTipoEmail) {
        this.idTipoEmail = idTipoEmail;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public List<Sistema> getSistemas() {
        return sistemas;
    }

    public void setSistemas(List<Sistema> sistemas) {
        this.sistemas = sistemas;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public String getClasseFormatador() {
        return classeFormatador;
    }

    public void setClasseFormatador(String classeFormatador) {
        this.classeFormatador = classeFormatador;
    }

    public Boolean getRotina() {
        return rotina;
    }

    public void setRotina(Boolean rotina) {
        this.rotina = rotina;
    }
    
    public Date recuperarProximoAgendamento() throws ParseException {
        if(getCron()==null){
            return new Date();
        }else{
            return CronUtils.recuperarProximaData(getCron());
        }
     }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTipoEmail == null) ? 0 : idTipoEmail.hashCode());
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
		TipoEmail other = (TipoEmail) obj;
		if (idTipoEmail == null) {
			if (other.idTipoEmail != null)
				return false;
		} else if (!idTipoEmail.equals(other.idTipoEmail))
			return false;
		return true;
	}

}
