package br.gov.cmb.common.rest.client;

import java.io.Serializable;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.gov.cmb.common.exception.runtime.CMBRestClientException;

public final class RestClient {

	private static final Logger logger = LoggerFactory.getLogger(RestClient.class);

	private RestClient() {

	}

	public static <T>T post(String url, Class<T> tipoRetorno, Serializable parametro, String mediaTypeParametro) {
		Response response = getWebTarget(url).request().post(Entity.entity(parametro, mediaTypeParametro));
		return getResponse(tipoRetorno, response);
	}

	public static <T>T put(String url, Class<T> tipoRetorno, Serializable parametro, String mediaTypeParametro) {
		Response response = getWebTarget(url).request().put(Entity.entity(parametro, mediaTypeParametro));
		return getResponse(tipoRetorno, response);
	}

	public static <T>T postJson(String url, Class<T> tipoRetorno, Serializable parametro) {
		return post(url, tipoRetorno, parametro, MediaType.APPLICATION_JSON);
	}

	
	/*
	
	Response response = null;
    ResteasyClient client = new ResteasyClientBuilder().build();
    ResteasyWebTarget target = client.target(URL);

    MultipartFormDataOutput mdo = new MultipartFormDataOutput();
    mdo.addFormData("my_file", new FileInputStream(new File("C:/sample/sample.zip")), MediaType.APPLICATION_OCTET_STREAM_TYPE);
    GenericEntity<MultipartFormDataOutput> entity = new GenericEntity<MultipartFormDataOutput>(mdo) { };

    response = target.request().post(Entity.entity(entity,MediaType.MULTIPART_FORM_DATA));
    
    */
	public static <T>T postJsonAnexo(String url, Class<T> tipoRetorno, Serializable parametro) {
		logger.error("postJsonAnexo");
		try {	
			return post(url, tipoRetorno, parametro, MediaType.MULTIPART_FORM_DATA);
		}
		catch(Exception e) {
			logger.error("Deu ruim !!!");
			logger.error(e.toString());
		}
		return null;
	}



	public static <T>T putJson(String url, Class<T> tipoRetorno, Serializable parametro) {
		return put(url, tipoRetorno, parametro, MediaType.APPLICATION_JSON);
	}

	public static <T>T post(String url, Class<T> tipoRetorno) {
		return post(url, tipoRetorno, "", MediaType.TEXT_PLAIN);
	}

	public static <T>T put(String url, Class<T> tipoRetorno) {
		return put(url, tipoRetorno, "", MediaType.TEXT_PLAIN);
	}

	public static <T>T get(String url, Class<T> tipoRetorno) {
		Response response = getWebTarget(url).request().get();
		return getResponse(tipoRetorno, response);
	}

	private static <T>T getResponse(Class<T> tipoRetorno, Response response) {
		if (Response.Status.Family.familyOf(response.getStatus()).equals(Response.Status.Family.SUCCESSFUL)) {
			T retorno = response.readEntity(tipoRetorno);
			return retorno;
		} else {
			throw new CMBRestClientException("Ocorreu um erro ao consumir servico rest, HTTP Error Code: " + response.getStatus());
		}
	}

	private static ResteasyWebTarget getWebTarget(String url) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		return client.target(url);
	}

}
