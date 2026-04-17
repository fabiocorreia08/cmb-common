package br.gov.cmb.common.exception.runtime;

import java.lang.RuntimeException;

public class CMBRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public CMBRuntimeException(String msg) {
        super(msg);
    }
    
    public CMBRuntimeException(Throwable t) {
        super(t);
    }
    
    public CMBRuntimeException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public CMBRuntimeException(String msg, String msg2) {
        super(msg, null);
    }
    
}
