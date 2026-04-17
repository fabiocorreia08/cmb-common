package br.gov.cmb.commons.certificado.exception;

public class CAManagerException extends RuntimeException {
	private static final long serialVersionUID = -1277808482011925845L;
	
	public CAManagerException(String message) {
		super(message);
	}

	public CAManagerException(String message, Throwable t) {
		super(message, t);
	}
	
}
