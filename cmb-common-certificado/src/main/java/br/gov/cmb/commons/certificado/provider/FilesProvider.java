package br.gov.cmb.commons.certificado.provider;

import org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class FilesProvider implements CAProvider {
	private final Logger log = LoggerFactory.getLogger(FilesProvider.class);
	
	List<X509Certificate> certificates = new ArrayList<X509Certificate>();
	
	public FilesProvider(String... filenames) {
		for (String filename : filenames) {
			log.info("Reading file: " + filename);
			List<X509Certificate> extracted = getCAsFromFile(filename);
			log.info("Extracted " + extracted.size() + " certificates!");
			certificates.addAll(getCAsFromFile(filename));
		}
	}
		
	public List<X509Certificate> getCertificates() {
		return certificates;
	}
	
	@SuppressWarnings("unchecked")
	private List<X509Certificate> getCAsFromFile(String filename) {
		CertificateFactory cf = new CertificateFactory();
		List<X509Certificate> result = new ArrayList<X509Certificate>();
		File file = new File(filename);
		try {
			InputStream cas = new FileInputStream(file);
	        try {        		        	
	            result.addAll(cf.engineGenerateCertificates(cas));
	        } catch (CertificateException e) {
	        } finally {
	        	try {
	        		if (cas != null) {
	        			cas.close();
	        		}
				} catch (IOException e) {
				}
	        }
		} catch (FileNotFoundException fnfe) {
			log.error("Nao foi encontrado o arquivo: " + filename);
		}
        return result;
	}
}
