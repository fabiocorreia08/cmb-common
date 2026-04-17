package br.gov.cmb.common.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public final class StreamUtils {
	
    private StreamUtils() {
	}
	
	public static InputStream recuperarStreamDoProjeto(String nomeArquivo){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(nomeArquivo);
       
	}
	
	public static InputStream stringToInputStream(String string) {
        return new ByteArrayInputStream(StringUtils.stringToByteArray(string));
	}
	
}
