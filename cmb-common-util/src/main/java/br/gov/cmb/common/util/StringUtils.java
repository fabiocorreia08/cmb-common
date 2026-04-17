package br.gov.cmb.common.util;

import java.nio.charset.Charset;

public final class StringUtils {
    
    private static final String CHARSET_UTF8 = "UTF-8";
    
    private StringUtils(){}
    
    public static byte[] stringToByteArray(String string) {
        return string.getBytes(Charset.forName(CHARSET_UTF8));
    }

}
