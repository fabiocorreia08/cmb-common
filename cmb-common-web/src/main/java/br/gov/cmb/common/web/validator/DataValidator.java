package br.gov.cmb.common.web.validator;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.gov.cmb.common.web.util.MessagesUtils;

/**
 * @deprecated Utilizar tag validateOrder do omnifaces
 * @author arbueno
 *
 */
@Deprecated
@FacesValidator(value = "dataValidator")
public class DataValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
        Object valorDataInicio = component.getAttributes().get("data");
        
        if (valorDataInicio == null) {
            return;
        }

        Date dataInicio = (Date) valorDataInicio;
        Date dataTermino = (Date) value;
        if (dataTermino.before(dataInicio)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
                    MessagesUtils.getMessage("label.curriculo.experiencia.mensagem.critica.data.termino.posterior.a.inicio")));
        }
      
    }
}
