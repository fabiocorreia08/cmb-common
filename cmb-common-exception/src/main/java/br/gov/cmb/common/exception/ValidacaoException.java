package br.gov.cmb.common.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.ApplicationException;

import br.gov.cmb.common.exception.runtime.CMBRuntimeException;

@ApplicationException(rollback=true)
public class ValidacaoException extends CMBRuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private static final String FORAM_ENCONTRADOS_ERROS = "Foram encontrados erros";
    
    private final List<ValidacaoException> errosDeValidacao;
    
    public ValidacaoException() {
        super(FORAM_ENCONTRADOS_ERROS);
        errosDeValidacao = new ArrayList<ValidacaoException>();
    }

    public ValidacaoException(String msg) {
    	super(msg);
    	errosDeValidacao = new ArrayList<ValidacaoException>();
        ValidacaoException validacaoException = new ValidacaoException(msg, "");
        adicionarErrosEncontrados(Arrays.asList(validacaoException));
    }    
    
    public ValidacaoException(String msg, String msg2) {
    	super(msg, "");
    	errosDeValidacao = new ArrayList<ValidacaoException>();
    }
    
    public ValidacaoException(Throwable t) {
        super(t);
        errosDeValidacao = new ArrayList<ValidacaoException>();
    }
    
    public ValidacaoException(String msg, Throwable t) {
        super(msg, t);
        errosDeValidacao = new ArrayList<ValidacaoException>();
    }
    
    
    public final void adicionarErrosEncontrados(List<ValidacaoException> erros){
        this.errosDeValidacao.addAll(erros);
    }

    public List<ValidacaoException> getErrosDeValidacao() {
        return errosDeValidacao;
    }
    
    
}
