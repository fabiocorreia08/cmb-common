package br.gov.cmb.common.filemanager.util;

import java.text.Normalizer;

import br.gov.cmb.common.filemanager.vo.FileMetadata;

public class StringUtils {

	public static String removerAcentos(String palavra) {
		return Normalizer.normalize(palavra, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	public static Object criarHeaderDownload(FileMetadata file) {
		return new StringBuffer().append("attachment;filename=").append(file.getFilename()).append(".").append(file.getMimeType()).toString(); 
	}
}
