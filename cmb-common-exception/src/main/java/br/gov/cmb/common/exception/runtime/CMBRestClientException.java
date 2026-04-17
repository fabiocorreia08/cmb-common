package br.gov.cmb.common.exception.runtime;

public class CMBRestClientException extends CMBRuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CMBRestClientException(String msg) {
        super(msg);
    }
    
    public CMBRestClientException(Throwable t) {
        super(t);
    }
    
    public CMBRestClientException(String msg, Throwable t) {
        super(msg, t);
    }
}
