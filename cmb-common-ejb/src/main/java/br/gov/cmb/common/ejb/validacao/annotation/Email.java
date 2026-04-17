package br.gov.cmb.common.ejb.validacao.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.gov.cmb.common.ejb.validacao.bean.EmailBeanValidator;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Constraint(validatedBy = EmailBeanValidator.class)
public @interface Email {
	
	String message() default "E-mail inválido";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
