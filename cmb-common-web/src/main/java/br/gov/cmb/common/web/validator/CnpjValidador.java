package br.gov.cmb.common.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang.StringUtils;

import br.gov.cmb.common.util.ValidadorUtil;
import br.gov.cmb.common.web.util.MessagesUtils;

@FacesValidator(value = "cnpjValidator")
public class CnpjValidador implements Validator {
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object valorTela) throws ValidatorException {
	    if (valorTela != null && StringUtils.isNotBlank(String.valueOf(valorTela)) && !ValidadorUtil.isCNPJValido(String.valueOf(valorTela))) {
    		((UIInput) arg1).setValid(false);
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
            		MessagesUtils.getMessage("error.message.CNPJ.invalido")));
	    }
	}
}