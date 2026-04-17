package br.gov.cmb.commons.certificado.manager;

import br.gov.cmb.commons.certificado.certificate.ICPBRCertificate;
import br.gov.cmb.commons.certificado.exception.CertificateValidationException;
import br.gov.cmb.commons.certificado.validator.CertificateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.cert.X509Certificate;


public class CertificateManager {
    private static final Logger logger = LoggerFactory.getLogger(CertificateManager.class);

    private ICPBRCertificate certificate;

    public CertificateManager(ICPBRCertificate certificate) {
        this.certificate = certificate;
    }

    public CertificateManager(X509Certificate x509Certificate) {
        this.certificate = new ICPBRCertificate(x509Certificate);
    }

    public boolean validate(CertificateValidator... validators) throws CertificateValidationException {
        for (CertificateValidator certificateValidator : validators) {
            certificateValidator.validate(certificate);
            logger.info(certificateValidator.getClass().getName() + ": OK" );
        }

        return true;
    }
}
