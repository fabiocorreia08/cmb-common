package br.gov.cmb.common.email.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "VALOR_PARAMETRO_EMAIL")
public class ValorParametroEmail implements Serializable {

	private static final long serialVersionUID = -6597396234827304567L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VALOR_PARAMETRO_EMAIL")
    private Long idValorParametroEmail;
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PARAMETRO_EMAIL")
    private ParametroEmail parametroEmail;
		
	@Column(name = "DS_PARAMETRO_EMAIL", length = 100)
	private String parametro;
	
	public ValorParametroEmail(){};

	public ValorParametroEmail(ParametroEmail parametroEmail, String parametro){
	    setParametro(parametro);
	    setParametroEmail(parametroEmail);
	    getParametroEmail().getValoresParametrosEmail().add(this);
	};

    public Long getIdValorParametroEmail() {
        return idValorParametroEmail;
    }

    public void setIdValorParametroEmail(Long idValorParametroEmail) {
        this.idValorParametroEmail = idValorParametroEmail;
    }

    public ParametroEmail getParametroEmail() {
        return parametroEmail;
    }

    public void setParametroEmail(ParametroEmail parametroEmail) {
        this.parametroEmail = parametroEmail;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idValorParametroEmail == null) ? 0 : idValorParametroEmail.hashCode());
        result = prime * result + ((parametro == null) ? 0 : parametro.hashCode());
        result = prime * result + ((parametroEmail == null) ? 0 : parametroEmail.hashCode());
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
        ValorParametroEmail other = (ValorParametroEmail) obj;
        if (idValorParametroEmail == null) {
            if (other.idValorParametroEmail != null)
                return false;
        } else if (!idValorParametroEmail.equals(other.idValorParametroEmail))
            return false;
        if (parametro == null) {
            if (other.parametro != null)
                return false;
        } else if (!parametro.equals(other.parametro))
            return false;
        if (parametroEmail == null) {
            if (other.parametroEmail != null)
                return false;
        } else if (!parametroEmail.equals(other.parametroEmail))
            return false;
        return true;
    }
  
  
}
