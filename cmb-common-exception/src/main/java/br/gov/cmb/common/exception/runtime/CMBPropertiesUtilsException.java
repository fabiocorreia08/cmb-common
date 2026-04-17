package br.gov.cmb.common.exception.runtime;

public class CMBPropertiesUtilsException extends CMBRuntimeException {

    private static final long serialVersionUID = 1L;

    public CMBPropertiesUtilsException(String msg) {
        super(msg);
    }

    public CMBPropertiesUtilsException(Throwable t) {
        super(t);
    }

    public CMBPropertiesUtilsException(String msg, Throwable t) {
        super(msg, t);
    }
}
