package br.gov.cmb.commons.certificado.crl;

import br.gov.cmb.commons.certificado.certificate.ICPBRCertificate;
import br.gov.cmb.commons.certificado.exception.CRLRepositoryException;
import br.gov.cmb.commons.certificado.util.PKIXUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509CRL;
import java.util.List;

public class OnlineCRLRepository implements CRLRepository {
	
	private final Logger logger = LoggerFactory.getLogger(OnlineCRLRepository.class);

	@Override
	public X509CRL getX509CRL(ICPBRCertificate certificate) {
		X509CRL x509Crl = null;
		
		try {
			List<String> distURLs = certificate.getCrlDistributionPoints();
			
			if (distURLs == null || distURLs.isEmpty()) {
	            throw new CRLRepositoryException("Não consegue obter CRL válido!");
	        }
			
			for (String crlUrl : distURLs) {
				
				try {
					X509CRL crl = PKIXUtils.downloadCRLFromWebDP(crlUrl);
					if (crl != null) {
						// valid crl
						logger.info("Encontrado um CRL valida [" + crlUrl +"]!");
						x509Crl = crl;
						break;
					}
				} catch (CertificateException | CRLException | IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			throw new CRLRepositoryException("Nao foi possivel obter a lista CRL do certificado", e);
		}
		
		
		
		return x509Crl;
	}
	

}
