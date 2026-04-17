package br.gov.cmb.commons.certificado.util;

import org.bouncycastle.util.encoders.Base64;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;

public class PKIXUtils {
	
	public static boolean isBase64(String dataEnc) {
		try {
			Base64.decode(dataEnc);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isX509Certificate(Certificate certificateTmpValue) {
		return certificateTmpValue instanceof X509Certificate;
	}
	
	
	/**
	 * Checks whether given X.509 certificate is self-signed.
	 */
	public static boolean isSelfSigned(X509Certificate cert) throws CertificateException, NoSuchAlgorithmException, NoSuchProviderException {
		try {
			// Try to verify certificate signature with its own public key
			PublicKey key = cert.getPublicKey();
			cert.verify(key);	
			return true;
		} catch (SignatureException sigEx) {
			// Invalid signature --> not self-signed
			return false;
		} catch (InvalidKeyException keyEx) {
			// Invalid key --> not self-signed
			return false;
		}
	}
	
	/**
	 * @param crlURL
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws CertificateException
	 * @throws CRLException
	 */
	public static X509CRL downloadCRLFromWebDP(String crlURL) throws MalformedURLException, IOException, CertificateException, CRLException {
		URL url = new URL(crlURL);
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		DataInputStream inStream = new DataInputStream(connection.getInputStream());
		
		return loadFromInputStream(inStream);
		
	}
	
	
	public static X509CRL loadFromInputStream(InputStream is) throws CertificateException, CRLException, IOException {
		DataInputStream inStream = new DataInputStream(is);

		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509CRL crl = (X509CRL) cf.generateCRL(inStream);
			return crl;
		} finally {
			inStream.close();
		}
	}
	
}
