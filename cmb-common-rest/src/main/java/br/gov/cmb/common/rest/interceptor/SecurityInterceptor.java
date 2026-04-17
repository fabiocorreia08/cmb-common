package br.gov.cmb.common.rest.interceptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.gov.cmb.common.rest.annotation.Secured;
import br.gov.cmb.common.rest.security.Authentication;
import br.gov.cmb.common.rest.security.AuthenticationContext;
import br.gov.cmb.common.rest.security.AuthenticationProvider;

@Interceptor
@Secured
public class SecurityInterceptor {


	@Inject
	private Instance<AuthenticationProvider> authenticationProviderInstance;

	@Inject
	private AuthenticationContext authenticationContext;

	@AroundInvoke
	public Object verifyAccess(InvocationContext ctx) throws Exception {
		Secured annotation = ctx.getMethod().getAnnotation(Secured.class);
		List<String> permissions = Arrays.asList(annotation.value());
		List<String> userPermissions = getUserPermissions();
		
		if (!permissions.isEmpty() && Collections.disjoint(permissions, userPermissions)) {
			Response response = Response.status(Status.FORBIDDEN).build();
			throw new ForbiddenException(response);
		}

		registerAuthenticationContext(annotation);

		return ctx.proceed();

	}


	private List<String> getUserPermissions() {
		try {
			 return getAuthenticationProvider().getPermissions();
		} catch (Exception e) {
			Response response = Response.status(Status.UNAUTHORIZED).build();
			throw new NotAuthorizedException(response);
		}
	}

	private void registerAuthenticationContext(Secured annotation) {
		if (annotation != null) {
			Authentication authentication = getAuthenticationProvider().getAuthentication();
			authenticationContext.setAuthentication(authentication);
		}
	}


	private AuthenticationProvider getAuthenticationProvider() {
		return authenticationProviderInstance.get();
	}
}
