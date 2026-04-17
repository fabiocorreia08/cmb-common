package br.gov.cmb.common.email.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SISTEMA")
public class Sistema implements Serializable {

	private static final long serialVersionUID = -6597396234827304567L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SISTEMA", unique = true, nullable = false)
	private Long idSistema;
	
	@Column(name = "NM_SISTEMA", length = 100)
	private String nome;
	
	@ManyToMany(mappedBy = "sistemas")
    private List<TipoEmail> tiposEmail = new ArrayList<TipoEmail>();

	public Long getIdSistema() {
		return idSistema;
	}

	public void setIdSistema(Long idSistema) {
		this.idSistema = idSistema;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		result = prime * result + ((idSistema == null) ? 0 : idSistema.hashCode());
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
		Sistema other = (Sistema) obj;
		if (idSistema == null) {
			if (other.idSistema != null)
				return false;
		} else if (!idSistema.equals(other.idSistema))
			return false;
		return true;
	}

}
