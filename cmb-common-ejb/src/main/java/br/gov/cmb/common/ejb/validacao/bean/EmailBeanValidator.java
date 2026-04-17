package br.gov.cmb.common.ejb.validacao.bean;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import br.gov.cmb.common.ejb.validacao.annotation.Email;
import br.gov.cmb.common.util.ValidadorUtil;

public class EmailBeanValidator implements ConstraintValidator<Email, String> {

	@Override
	public void initialize(Email email) {
		
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		if (StringUtils.isNotBlank(email)) {
			return ValidadorUtil.isEmailValido(email);
		} else {
			return true;
		}
	}

}
