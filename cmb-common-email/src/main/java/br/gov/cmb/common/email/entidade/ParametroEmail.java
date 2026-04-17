package br.gov.cmb.common.email.entidade;

import java.io.Serializable;
import java.util.HashSet;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PARAMETRO_EMAIL")
public class ParametroEmail implements Serializable {

	private static final long serialVersionUID = -6597396234827304567L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PARAMETRO_EMAIL")
    private Long idParametroEmail;
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMAIL")
    private Email email;
	
	@Column(name = "DS_CHAVE", length = 100)
	private String chave;
	
	@Column(name = "FL_LISTA")
    private Boolean lista = false;
	
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parametroEmail", cascade = CascadeType.ALL)
    private Set<ValorParametroEmail> valoresParametrosEmail = new HashSet<ValorParametroEmail>();

    public Long getIdParametroEmail() {
        return idParametroEmail;
    }

    public void setIdParametroEmail(Long idParametroEmail) {
        this.idParametroEmail = idParametroEmail;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public Set<ValorParametroEmail> getValoresParametrosEmail() {
        return valoresParametrosEmail;
    }

    public void setValoresParametrosEmail(Set<ValorParametroEmail> valoresParametrosEmail) {
        this.valoresParametrosEmail = valoresParametrosEmail;
    }
    
    public Boolean getLista() {
        return lista;
    }

    public void setLista(Boolean lista) {
        this.lista = lista;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idParametroEmail == null) ? 0 : idParametroEmail.hashCode());
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
        ParametroEmail other = (ParametroEmail) obj;
        if (idParametroEmail == null) {
            if (other.idParametroEmail != null)
                return false;
        } else if (!idParametroEmail.equals(other.idParametroEmail))
            return false;
        return true;
    }
    
    

}
