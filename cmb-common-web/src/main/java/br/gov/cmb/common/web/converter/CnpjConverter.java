package br.gov.cmb.common.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.google.common.base.Strings;

import br.gov.cmb.common.util.FormatadorUtils;

@FacesConverter("cnpjConverter")
public class CnpjConverter implements Converter {

	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return FormatadorUtils.limparFormatacao(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object obj) {
        
    	if (obj != null) {
        	String value = String.valueOf(obj);
        	if (!Strings.isNullOrEmpty(value)) {
        		return FormatadorUtils.formatarCnpj(value);
        	}else return "";
        }
    	return null;
    }

}
