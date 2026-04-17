package br.gov.cmb.common.rest.security;

import java.io.Serializable;
import java.util.List;

public class Authentication implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	
	private String name;

	private List<String> permissions;

	private Object details;

	public Authentication() {
	}
	
	public Authentication(String username) {
		this.username = username;
	}

	public Authentication(String username, List<String> permissions, Object details) {
		this(username);
		this.permissions = permissions;
		this.details = details;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public Object getDetails() {
		return details;
	}

	public void setDetails(Object details) {
		this.details = details;
	}


}
