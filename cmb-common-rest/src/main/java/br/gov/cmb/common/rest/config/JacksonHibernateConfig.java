package br.gov.cmb.common.rest.config;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@Provider
public class JacksonHibernateConfig implements ContextResolver<ObjectMapper>{
	

	@Override
	public ObjectMapper getContext(Class<?> clazz) {
		ObjectMapper objectMapper = new ObjectMapper();
		Hibernate5Module hibernate5Module = new Hibernate5Module();
		objectMapper.registerModule(hibernate5Module);
		return objectMapper;
	}

}
