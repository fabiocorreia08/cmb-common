package br.gov.cmb.commons.certificado.exception;

public class CRLRepositoryException extends RuntimeException {
	private static final long serialVersionUID = 8214046480499697894L;

	public CRLRepositoryException(String message) {
		super(message);
	}
	
	public CRLRepositoryException(String message, Throwable t) {
		super(message, t);
	}
}
