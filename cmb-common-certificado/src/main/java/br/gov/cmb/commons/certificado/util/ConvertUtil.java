package br.gov.cmb.commons.certificado.util;

import java.math.BigInteger;

public class ConvertUtil {
	
	public static String toHexa(BigInteger value) {
		String result = null;
		
        if (value != null) {
	        result = value.toString(16);
	
	        if (result.length() % 2 == 1) {
	        	result = "0" + result;
	        }

        	result = result.toUpperCase();
        }
        
        return result;
    }

}
