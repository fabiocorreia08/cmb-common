package br.gov.cmb.commons.certificado.validator;


import br.gov.cmb.commons.certificado.exception.CAManagerException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;


public class CAValidatorHelper {
	
    public static boolean isRootCA(X509Certificate ca) {
        if (ca == null) {
            return false;
        }
        return isCAofCertificate(ca, ca);
    }

    public static boolean isCAofCertificate(X509Certificate ca, X509Certificate certificate) {
        try {
            certificate.verify(ca.getPublicKey());
            return true;
        } catch (SignatureException | InvalidKeyException ex) {
            return false;
        } catch (CertificateException error) {
            throw new CAManagerException("Algum erro ocorreu com o certificado informado", error);
        } catch (NoSuchAlgorithmException error) {
            throw new CAManagerException("Não há o algoritmo necessário", error);
        } catch (NoSuchProviderException error) {
            throw new CAManagerException("Provider inválido", error);
        }
    }


    public static X509Certificate getCAFromCertificate(Collection<X509Certificate> certificates, X509Certificate certificate) {
        if (isRootCA(certificate)) {
            return null;
        }
        if (certificates == null || certificates.isEmpty()) {
            return null;
        }
        for (X509Certificate ca : certificates) {
            if (isCAofCertificate(ca, certificate)) {
                return ca;
            }
        }
        return null;
    }

}