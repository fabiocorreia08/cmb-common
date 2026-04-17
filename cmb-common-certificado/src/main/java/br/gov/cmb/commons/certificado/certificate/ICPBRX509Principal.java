package br.gov.cmb.commons.certificado.certificate;

import javax.security.auth.x500.X500Principal;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

public class ICPBRX509Principal {
	private static final String ORGANIZATION_UNIT_ATTRIBUTE_NAME = "OU";
	private static final String COMMON_NAME_ATTRIBUTE_NAME = "CN";
	private static final String ORGANIZATION_ATTRIBUTE_NAME = "O";
	private static final String COUNTRY_ATTRIBUTE_NAME = "C";
	private X500Principal principal;
	private Properties principalNameProperties;

	
	public ICPBRX509Principal(X500Principal principal) throws IOException {
        super();
        this.principal = principal;
        this.setPrincipalNameProperties();
    }
	
	public X500Principal getPrincipal() {
		return principal;
	}
	
	public String getPrincipalName() {
		return principal.getName();
	}
	
	private void setPrincipalNameProperties() throws IOException {
		principalNameProperties = new Properties();
        ByteArrayInputStream bis = new ByteArrayInputStream(getPrincipalName().replaceAll(",", "\n").getBytes());
        principalNameProperties.load(bis);
        bis.close();
	}
	
	public String getCountry() {
		return principalNameProperties.getProperty(COUNTRY_ATTRIBUTE_NAME);
	}
	
	public String getOrganization() {
		return principalNameProperties.getProperty(ORGANIZATION_ATTRIBUTE_NAME);
	}
	
	public String getCommonName() {
		return principalNameProperties.getProperty(COMMON_NAME_ATTRIBUTE_NAME);
	}
	
	public String getOrganizationUnit() {
		return principalNameProperties.getProperty(ORGANIZATION_UNIT_ATTRIBUTE_NAME);
	}
	
	public String getAttribute(String attributeName) {
		return principalNameProperties.getProperty(attributeName);
	}
}
