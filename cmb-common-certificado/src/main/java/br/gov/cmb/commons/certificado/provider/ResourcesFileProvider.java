package br.gov.cmb.commons.certificado.provider;

import org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFileProvider implements CAProvider {
	List<X509Certificate> certificates = new ArrayList<X509Certificate>();
	
	public ResourcesFileProvider(String... resources) {
		for (String resourceName : resources) {
			certificates.addAll(getCAsFromResourceFile(resourceName));
		}
	}
		
	public List<X509Certificate> getCertificates() {
		return certificates;
	}
	
	@SuppressWarnings("unchecked")
	private List<X509Certificate> getCAsFromResourceFile(String filename) {
		CertificateFactory cf = new CertificateFactory();
		List<X509Certificate> result = new ArrayList<X509Certificate>();
        InputStream cas = ResourcesFileProvider.class.getClassLoader().getResourceAsStream(filename);
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
        
        return result;
	}
}
