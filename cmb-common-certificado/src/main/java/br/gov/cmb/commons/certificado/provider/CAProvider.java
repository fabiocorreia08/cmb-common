package br.gov.cmb.commons.certificado.provider;

import java.security.cert.X509Certificate;
import java.util.Collection;

public interface CAProvider {
	Collection<X509Certificate> getCertificates();
}
