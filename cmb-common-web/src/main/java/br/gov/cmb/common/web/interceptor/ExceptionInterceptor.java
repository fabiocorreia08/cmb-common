package br.gov.cmb.common.web.interceptor;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.jboss.logging.Logger;

public class ExceptionInterceptor implements Serializable {

    private static final long serialVersionUID = -1127901267196070851L;

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Throwable {
    	final Logger logger = Logger.getLogger(context.getTarget().toString());
        try {
            return context.proceed();
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
    }
}
