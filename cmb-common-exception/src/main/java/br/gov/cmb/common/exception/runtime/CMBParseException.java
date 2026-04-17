package br.gov.cmb.common.exception.runtime;

public class CMBParseException extends CMBRuntimeException {
	
	private static final long serialVersionUID = 1L;

    public CMBParseException(String msg) {
        super(msg);
    }

    public CMBParseException(Throwable t) {
        super(t);
    }

    public CMBParseException(String msg, Throwable t) {
        super(msg, t);
    }
}
