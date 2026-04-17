package br.gov.cmb.common.exception;

public class CMBException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public CMBException(String msg) {
        super(msg);
    }
    
    public CMBException(Throwable t) {
        super(t);
    }
    
    public CMBException(String msg, Throwable t) {
        super(msg, t);
    }
    
}
