package br.gov.cmb.commons.certificado.validator;


import br.gov.cmb.commons.certificado.certificate.ICPBRCertificate;
import br.gov.cmb.commons.certificado.exception.CAManagerException;
import br.gov.cmb.commons.certificado.exception.CertificateValidationException;
import br.gov.cmb.commons.certificado.provider.CAProvider;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collection;

public class CARootValidator implements CertificateValidator {
	
	CAProvider caProvider = null;
	
	public CARootValidator(CAProvider caProvider) throws CertificateValidationException {
		
		if (caProvider == null) {
			throw new CertificateValidationException("Não foi definido nenhum provider CA.");
		}
		
		this.caProvider = caProvider;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void validate(ICPBRCertificate cert) throws CertificateValidationException {
		
		Collection<X509Certificate> cas = caProvider.getCertificates();
		X509Certificate certificate = cert.getCertificate();
        if (cas == null || cas.size() <= 0) {
            throw new CertificateValidationException("Não há informações das autoridades certificadoras para validar o certificado informado.");
        }
        Certificate ca = null;
        try {
            ca = CAValidatorHelper.getCAFromCertificate(cas, certificate);
        } catch (CAManagerException error) {
            throw new CertificateValidationException("Não foi possível localizar o certificado da autoridade do certificado informado [" + certificate.getIssuerDN().getName() + "]", error);
        }
        if (ca == null) {
            throw new CertificateValidationException("Autoridade Certificadora do certificado em questao não é confiável.");
        }
	}
	
}
