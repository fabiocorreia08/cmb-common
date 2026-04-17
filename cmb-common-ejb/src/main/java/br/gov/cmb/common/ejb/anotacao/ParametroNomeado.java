package br.gov.cmb.common.ejb.anotacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParametroNomeado {
    
    String value() default "" ;

    boolean like() default false;
    
}
