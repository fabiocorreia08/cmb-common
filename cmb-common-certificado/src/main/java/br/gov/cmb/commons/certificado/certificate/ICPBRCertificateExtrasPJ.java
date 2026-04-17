package br.gov.cmb.commons.certificado.certificate;

import br.gov.cmb.commons.certificado.exception.CertificateExtrasWrongTypeException;
import br.gov.cmb.commons.certificado.oid.OID_2_16_76_1_3_2;
import br.gov.cmb.commons.certificado.oid.OID_2_16_76_1_3_3;
import br.gov.cmb.commons.certificado.oid.OID_2_16_76_1_3_4;
import br.gov.cmb.commons.certificado.oid.OID_2_16_76_1_3_7;

/**
* Informacoes extras do Certificado (ICP-BRASIL - DOC-ICP-04 versao 5.3)<br>
* <br>
* Certificado Pessoa Juridica<br>
* <br>
* OID:<br>
* 	* 2.16.76.1.3.2<br>
*   * 2.16.76.1.3.3<br>
*   * 2.16.76.1.3.4<br>
*   * 2.16.76.1.3.7<br>
*     
* URL:
*  http://www.iti.gov.br/images/legislacao/Docicp/DOC-ICP-04_-_Versao_5.3_-_REQ._MIN._PARA_AS_PCs_NA_ICP-BRASIL_29.04.2014.pdf
*  
* @author pjneves
*
*/
public class ICPBRCertificateExtrasPJ implements ResponsibleCertificateExtras {
	private ICPBRCertificateExtras extras = null;
	
	
	public ICPBRCertificateExtrasPJ(ICPBRCertificateExtras extras) {
		if (!extras.isCertificatePJ()) {
			throw new CertificateExtrasWrongTypeException("Nao e certificado de pessoa juridica");
		}
		
		this.extras = extras;
	}
	
	private OID_2_16_76_1_3_2 getOID_2_16_76_1_3_2() {
		return (OID_2_16_76_1_3_2) extras.getOtherName(OID_2_16_76_1_3_2.OID);
	}
	
	private OID_2_16_76_1_3_3 getOID_2_16_76_1_3_3() {
		return (OID_2_16_76_1_3_3) extras.getOtherName(OID_2_16_76_1_3_3.OID);
	}
	
	private OID_2_16_76_1_3_4 getOID_2_16_76_1_3_4() {
		return (OID_2_16_76_1_3_4) extras.getOtherName(OID_2_16_76_1_3_4.OID);
	}
	
	private OID_2_16_76_1_3_7 getOID_2_16_76_1_3_7() {
		return (OID_2_16_76_1_3_7) extras.getOtherName(OID_2_16_76_1_3_7.OID);
	}
	
	public String getNomeResponsavel() {
		if (this.getOID_2_16_76_1_3_2() != null) {
			return this.getOID_2_16_76_1_3_2().getNome();
		}
		return null;
	}
	
	public String getDataNascimento() {
		if (this.getOID_2_16_76_1_3_4() != null) {
			return this.getOID_2_16_76_1_3_4().getDataNascimento();
		}
		return null;
	}
	
	public String getCPF() {
		if (this.getOID_2_16_76_1_3_4() != null) {
			return this.getOID_2_16_76_1_3_4().getCpf();
		}
		return null;
	}
	
	public String getNIS() {
		if (this.getOID_2_16_76_1_3_4() != null) {
			return this.getOID_2_16_76_1_3_4().getNis();
		}
		return null;
	}

	public String getRG() {
		if (this.getOID_2_16_76_1_3_4() != null) {
			return this.getOID_2_16_76_1_3_4().getRg();
		}
		return null;
	}
	
	public String getOrgaoExpedidorRg() {
		if (this.getOID_2_16_76_1_3_4() != null) {
			return this.getOID_2_16_76_1_3_4().getOrgaoExpedidorRg();
		}
		return null;
	}
	
	public String getUfOrgaoExpedidorRg() {
		if (this.getOID_2_16_76_1_3_4() != null) {
			return this.getOID_2_16_76_1_3_4().getUfOrgaoExpedidorRg();
		}
		return null;
	}
	
	public String getCNPJ() {
		if (this.getOID_2_16_76_1_3_3() != null) {
			return this.getOID_2_16_76_1_3_3().getCNPJ();
		}
		return null;
	}
		
	public String getINSSCei() {
		if (this.getOID_2_16_76_1_3_7() != null) {
			return this.getOID_2_16_76_1_3_7().getINSSCei();
		}
		return null;
	}
}
