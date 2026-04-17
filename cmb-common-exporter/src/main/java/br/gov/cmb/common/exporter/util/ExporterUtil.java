package br.gov.cmb.common.exporter.util;

import br.gov.cmb.common.exporter.VO.DocumentoRequestVO;
import br.gov.cmb.common.exporter.VO.DocumentoResponseVO;
import br.gov.cmb.common.rest.client.RestClient;


public final class ExporterUtil {

	private ExporterUtil() {

	}

	public static DocumentoResponseVO exportarRelatorio(String url, DocumentoRequestVO documentoRequestVO){
	      return RestClient.postJson(url, DocumentoResponseVO.class, documentoRequestVO);
	}

}
