package br.gov.cmb.commons.certificado.exception;

public class CertificateValidationException extends Exception {
	private static final long serialVersionUID = -1277808482011925845L;
	
	public CertificateValidationException(String message) {
		super(message);
	}
	
	public CertificateValidationException(String message, Throwable t) {
		super(message);
	}

	
}
