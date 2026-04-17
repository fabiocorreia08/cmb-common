package br.gov.cmb.commons.certificado.manager;


import br.gov.cmb.commons.certificado.certificate.ICPBRCertificate;
import br.gov.cmb.commons.certificado.exception.CertificateValidationException;
import br.gov.cmb.commons.certificado.exception.DigitalSignedMessageException;
import br.gov.cmb.commons.certificado.exception.InvalidBase64MessageException;
import br.gov.cmb.commons.certificado.util.PKIXUtils;
import br.gov.cmb.commons.certificado.validator.CertificateValidator;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.ContentInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.SignedData;
import org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.*;
import java.util.*;

public class SignedMessageManager {
	Logger log = LoggerFactory.getLogger(SignedMessageManager.class);
	
	private String signedMessage;
	private String decodedMessage;
	private SignedData signedData;
	private X509Certificate rootCertificate;
	private ICPBRCertificate signatureCertificate;
	private List<X509Certificate> certificates = new ArrayList<>();
	
	public SignedMessageManager(String signedMessage) throws DigitalSignedMessageException {
		this.signedMessage = signedMessage;
		initialize();
	}
	
	private void initialize() throws DigitalSignedMessageException {
		try {
			validateBase64();
			extractSignedData();
			decodeMessage();
			extractCertificates();
		} catch (InvalidBase64MessageException ibme) {
			throw new DigitalSignedMessageException(ibme.getMessage());
		} catch (IOException | CertificateException | NoSuchProviderException | NoSuchAlgorithmException e) {
			throw new DigitalSignedMessageException("Erro ao extrair certificados.", e);
		}
	}
	
	public String getDecodedMessage() {
		return decodedMessage;
	}
	
	public List<X509Certificate> getCertificates() {
		return certificates;
	}
	
	public ICPBRCertificate getSignatureCertificate() {
		return signatureCertificate;
	}
	
	public void validateBase64() throws InvalidBase64MessageException {
		if (!PKIXUtils.isBase64(signedMessage)) {
			throw new InvalidBase64MessageException("Mensagem não está codificada com Base64");
		}
	}

	
	private void extractSignedData() throws DigitalSignedMessageException {
		
		try {
			byte[] data = Base64.decode(signedMessage);
			
			ASN1InputStream din = new ASN1InputStream(new ByteArrayInputStream(data));
			ASN1Primitive pkcs;
			pkcs = din.readObject();
			din.close();
					
			ContentInfo content = ContentInfo.getInstance(pkcs);
			
			if (!content.getContentType().equals(PKCSObjectIdentifiers.signedData))
				throw new SecurityException("Não é um dado assinado com certificado PKCS#7 válido - Header Errado " + content.getContentType().getId());
			
			signedData = SignedData.getInstance(content.getContent());
		} catch (SecurityException se) {
			throw new DigitalSignedMessageException(se.getMessage(), se);
		} catch (Exception e) {
			throw new DigitalSignedMessageException("A mensagem não foi assinada digitalmente com formato PKCS#7", e);
		} 

	}

	/**
	 * Decodifica mensagem assinada digitalmente com PKCS#7
	 * 
	 * @throws DigitalSignedMessageException caso mensagem nao for assinada digitalmente com formato PKCS#7
	 */
	private void decodeMessage() throws DigitalSignedMessageException {
		/*
		 * pkcs-7 OBJECT IDENTIFIER
		 * 1.2.840.113549.1.7.1 - data
		 * 1.2.840.113549.1.7.2 - signedData
		 * 1.2.840.113549.1.7.3 - envelopedData
		 * 1.2.840.113549.1.7.4 - signedAndEnvelopedData
		 * 1.2.840.113549.1.7.5 - digestedData
		 * 1.2.840.113549.1.7.6 - encryptedData 
		 */
		try {
			ContentInfo content = this.signedData.getContentInfo();
			if (!content.getContentType().equals(PKCSObjectIdentifiers.data))
				throw new SecurityException("Não é um objeto PKCS#7 válido - não é uma assinatura PKCS#7");
			
			byte[] dataresp = ((ASN1OctetString) content.getContent()).getOctets();
			this.decodedMessage = new String(dataresp, StandardCharsets.ISO_8859_1);
			
		} catch (SecurityException se) {
			throw new DigitalSignedMessageException(se.getMessage(), se);
		} catch (Exception e) {
			throw new DigitalSignedMessageException("A mensagem não foi assinada digitalmente com formato PKCS#7", e);
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	private void extractCertificates() throws IOException, CertificateException, NoSuchAlgorithmException, NoSuchProviderException {
		CertificateFactory cf = new CertificateFactory();
	
		Enumeration<ASN1Primitive> enumCert = this.signedData.getCertificates().getObjects();
		
		while (enumCert.hasMoreElements()) {
			ASN1Primitive certObj = enumCert.nextElement();
			
		    InputStream in = new ByteArrayInputStream(certObj.getEncoded(ASN1Encoding.DER));
		    
		    X509Certificate certificate = (X509Certificate) cf.engineGenerateCertificate(in);
		    ICPBRCertificate icpCertificate = new ICPBRCertificate(certificate);
		    certificates.add(certificate);
		    
		    if (icpCertificate.isSelfSigned()) {
		    	rootCertificate = certificate;
		    }
		    
		    if (!icpCertificate.isCertificadoAc()) {
		    	signatureCertificate = icpCertificate;
		    }
		    
		}
	}
	
	
	public boolean validate(CertificateValidator... validators) throws CertificateValidationException {
		for (CertificateValidator certificateValidator : validators) {
			certificateValidator.validate(signatureCertificate);
			log.info(certificateValidator.getClass().getName() + ": OK" );
		}
		
		return true;
	}
	
	/**
	 * Lista de certificados 
	 * @param certificates
	 * @return
	 */
	public Set<TrustAnchor> getTrustAnchors(List<X509Certificate> certificates) {
		Set<TrustAnchor> trusts = new HashSet<>();
		for (X509Certificate certificate : certificates) {
			ICPBRCertificate icpCertificate = new ICPBRCertificate(certificate);
			if (icpCertificate.isCertificadoAc()) {
				trusts.add(new TrustAnchor(certificate, null));
			}
		}
		
		return trusts;
	}
	
	public boolean validateCertPath() throws CertificateException, InvalidAlgorithmParameterException, CertPathValidatorException, NoSuchAlgorithmException {
		CertificateFactory cf = new CertificateFactory();
		CertPath certPath = cf.engineGenerateCertPath(this.certificates);
		
		PKIXParameters params;
		if (rootCertificate != null) {
			params = new PKIXParameters(Collections.singleton(new TrustAnchor(rootCertificate, null)));
			params.setRevocationEnabled(false);
			CertPathValidatorResult result = CertPathValidator.getInstance("PKIX").validate(certPath, params);
			
			return result != null;
		} else {
			return true;
		}
	}
}
