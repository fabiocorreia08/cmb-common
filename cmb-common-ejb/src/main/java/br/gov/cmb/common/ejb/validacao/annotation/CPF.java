package br.gov.cmb.common.ejb.validacao.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.gov.cmb.common.ejb.validacao.bean.CPFBeanValidator;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Constraint(validatedBy = CPFBeanValidator.class)
public @interface CPF {
	
	String message() default "CPF inválido";

	boolean formatted() default false;

	boolean ignoreRepeated() default false;
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
