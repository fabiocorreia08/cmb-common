package br.gov.cmb.common.rest.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class AuthenticationContext {

	private static final String ANONYMOUS_USERNAME = "ANONYMOUS";
	
	private Authentication authentication;
	
	public boolean isAuthenticaded() {
		return authentication != null;
	}
	
	public String getUsername() {
		return isAuthenticaded() ? authentication.getUsername() : ANONYMOUS_USERNAME;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
	public Object getAuthenticationDetails() {
		return authentication.getDetails();
	}

}
