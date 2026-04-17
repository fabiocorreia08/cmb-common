package br.gov.cmb.commons.certificado.util;


import br.gov.cmb.commons.certificado.exception.CRLRepositoryException;

import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class RepositoryUtils {
	
	private static final Logger log = Logger.getLogger(RepositoryUtils.class.getName());

	public static String urlToHexaMD5(String url) {
		String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(url.getBytes());
            // convert to hexa
            BigInteger bigInt = new BigInteger(1, md.digest());            
            result = bigInt.toString(16);
        } catch (NoSuchAlgorithmException e) {
        }
        
        return result;
    }
	
	   public static void saveURL(String sUrl, File destinationFile) {
	        URL url;
	        byte[] buf;
	        int byteRead;
	        BufferedOutputStream outStream = null;
	        URLConnection uCon = null;
	        InputStream is = null;
	        try {
	            url = new URL(sUrl);
	            uCon = url.openConnection();
	            uCon.setConnectTimeout(5000);
	            is = uCon.getInputStream();
	            outStream = new BufferedOutputStream(new FileOutputStream(destinationFile));
	            buf = new byte[1024];
	            while ((byteRead = is.read(buf)) != -1) {
	                outStream.write(buf, 0, byteRead);
	            }
	        } catch (MalformedURLException e) {
	            throw new CRLRepositoryException("URL [" + sUrl + "] is Malformed", e);
	        } catch (FileNotFoundException e) {
	            throw new CRLRepositoryException("File [" + sUrl + "] is not found", e);
	        } catch (IOException e) {
	            log.info("Error in  url openConnection [" + sUrl + "]" + e.getMessage());
	        } finally {
	            try {
	                if (is != null) {
	                    is.close();
	                }
	                if (outStream != null) {
	                    outStream.close();
	                }
	            } catch (Throwable e) {
	                throw new CRLRepositoryException("Is not possible close conection [" + sUrl + "]", e);
	            }
	        }
	    }
}
