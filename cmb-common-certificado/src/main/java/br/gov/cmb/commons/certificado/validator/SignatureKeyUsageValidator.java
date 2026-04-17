package br.gov.cmb.commons.certificado.validator;


import br.gov.cmb.commons.certificado.certificate.ICPBRCertificate;
import br.gov.cmb.commons.certificado.exception.CertificateValidationException;

public class SignatureKeyUsageValidator implements CertificateValidator {

	@Override
	public void validate(ICPBRCertificate cert) throws CertificateValidationException {

		if (!cert.getKeyUsage().isDigitalSignature()) {
			throw new CertificateValidationException("Certificado não pode ser utilizado para assinar mensagem");
		}

	}

}
