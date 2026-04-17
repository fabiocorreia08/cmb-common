package br.gov.cmb.common.exception.runtime;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class DAOException extends CMBRuntimeException {

    private static final long serialVersionUID = 1L;
    
    public DAOException(String msg) {
        super(msg);
    }
    
    public DAOException(Throwable t) {
        super(t);
    }
    
    public DAOException(String msg, Throwable t) {
        super(msg, t);
    }
}
