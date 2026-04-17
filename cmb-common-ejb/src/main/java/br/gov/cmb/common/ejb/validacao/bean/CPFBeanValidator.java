package br.gov.cmb.common.ejb.validacao.bean;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import br.gov.cmb.common.ejb.validacao.annotation.CPF;
import br.gov.cmb.common.util.ValidadorUtil;

public class CPFBeanValidator implements ConstraintValidator<CPF, String> {
	
	private boolean formatted;	
	private boolean ignoreRepeated;

	@Override
	public void initialize(CPF cpf) {
		this.formatted = cpf.formatted();
		this.ignoreRepeated = cpf.ignoreRepeated();		
	}

	@Override
	public boolean isValid(String cpf, ConstraintValidatorContext context) {
		if (StringUtils.isNotBlank(cpf)) {
			return ValidadorUtil.isCPFValido(cpf, formatted, ignoreRepeated);
		} else {
			return true;
		}
	}

}
