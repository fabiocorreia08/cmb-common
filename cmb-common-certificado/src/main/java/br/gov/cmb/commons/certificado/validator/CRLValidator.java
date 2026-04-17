package br.gov.cmb.commons.certificado.validator;


import br.gov.cmb.commons.certificado.certificate.ICPBRCertificate;
import br.gov.cmb.commons.certificado.crl.CRLRepository;
import br.gov.cmb.commons.certificado.exception.CertificateValidationException;

import java.security.cert.X509CRL;

public class CRLValidator implements CertificateValidator {
	private CRLRepository repository = null;
	
	public CRLValidator(CRLRepository repository) {
		this.repository = repository;
	}
	
	
	@Override
	public void validate(ICPBRCertificate cert) throws CertificateValidationException {

		X509CRL crl = repository.getX509CRL(cert);
        if (crl == null) {
            throw new CertificateValidationException("Não foi possível verificar se o certificado está Revogado. Nenhuma lista válida foi encontrada.");
        }
        
        if (crl.isRevoked(cert.getCertificate())) {
                throw new CertificateValidationException("Certificado Revogado");
        }
	}

}
