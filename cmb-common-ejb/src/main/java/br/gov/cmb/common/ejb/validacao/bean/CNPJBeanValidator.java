package br.gov.cmb.common.ejb.validacao.bean;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import br.gov.cmb.common.ejb.validacao.annotation.CNPJ;
import br.gov.cmb.common.util.ValidadorUtil;

public class CNPJBeanValidator implements ConstraintValidator<CNPJ, String> {
	
	private boolean formatted;	

	@Override
	public void initialize(CNPJ cnpj) {
		this.formatted = cnpj.formatted();
	}

	@Override
	public boolean isValid(String cnpj, ConstraintValidatorContext context) {
		if (StringUtils.isNotBlank(cnpj)) {
			return ValidadorUtil.isCNPJValido(cnpj, formatted);
		} else {
			return true;
		}
	}

}
