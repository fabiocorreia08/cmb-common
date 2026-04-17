package br.gov.cmb.common.exception.runtime;

public class DataModelException extends CMBRuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public DataModelException(String msg) {
        super(msg);
    }
    
    public DataModelException(Throwable t) {
        super(t);
    }
    
    public DataModelException(String msg, Throwable t) {
        super(msg, t);
    }
}
