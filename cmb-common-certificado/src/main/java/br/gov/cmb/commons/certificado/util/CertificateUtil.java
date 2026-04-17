package br.gov.cmb.commons.certificado.util;


import br.gov.cmb.commons.certificado.certificate.ICPBRCertificate;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.openssl.PEMParser;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;

public class CertificateUtil {

    public static ICPBRCertificate deserializeBase64ICPCertificate(String certificado) throws UnsupportedEncodingException, IOException, ClassNotFoundException {
        byte[] byteData = org.bouncycastle.util.encoders.Base64.decode(certificado.getBytes("UTF-8"));
        ByteArrayInputStream bis = new ByteArrayInputStream(byteData);
        ObjectInput in = new ObjectInputStream(bis);
        X509Certificate cert = (X509Certificate) in.readObject();
        bis.close();
        ICPBRCertificate ICPCert = new ICPBRCertificate(cert);

        return ICPCert;
    }


    public static ICPBRCertificate fromPEMtoX509Certificate(String pem) throws CertificateException, IOException {
        X509Certificate cert = null;
        StringReader reader = new StringReader(pem);
        PEMParser pr = new PEMParser(reader);
        X509CertificateHolder holder = (X509CertificateHolder) pr.readObject();
        JcaX509CertificateConverter certconv = new JcaX509CertificateConverter().setProvider("BC");
        cert = certconv.getCertificate(holder);

        reader.close();

        ICPBRCertificate ICPCert = new ICPBRCertificate(cert);

        return ICPCert;

    }

    public static String fromHTTPHeaderToPEM(String sslClientCert) {
        sslClientCert = sslClientCert.replaceAll("-----BEGIN CERTIFICATE-----", "").replaceAll("-----END CERTIFICATE-----", "");
        String base64Certificate = sslClientCert
                .replaceAll("\\s{1,}", System.lineSeparator())
                .replaceAll("\\s{2,}", System.lineSeparator())
                .replaceAll("\\t+", System.lineSeparator());
        sslClientCert = "-----BEGIN CERTIFICATE-----" + base64Certificate + "-----END CERTIFICATE-----";

        return sslClientCert;
    }

    /**
     * Carrega um certificado no formato DER (.crt) a partir do arquivo certificado
     * @param certFileName nome do arquivo certificado (.crt)
     * @return certificado X.509
     * @throws CertificateException
     * @throws IOException
     */
    public static X509Certificate readCertificateFromFile(String certFileName) throws CertificateException, IOException {
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        InputStream in = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(certFileName)));
        X509Certificate cert = (X509Certificate)certFactory.generateCertificate(in);

        return cert;
    }

    /**
     * Lê chave publica do certificado
     * @param pem Certificado X.509 no formato PEM
     * @return Chave publica do certificado
     * @throws CertificateException
     * @throws IOException
     */
    public static PublicKey readPublicKeyFromCertificate(String pem) throws IOException, CertificateException {
        ICPBRCertificate certificate = fromPEMtoX509Certificate(pem);

        return readPublicKeyFromCertificate(certificate.getCertificate());
    }

    /**
     * Lê chave publica do certificado
     * @param certificate Certificado X.509
     * @return Chave publica do certificado
     * @throws CertificateException
     * @throws IOException
     */
    public static PublicKey readPublicKeyFromCertificate(X509Certificate certificate) throws CertificateException {
        PublicKey key = certificate.getPublicKey();

        return key;
    }

    private static String getKey(String filename) throws IOException {
        // Read key from file
        String strKeyPEM = "";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            strKeyPEM += line + "\n";
        }
        br.close();
        return strKeyPEM;
    }

    /**
     * Obtem chave privada de um arquivo no formato PKCS8
     *
     * Como converter um arquivo PrivateKey no formato pkcs8 :
     * $ openssl pkcs8 -topk8 -inform PEM -outform PEM -in privateKeyFile.key  -nocrypt > pkcs8_key
     *
     * @param filename arquivo chave privada
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static PrivateKey getPrivateKey(String filename) throws IOException, GeneralSecurityException {
        String privateKeyPEM = getKey(filename);
        return getPrivateKeyFromString(privateKeyPEM);
    }

    public static PrivateKey getPrivateKeyFromString(String key) throws IOException, GeneralSecurityException {
        String privateKeyPEM = key;
        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----\n", "");
        privateKeyPEM = privateKeyPEM.replace("-----END PRIVATE KEY-----", "");
        byte[] encoded = Base64.decodeBase64(privateKeyPEM);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        PrivateKey privKey = (PrivateKey) kf.generatePrivate(keySpec);
        return privKey;
    }
}
