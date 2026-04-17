package br.gov.cmb.commons.certificado.validator;


import br.gov.cmb.commons.certificado.certificate.ICPBRCertificate;
import br.gov.cmb.commons.certificado.exception.CertificateValidationException;

public class PeriodValidator implements CertificateValidator {

	@Override
	public void validate(ICPBRCertificate cert) throws CertificateValidationException {
		if (!cert.isPeriodValid()) {
			throw new CertificateValidationException("Certificado fora do prazo de validade!");
		}

	}

	
}
