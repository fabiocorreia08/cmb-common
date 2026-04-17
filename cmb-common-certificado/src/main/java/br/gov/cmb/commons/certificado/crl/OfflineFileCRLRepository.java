package br.gov.cmb.commons.certificado.crl;

import br.gov.cmb.commons.certificado.certificate.ICPBRCertificate;
import br.gov.cmb.commons.certificado.exception.CRLRepositoryException;
import br.gov.cmb.commons.certificado.util.DateUtils;
import br.gov.cmb.commons.certificado.util.PKIXUtils;
import br.gov.cmb.commons.certificado.util.RepositoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509CRL;
import java.util.Date;
import java.util.List;

public class OfflineFileCRLRepository implements CRLRepository {
	private final Logger log = LoggerFactory.getLogger(OfflineFileCRLRepository.class);
	private String pathRepository = null;
	private int hours = 24;
	

	public OfflineFileCRLRepository(String pathRepository) {
		this.pathRepository = pathRepository;
	}
	
	public OfflineFileCRLRepository(String pathRepository, int hours) {
		this.pathRepository = pathRepository;
		this.hours = hours;
	}
	
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
					X509CRL crl = this.getFromFile(crlUrl);
					if (crl != null) {
						// valid crl
						log.info("Encontrado um CRL valida [" + crlUrl +"]!");
						x509Crl = crl;
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			throw new CRLRepositoryException("Nao foi possivel obter a lista CRL do certificado", e);
		}
		
		return x509Crl;
	}
	
	
	private X509CRL getFromFile(String crlUrl) throws CertificateException, CRLException, FileNotFoundException, IOException {
		X509CRL crl = null;
		File fileCRL = null;
		
		if (new File(pathRepository).mkdirs()) {
			log.info("Criando repositorio de CRLs.");
        } else {
        	log.info("Repositorio CRL ja esta criado.");
        }
		
		fileCRL = new File(pathRepository, RepositoryUtils.urlToHexaMD5(crlUrl));
		log.info("File cache crl:" + fileCRL.getName());
		
        if (!fileCRL.exists()) {
        	log.info("Fazendo cache do CRL!");
            RepositoryUtils.saveURL(crlUrl, fileCRL);
        } else {
        	log.info("Lendo CRL do cache!");
        }
        
        if (fileCRL.length() != 0) {
            crl = PKIXUtils.loadFromInputStream(new FileInputStream(fileCRL));
            log.info("Prazo do CRL :" + crl.getNextUpdate());
            Date dateFile = new Date(fileCRL.lastModified());
            Date cacheFileExpire = DateUtils.plusHours(dateFile, hours);
            Date expireCacheAt = greaterDate(cacheFileExpire, crl.getNextUpdate());
            log.info("Cache CRL expira na data: " + expireCacheAt);
            if (expireCacheAt.before(new Date())) {
                // Se estiver expirado, atualiza com a CRL mais nova
                log.info("CRL expirado, executando UPDATE do cache.");
                RepositoryUtils.saveURL(crlUrl, fileCRL);
                crl = PKIXUtils.loadFromInputStream(new FileInputStream(fileCRL));
            }
        }

        return crl;		
	}
	
	private Date greaterDate(Date cacheFileExpire, Date crlExpireDate) {
		if (crlExpireDate == null || cacheFileExpire.after(crlExpireDate)) {
			return cacheFileExpire;
		}
		
		return crlExpireDate;
	}
	

}
