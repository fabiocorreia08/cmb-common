package br.gov.cmb.commons.certificado.exception;

public class DigitalSignedMessageException extends Exception {
	private static final long serialVersionUID = 663816749051486774L;
	
	
	public DigitalSignedMessageException(String message) {
		super(message);
	}
	
	public DigitalSignedMessageException(String message, Throwable t) {
		super(message, t);
	}
}
