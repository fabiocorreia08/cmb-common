package br.gov.cmb.common.exception.runtime;

public class ServiceLocaleException extends CMBRuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public ServiceLocaleException(String msg) {
        super(msg);
    }
    
    public ServiceLocaleException(Throwable t) {
        super(t);
    }
    
    public ServiceLocaleException(String msg, Throwable t) {
        super(msg, t);
    }
    
}
