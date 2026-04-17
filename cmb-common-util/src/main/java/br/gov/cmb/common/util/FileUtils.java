package br.gov.cmb.common.util;

import java.io.File;
import java.net.URL;

public final class FileUtils {
	
	private FileUtils() {
	}
	
	public static File recuperarArquivoDoProjeto(String nomeArquivo){
		URL url = Thread.currentThread().getContextClassLoader().getResource(nomeArquivo);
        return new File(url.getPath());
    }
	
}
