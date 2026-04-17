package br.gov.cmb.commons.certificado.crl;

import br.gov.cmb.commons.certificado.certificate.ICPBRCertificate;

import java.security.cert.X509CRL;

public interface CRLRepository {
	X509CRL getX509CRL(ICPBRCertificate certificate);
}
