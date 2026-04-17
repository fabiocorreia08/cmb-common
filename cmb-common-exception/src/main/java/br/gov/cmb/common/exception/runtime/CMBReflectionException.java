package br.gov.cmb.common.exception.runtime;

public class CMBReflectionException extends CMBRuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CMBReflectionException(String msg) {
        super(msg);
    }
    
    public CMBReflectionException(Throwable t) {
        super(t);
    }
    
    public CMBReflectionException(String msg, Throwable t) {
        super(msg, t);
    }
}
