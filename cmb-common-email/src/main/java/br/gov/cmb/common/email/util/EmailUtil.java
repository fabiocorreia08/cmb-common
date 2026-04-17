package br.gov.cmb.common.email.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.gov.cmb.common.email.VO.EmailVO;
import br.gov.cmb.common.rest.client.RestClient;


public final class EmailUtil {

	private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);
	
	private EmailUtil() {

	}

	public static void enviarEmail(String url, EmailVO emailVO){
		RestClient.postJson(url, String.class, emailVO);
	}
	
	public static void enviarEmailAnexo(String url, EmailVO emailVO){
		logger.error("Tentando Enviar Email");
		RestClient.postJsonAnexo(url, String.class, emailVO);
	}

}
