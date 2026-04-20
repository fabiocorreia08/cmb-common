package br.gov.cmb.common.email.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TEMPLATE")
public class Template implements Serializable{
	
	private static final long serialVersionUID = 4031295187256307345L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TEMPLATE", unique = true, nullable = false)
	private Long idTemplate;

	@Column(name = "NM_TEMPLATE", length = 300)
	private String nome;

	@Column(name = "DS_ASSUNTO_TEMPLATE", length = 300)
	private String assunto;

	@Column(name = "DS_TEMPLATE")
	private String descricao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "template")
	private List<TipoEmail> tiposEmail = new ArrayList<TipoEmail>();

	public Long getIdTemplate() {
		return idTemplate;
	}

	public void setIdTemplate(Long idTemplate) {
		this.idTemplate = idTemplate;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public List<TipoEmail> getTiposEmail() {
        return tiposEmail;
    }

    public void setTiposEmail(List<TipoEmail> tiposEmail) {
        this.tiposEmail = tiposEmail;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idTemplate == null) ? 0 : idTemplate.hashCode());
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
		Template other = (Template) obj;
		if (idTemplate == null) {
			if (other.idTemplate != null)
				return false;
		} else if (!idTemplate.equals(other.idTemplate))
			return false;
		return true;
	}

}
