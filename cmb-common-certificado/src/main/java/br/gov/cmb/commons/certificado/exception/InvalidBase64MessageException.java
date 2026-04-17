package br.gov.cmb.commons.certificado.exception;

public class InvalidBase64MessageException extends Exception {
	private static final long serialVersionUID = -547540622206608918L;

	public InvalidBase64MessageException(String message) {
		super(message);
	}
}
