package br.gov.cmb.commons.certificado.certificate;


import br.gov.cmb.commons.certificado.oid.OIDGeneric;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Informacoes extras do Certificado (ICP-BRASIL - DOC-ICP-04 versao 5.3)
 * atraves do SubjectAlternativeNames do certificado
 * 
 * Abrange Certificado Pessoa Fisica, Pessoa Jurudica e Equipamentos
 * 
 * URL:
 *  http://www.iti.gov.br/images/legislacao/Docicp/DOC-ICP-04_-_Versao_5.3_-_REQ._MIN._PARA_AS_PCs_NA_ICP-BRASIL_29.04.2014.pdf
 *  
 * @author pjneves
 *
 */
public class ICPBRCertificateExtras {
	private static final int RFC822NAME = 1;

	private static final int OTHERNAME = 0;

	private final Map<String, OIDGeneric> extras = new HashMap<>();

	private String email = null;
	
	public ICPBRCertificateExtras(X509Certificate certificate) {
		try {
			if (certificate.getSubjectAlternativeNames() != null) {        	
        	
	            for (List<?> list : certificate.getSubjectAlternativeNames()) {
	                Integer type = (Integer) list.get(0);
	                Object extra = list.get(1);
	
	                switch (type) {
	                	case OTHERNAME: // OtherName - contem CPF, CNPJ etc.  
	                		byte[] data = (byte[]) extra;
		                    OIDGeneric oid = OIDGeneric.getInstance(data);
		                    extras.put(oid.getOid(), oid);
		                    break;
	                	case RFC822NAME : // rfc822Name - usado para email                    	
	                    	email = (String) extra;
	                    	break;
	                    default:
	                    	break;
	                    	
	                }
	            }
			}
    	} catch (CertificateParsingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }
	
	public String getEmail() {
		return email;
	}
	
	public boolean hasExtras() {
		return extras.size() != 0;
	}
	
	public boolean isCertificatePF() {
        return extras.get("2.16.76.1.3.1") != null;
    }
	
	public boolean isCertificatePJ() {
        return extras.get("2.16.76.1.3.4") != null;
    }
	
	public boolean isCertificateEquipment() {
        return extras.get("2.16.76.1.3.8") != null;
    }
	
	
	public OIDGeneric getOtherName(String oid) {
		return extras.get(oid);
	}
	
	public ICPBRCertificateExtrasPF getICPBRCertificateExtrasPF() {
		return new ICPBRCertificateExtrasPF(this);
	}
	
	public ICPBRCertificateExtrasPJ getICPBRCertificateExtrasPJ() {
		return new ICPBRCertificateExtrasPJ(this);
	}
	
	public ICPBRCertificateExtrasEquip getICPBRCertificateExtrasEquip() {
		return new ICPBRCertificateExtrasEquip(this);
	}
}
