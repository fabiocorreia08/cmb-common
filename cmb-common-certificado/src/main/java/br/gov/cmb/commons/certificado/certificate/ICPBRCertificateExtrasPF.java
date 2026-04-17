package br.gov.cmb.commons.certificado.certificate;


import br.gov.cmb.commons.certificado.exception.CertificateExtrasWrongTypeException;
import br.gov.cmb.commons.certificado.oid.OID_2_16_76_1_3_1;
import br.gov.cmb.commons.certificado.oid.OID_2_16_76_1_3_5;
import br.gov.cmb.commons.certificado.oid.OID_2_16_76_1_3_6;

/**
* Informacoes extras do Certificado (ICP-BRASIL - DOC-ICP-04 versao 5.3)<br>
* <br>
* Certificado Pessoa Fisica<br>
* <br>
* OID:<br>
* 	* 2.16.76.1.3.1<br>
*   * 2.16.76.1.3.5<br>
*   * 2.16.76.1.3.6<br>
*     
* URL:
*  http://www.iti.gov.br/images/legislacao/Docicp/DOC-ICP-04_-_Versao_5.3_-_REQ._MIN._PARA_AS_PCs_NA_ICP-BRASIL_29.04.2014.pdf
*  
* @author pjneves
*
*/
public class ICPBRCertificateExtrasPF implements ResponsibleCertificateExtras {
	private ICPBRCertificateExtras extras = null;
	
	
	public ICPBRCertificateExtrasPF(ICPBRCertificateExtras extras) {
		if (!extras.isCertificatePF()) {
			throw new CertificateExtrasWrongTypeException("Nao e certificado de pessoa fisica");
		}
		
		this.extras = extras;
	}
	
	private OID_2_16_76_1_3_1 getOID_2_16_76_1_3_1() {
		return (OID_2_16_76_1_3_1) extras.getOtherName(OID_2_16_76_1_3_1.OID);
	}
	
	private OID_2_16_76_1_3_5 getOID_2_16_76_1_3_5() {
		return (OID_2_16_76_1_3_5) extras.getOtherName(OID_2_16_76_1_3_5.OID);
	}
	
	private OID_2_16_76_1_3_6 getOID_2_16_76_1_3_6() {
		return (OID_2_16_76_1_3_6) extras.getOtherName(OID_2_16_76_1_3_6.OID);
	}
	
	public String getDataNascimento() {
		if (this.getOID_2_16_76_1_3_1() != null) {
			return this.getOID_2_16_76_1_3_1().getDataNascimento();
		}
		return null;
	}
	
	public String getCPF() {
		if (this.getOID_2_16_76_1_3_1() != null) {
			return this.getOID_2_16_76_1_3_1().getCpf();
		}
		return null;
	}
	
	public String getNIS() {
		if (this.getOID_2_16_76_1_3_1() != null) {
			return this.getOID_2_16_76_1_3_1().getNis();
		}
		return null;
	}

	public String getRG() {
		if (this.getOID_2_16_76_1_3_1() != null) {
			return this.getOID_2_16_76_1_3_1().getRg();
		}
		return null;
	}
	
	public String getOrgaoExpedidorRg() {
		if (this.getOID_2_16_76_1_3_1() != null) {
			return this.getOID_2_16_76_1_3_1().getOrgaoExpedidorRg();
		}
		return null;
	}
	
	public String getUfOrgaoExpedidorRg() {
		if (this.getOID_2_16_76_1_3_1() != null) {
			return this.getOID_2_16_76_1_3_1().getUfOrgaoExpedidorRg();
		}
		return null;
	}
	
	public String getTituloEleitor() {
		if (this.getOID_2_16_76_1_3_5() != null) {
			return this.getOID_2_16_76_1_3_5().getTituloEleitor();
		}
		return null;
	}
	
	public String getZonaEleitoral() {
		if (this.getOID_2_16_76_1_3_5() != null) {
			return this.getOID_2_16_76_1_3_5().getZonaEleitoral();
		}
		return null;
	}
	
	public String getSecaoTituloEleitoral() {
		if (this.getOID_2_16_76_1_3_5() != null) {
			return this.getOID_2_16_76_1_3_5().getSecao();
		}
		return null;
	}
	
	public String getMunicipioTituloEleitoral() {
		if (this.getOID_2_16_76_1_3_5() != null) {
			return this.getOID_2_16_76_1_3_5().getMunicipio();
		}
		return null;
	}
	
	public String getUfMunicipioTituloEleitoral() {
		if (this.getOID_2_16_76_1_3_5() != null) {
			return this.getOID_2_16_76_1_3_5().getUfMunicipio();
		}
		return null;
	}
	
	public String getINSSCei() {
		if (this.getOID_2_16_76_1_3_6() != null) {
			return this.getOID_2_16_76_1_3_6().getINSSCei();
		}
		return null;
	}
}
