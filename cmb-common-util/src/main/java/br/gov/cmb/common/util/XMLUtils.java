package br.gov.cmb.common.util;

import java.io.File;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import br.gov.cmb.common.exception.runtime.CMBParseException;

public final class XMLUtils {
	
	private XMLUtils() {
	}
	
	@SuppressWarnings("unchecked")
	public static <T>T parse(File arquivo, Class<T> classeDeRetorno){
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(classeDeRetorno);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			return (T) jaxbUnmarshaller.unmarshal(arquivo);
		} catch (JAXBException e) {
			throw new CMBParseException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T>T parse(InputStream arquivo, Class<T> classeDeRetorno){
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(classeDeRetorno);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			return (T) jaxbUnmarshaller.unmarshal(arquivo);
		} catch (JAXBException e) {
			throw new CMBParseException(e);
		}
	
	}
}
