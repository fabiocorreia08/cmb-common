package br.gov.cmb.commons.certificado.validator;


import br.gov.cmb.commons.certificado.certificate.ICPBRCertificate;
import br.gov.cmb.commons.certificado.exception.CertificateValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CertificateTypeValidator implements CertificateValidator {
	private List<String> types = new ArrayList<>();

	public CertificateTypeValidator(String... validTypes) {
		this.types = Arrays.asList(validTypes);
	}

	@Override
	public void validate(ICPBRCertificate cert) throws CertificateValidationException {
		if (!types.contains(cert.getNivelCertificado())) {
			throw new CertificateValidationException("Certificado enviado não é do Tipo Válido!");
		}

	}

	
}
