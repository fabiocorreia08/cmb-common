package br.gov.cmb.common.filemanager.client;

import br.gov.cmb.common.filemanager.vo.ArquivoVO;
import br.gov.cmb.common.filemanager.vo.FileMetadata;
import br.gov.cmb.common.infra.RestClient;

public final class FileServerClient {
	
	private static final String END_POINT_DOWNLOAD = "/api/arquivos/download/";
	private static final String END_POINT_UPLOAD = "/api/arquivos/upload";
	
	private String url;
	private String siglaSistema;
	
	public FileServerClient(String url, String siglaSistema) {
		this.url = url;
		this.siglaSistema = siglaSistema;
	}

	public String enviaArquivo(FileMetadata fileMetadata) {
		fileMetadata.setSiglaSistema(this.siglaSistema);
		
		String urlServer = new StringBuffer().append(this.url).append(END_POINT_UPLOAD).toString();
		ArquivoVO arquivo = RestClient.postJson(urlServer, ArquivoVO.class, fileMetadata);
		return arquivo.getCodigo();
	}

	public FileMetadata downloadArquivo(String arquivo) {
		String url = new StringBuffer().append(this.url).append(END_POINT_DOWNLOAD).append(arquivo).toString();
		return RestClient.get(url, FileMetadata.class);
	}


}
