package br.gov.cmb.common.rest.security;

import java.util.List;

public interface AuthenticationProvider {

	Authentication authenticate(Authentication Authentication);
	
	List<String> getPermissions();
	
	Authentication getAuthentication();

}
