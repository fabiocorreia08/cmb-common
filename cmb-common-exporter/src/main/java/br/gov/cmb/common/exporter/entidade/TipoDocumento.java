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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.gov.cmb.common.exporter.enums.TipoEntradaEnum;
import br.gov.cmb.common.exporter.enums.TipoSaidaEnum;
import br.gov.cmb.common.util.PropertiesUtils;

@Entity
@Table(name = "TIPO_DOCUMENTO")
public class TipoDocumento implements Serializable {

	private static final long serialVersionUID = 6999933549406347432L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_DOCUMENTO", unique = true, nullable = false)
	private Long idTipoDocumento;
	
	@Column(name = "NM_TIPO_DOCUMENTO", length = 60)
	private String nomeTipoDocumento;

	@Column(name = "DS_TIPO_DOCUMENTO", length = 200)
	private String descricaoTipoDocumento;
	
	@Column(name = "DS_CLASSE_FORMATADORA", length = 100)
	private String descricaoClasseFormatadora;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DS_TIPO_ENTRADA")
	private TipoEntradaEnum tipoEntradaEnum;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DS_TIPO_SAIDA")
	private TipoSaidaEnum tipoSaidaEnum;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SISTEMA")
    private Sistema sistema;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TEMPLATE")
	private Template template;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoDocumento")
    private List<HistoricoDocumento> historicoDocumentos= new ArrayList<HistoricoDocumento>();

    public Long getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Long idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getNomeTipoDocumento() {
        return nomeTipoDocumento;
    }

    public void setNomeTipoDocumento(String nomeTipoDocumento) {
        this.nomeTipoDocumento = nomeTipoDocumento;
    }

    public String getDescricaoTipoDocumento() {
        return descricaoTipoDocumento;
    }

    public void setDescricaoTipoDocumento(String descricaoTipoDocumento) {
        this.descricaoTipoDocumento = descricaoTipoDocumento;
    }

    public String getDescricaoClasseFormatadora() {
        return descricaoClasseFormatadora;
    }

    public void setDescricaoClasseFormatadora(String descricaoClasseFormatadora) {
        this.descricaoClasseFormatadora = descricaoClasseFormatadora;
    }

    public TipoEntradaEnum getTipoEntradaEnum() {
        return tipoEntradaEnum;
    }

    public void setTipoEntradaEnum(TipoEntradaEnum tipoEntradaEnum) {
        this.tipoEntradaEnum = tipoEntradaEnum;
    }

    public TipoSaidaEnum getTipoSaidaEnum() {
        return tipoSaidaEnum;
    }

    public void setTipoSaidaEnum(TipoSaidaEnum tipoSaidaEnum) {
        this.tipoSaidaEnum = tipoSaidaEnum;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public List<HistoricoDocumento> getHistoricoDocumentos() {
        return historicoDocumentos;
    }

    public void setHistoricoDocumentos(List<HistoricoDocumento> historicoDocumentos) {
        this.historicoDocumentos = historicoDocumentos;
    }
    
    public String getCaminho() {
        return String.format("%s/%s/%s", PropertiesUtils.getProperty("caminho.template"), getSistema().getNome(), getTemplate().getNomeTemplate());
    }
    
    public String getCaminhoTemplate() {
        return String.format("%s/%s", getCaminho(), getTemplate().getNomeArquivo());
    }

    public String getNomeArquivo() {
        return String.format("%s.%s", getNomeTipoDocumento(), tipoSaidaEnum.getExtensao());
    }
    
    public String getMimeType() {
        return getTipoSaidaEnum().getMimeType();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idTipoDocumento == null) ? 0 : idTipoDocumento.hashCode());
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
        TipoDocumento other = (TipoDocumento) obj;
        if (idTipoDocumento == null) {
            if (other.idTipoDocumento != null)
                return false;
        } else if (!idTipoDocumento.equals(other.idTipoDocumento))
            return false;
        return true;
    }

}
