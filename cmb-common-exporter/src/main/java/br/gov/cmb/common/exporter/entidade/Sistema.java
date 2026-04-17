package br.gov.cmb.common.exporter.entidade;

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
@Table(name = "SISTEMA")
public class Sistema implements Serializable {

    private static final long serialVersionUID = 4427046838452509725L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SISTEMA", unique = true, nullable = false)
	private Long idSistema;
	
	@Column(name = "NM_SISTEMA", length = 60)
	private String nome;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sistema")
    private List<TipoDocumento> tiposDocumento= new ArrayList<TipoDocumento>();

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

    public List<TipoDocumento> getTiposDocumento() {
        return tiposDocumento;
    }

    public void setTiposDocumento(List<TipoDocumento> tiposDocumento) {
        this.tiposDocumento = tiposDocumento;
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
