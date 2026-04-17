package br.gov.cmb.common.util;

import javax.enterprise.inject.spi.CDI;

public final class CDIUtils {
	
	private CDIUtils() { }
	
	public static <T> T recuperarBean(Class<T> classDeRetorno){
		return CDI.current().select(classDeRetorno).get();
	}
}
