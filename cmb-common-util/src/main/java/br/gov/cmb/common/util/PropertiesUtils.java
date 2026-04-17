package br.gov.cmb.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import br.gov.cmb.common.exception.runtime.CMBPropertiesUtilsException;

public final class PropertiesUtils {
	private static final String ARQUIVO_PROPERTIES_DEFAULT = "configuracao.properties";
	private static Properties properties = new Properties();

	private PropertiesUtils() {
	}
	
	public static String getEnvProperty(String key) {
	    return System.getProperty(key);
	}

	public static String getProperty(String key) {
	    String value = getEnvProperty(key);
	    
	    if (value == null) {
	        value = getConfiguracaoProperties().getProperty(key);
	    }
	    
		return value;
	}
	
	public static boolean getBooleanProperty(String key) {
	    String property = getProperty(key);
	    return Boolean.parseBoolean(property);
	}

	public static Properties getConfiguracaoProperties() {
		try {
			InputStream arquivo = getArquivoConfiguracao();
			properties.load(arquivo);
		} catch (URISyntaxException | IOException e) {
			throw new CMBPropertiesUtilsException(e);
		}
		return properties;
	}

	private static InputStream getArquivoConfiguracao() throws URISyntaxException {
		return StreamUtils.recuperarStreamDoProjeto(ARQUIVO_PROPERTIES_DEFAULT);
	}

}
