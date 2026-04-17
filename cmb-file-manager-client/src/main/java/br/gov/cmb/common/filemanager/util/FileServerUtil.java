package br.gov.cmb.common.filemanager.util;

import java.util.regex.Pattern;

import br.gov.cmb.common.filemanager.vo.FileMetadata;

public class FileServerUtil {

	public static FileMetadata montarFileData(String nmArquivo, byte[] byteArray, String workspace) {
		String[] splitArquivo = nmArquivo.split(Pattern.quote("."));
		FileMetadata fm = new FileMetadata();

		fm.setPath(workspace);
		fm.setFilename(splitArquivo[0]);
		fm.setMimeType(splitArquivo[1]);
		fm.setFileSize(Long.valueOf(byteArray.length));
		fm.setArquivo(byteArray);
		
		return fm;
	}
	
	
}
