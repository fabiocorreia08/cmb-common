package br.gov.cmb.common.exporter.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.gov.cmb.common.exporter.enums.TipoTemplateEnum;

@Entity
@Table(name = "TEMPLATE")
public class Template implements Serializable {

    private static final long serialVersionUID = 4097710361643797634L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TEMPLATE", unique = true, nullable = false)
	private Long idTemplate;
	
	@Column(name = "NM_TEMPLATE", length = 60)
	private String nomeTemplate;

	@Column(name = "NM_ARQUIVO", length = 200)
	private String nomeArquivo;
		
	@Enumerated(EnumType.STRING)
	@Column(name = "DS_TIPO_TEMPLATE", length = 200)
    private TipoTemplateEnum tipoTemplate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "template")
    private List<TipoDocumento> tiposDocumento= new ArrayList<TipoDocumento>();

    public Long getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(Long idTemplate) {
        this.idTemplate = idTemplate;
    }

    public String getNomeTemplate() {
        return nomeTemplate;
    }

    public void setNomeTemplate(String nomeTemplate) {
        this.nomeTemplate = nomeTemplate;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public TipoTemplateEnum getTipoTemplate() {
        return tipoTemplate;
    }

    public void setTipoTemplate(TipoTemplateEnum tipoTemplate) {
        this.tipoTemplate = tipoTemplate;
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
        result = prime * result + ((idTemplate == null) ? 0 : idTemplate.hashCode());
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
