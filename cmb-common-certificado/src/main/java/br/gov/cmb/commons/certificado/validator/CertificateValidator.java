package br.gov.cmb.commons.certificado.validator;


import br.gov.cmb.commons.certificado.certificate.ICPBRCertificate;
import br.gov.cmb.commons.certificado.exception.CertificateValidationException;

public interface CertificateValidator {
	
	void validate(ICPBRCertificate cert) throws CertificateValidationException;

}
