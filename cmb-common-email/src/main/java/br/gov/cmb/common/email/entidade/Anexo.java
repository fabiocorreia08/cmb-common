package br.gov.cmb.common.email.entidade;

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
@Table(name = "ANEXO_EMAIL")
public class Anexo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ANEXO_EMAIL", unique = true, nullable = false)
	private Long idAnexo;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EMAIL")
	private Email email;
	
	@Column(name = "CD_ARQUIVO")
	private String codigoArquivo;

	public Long getIdAnexo() {
		return idAnexo;
	}

	public void setIdAnexo(Long idAnexo) {
		this.idAnexo = idAnexo;
	}

	public String getCodigoArquivo() {
		return codigoArquivo;
	}

	public void setCodigoArquivo(String codigoArquivo) {
		this.codigoArquivo = codigoArquivo;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}
}
