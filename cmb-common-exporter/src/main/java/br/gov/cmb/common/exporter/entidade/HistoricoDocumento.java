package br.gov.cmb.common.exporter.entidade;

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

import br.gov.cmb.common.exporter.VO.DocumentoRequestVO;
import br.gov.cmb.common.exporter.enums.StatusHistoricoEnum;

@Entity
@Table(name = "HISTORICO_DOCUMENTO")
public class HistoricoDocumento implements Serializable {

    private static final long serialVersionUID = -8506637878278961582L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HISTORICO_DOCUMENTO", unique = true, nullable = false)
	private Long idHistoricoDocumento;
	
	@Column(name = "CD_MATRIC", length = 6)
	private String codigoMatricula;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "DT_DOCUMENTO")
	private Date dataDocumento;
		
	@Enumerated(EnumType.STRING)
	@Column(name = "DS_STATUS", length = 1)
    private StatusHistoricoEnum statusHistorico;

	@Column(name = "DS_ERRO")
	private String descricaoErro;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_DOCUMENTO")
    private TipoDocumento tipoDocumento;
	
	public HistoricoDocumento(){}
	
	public HistoricoDocumento(DocumentoRequestVO documento) {
	    this.codigoMatricula = documento.getMatricula();
	    this.dataDocumento = new Date();
	    this.tipoDocumento = documento.getTipoDocumento();
	    this.statusHistorico = StatusHistoricoEnum.S;
	}

    public Long getIdHistoricoDocumento() {
        return idHistoricoDocumento;
    }

    public void setIdHistoricoDocumento(Long idHistoricoDocumento) {
        this.idHistoricoDocumento = idHistoricoDocumento;
    }

    public String getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(String codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }

    public Date getDataDocumento() {
        return dataDocumento;
    }

    public void setDataDocumento(Date dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    public StatusHistoricoEnum getStatusHistorico() {
        return statusHistorico;
    }

    public void setStatusHistorico(StatusHistoricoEnum statusHistorico) {
        this.statusHistorico = statusHistorico;
    }

    public String getDescricaoErro() {
        return descricaoErro;
    }

    public void setDescricaoErro(String descricaoErro) {
        this.descricaoErro = descricaoErro;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    public void erro(String descricaoErro) {
        this.statusHistorico = StatusHistoricoEnum.E;
        this.descricaoErro = descricaoErro;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idHistoricoDocumento == null) ? 0 : idHistoricoDocumento.hashCode());
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
        HistoricoDocumento other = (HistoricoDocumento) obj;
        if (idHistoricoDocumento == null) {
            if (other.idHistoricoDocumento != null)
                return false;
        } else if (!idHistoricoDocumento.equals(other.idHistoricoDocumento))
            return false;
        return true;
    }
	
}
