package br.gov.cmb.common.exception.runtime;

public class CMBSecurityException extends CMBRuntimeException {
	
	private static final long serialVersionUID = 1L;

    public CMBSecurityException(String msg) {
        super(msg);
    }

    public CMBSecurityException(Throwable t) {
        super(t);
    }

    public CMBSecurityException(String msg, Throwable t) {
        super(msg, t);
    }
}
