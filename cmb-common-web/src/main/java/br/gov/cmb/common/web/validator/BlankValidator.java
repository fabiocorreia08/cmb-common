package br.gov.cmb.common.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.gov.cmb.common.web.util.MessagesUtils;

@FacesValidator
public class BlankValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String valor = (String) value;
		if(valor != null && valor.trim().length() == 0) {
			String label = (String) component.getAttributes().get("label");
			String mensagem = MessagesUtils.getMessage("error.message.blank.validator", label);
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
            throw new ValidatorException(facesMessage);
		}
	}

	
}
