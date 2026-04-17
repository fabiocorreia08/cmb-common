package br.gov.cmb.commons.certificado.util;

import br.gov.cmb.common.builder.ZipBuilder;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.X509Certificate;

/**
 * Assina / Verifica assinatura de um arquivo
 *
 * Comandos uteis linha de comandos para testar utilitario:
 *
 * Extrair chave publica do certificado
 *
 *   $ openssl x509 -pubkey -noout -in GabrielGoncalvesCardoso.crt  > pubkey.pem
 *
 * Validar assinatura
 *
 *   $ openssl dgst -sha1 -verify pubkey.pem -signature selos.SHA1withRSA.sig selos.txt
 *
 * Author: Paulo Neves (24/05/2017)
 */
public class SignUtil {


    public static final String SIGN_ALGORITHM = "SHA1withRSA";
    public static final String BEGIN_SIGNATURE_SEPARATOR = "-----BEGIN SIGNATURE-----";
    public static final String END_SIGNATURE_SEPARATOR = "-----END SIGNATURE-----";
    public static final String SIGNATURE_EXTENSION = ".sig";

    /**
     * Gera uma assinatura de um conjunto de dados usando algoritmo RSA e chave privada
     * @param data dados a serem assinados
     * @param privkey chave privada
     * @return assinatura RSA
     * @throws Exception
     */
    public static byte[] generateSignature(byte[] data, PrivateKey privkey) throws Exception {
        Signature rsa = Signature.getInstance(SIGN_ALGORITHM);
        rsa.initSign(privkey);
        rsa.update(data, 0, data.length);
        byte[] signature = rsa.sign();

        return signature;
    }

    /**
     * Gera uma assinatura de um conjunto de dados usando algoritmo RSA e chave privada
     * @param data dados a serem assinados
     * @param privkey chave privada
     * @return assinatura RSA codificada em hexadecimal
     * @throws Exception
     */
    public static String generateHexFormatSignature(String data, PrivateKey privkey) throws Exception {
        byte[] signature = generateSignature(data.getBytes("UTF-8"), privkey);

        return Hex.encodeHexString(signature);
    }

    /**
     * Gera uma assinatura de um conjunto de dados usando algoritmo RSA e chave privada
     * @param data dados a serem assinados
     * @param privkey chave privada
     * @return assinatura RSA codificada em hexadecimal
     * @throws Exception
     */
    public static byte[] generateSignature(String data, PrivateKey privkey) throws Exception {
        byte[] signature = generateSignature(data.getBytes("UTF-8"), privkey);

        return signature;
    }

    /**
     * Assina um conjunto de dados concatenando no final a assinatura dos dados
     * @param data dados a serem assinados
     * @param privkey Chave privada
     * @return Dados assinados
     * @throws Exception
     */
    public static String signData(String data, PrivateKey privkey) throws Exception {
        String signature = generateHexFormatSignature(data,privkey);
        StringBuilder signedData = new StringBuilder();
        signedData.append(data);
        signedData.append(BEGIN_SIGNATURE_SEPARATOR);
        signedData.append(signature);
        signedData.append(END_SIGNATURE_SEPARATOR);

        return signedData.toString();
    }

    /**
     * Assina um conjunto de dados concatenando no final a assinatura dos dados
     * @param filename nome do arquivo
     * @param data dados a serem assinados
     * @param privkey Chave privada
     * @return arquivo Zip com arquivo de dados e arquivo assinatura
     * @throws Exception
     */
    public static byte[] signFile(String filename, String data, PrivateKey privkey) throws Exception {
        byte[] signature = generateSignature(data,privkey);

        byte[] fileSigned = ZipBuilder.getInstance()
                .addEntry(filename+".txt",data.getBytes("UTF-8"))
                .addEntry(filename+"."+SIGN_ALGORITHM+ SIGNATURE_EXTENSION, signature)
                .build();

        return fileSigned;
    }

    /**
     * Verifica assinatura de um conjunto de dados
     * @param data dados
     * @param signature assinatura dos dados
     * @param pubKey chave publica
     * @return true caso a assinatura seja válida, false caso contrário
     * @throws Exception
     */
    public static boolean verifySignature(byte[] data, byte[] signature, PublicKey pubKey) throws Exception {
        Signature sig = Signature.getInstance(SIGN_ALGORITHM);
        sig.initVerify(pubKey);
        sig.update(data, 0, data.length);

        return  sig.verify(signature);
    }

    /**
     * Verifica assinatura de um conjunto de dados
     * @param dataWithSignature dados
     * @param pubKey chave publica
     * @return true caso a assinatura seja válida, false caso contrário
     * @throws Exception
     */
    public static boolean verifySignature(String dataWithSignature, PublicKey pubKey) throws Exception {
        String data = extractData(dataWithSignature);
        byte[] signature = Hex.decodeHex(extractSignature(dataWithSignature).toCharArray());

        return verifySignature(data.getBytes("UTF-8"), signature, pubKey);
    }

    /**
     * Verifica assinatura de um conjunto de dados
     * @param data dados
     * @param signature assinatura RSA
     * @param pubKey chave publica
     * @return true caso a assinatura seja válida, false caso contrário
     * @throws Exception
     */
    public static boolean verifySignature(String data, byte[] signature, PublicKey pubKey) throws Exception {
        return verifySignature(data.getBytes("UTF-8"), signature, pubKey);
    }

    private static String extractData(String dataWithSignature) {
        return StringUtils.substringBefore(dataWithSignature, BEGIN_SIGNATURE_SEPARATOR);
    }

    private static String extractSignature(String dataWithSignature) {
        return StringUtils.substringBetween(dataWithSignature, BEGIN_SIGNATURE_SEPARATOR, END_SIGNATURE_SEPARATOR);
    }

    /**
     * Verifica se é um arquivo de assinatura
     * @param filename
     * @return
     */
    public static boolean isSignatureFile(String filename) {
        return SIGNATURE_EXTENSION.substring(1).equalsIgnoreCase(FilenameUtils.getExtension(filename));
    }

    public static void main(String[] args) throws Exception {
        System.out.println("IsSigFile: (selos.rsa.sig) " + SignUtil.isSignatureFile("selos.rsa.sig"));
        System.out.println("IsSigFile: (selos.rsa.SIG) " + SignUtil.isSignatureFile("selos.rsa.SIG"));
        System.out.println("IsSigFile: (selos.txt) " + SignUtil.isSignatureFile("selos.txt"));
        System.out.println("IsSigFile: (selos.sig.txt)" + SignUtil.isSignatureFile("selos.sig.txt"));


        String cipherText = "zUWiXLeCLTtTFWHurqJ6hgDdEU9bNVD5D3xslWatbY3nDp2J497KpGb4ZaPJbNiXR83Qxsm130Y0Uvyop8+vtgDsksQbjyzUF9J0QuWu1ms9Wer2mfnqssNMNDILac/8v6H+V3Ei+8+NPLT2dJMq2vYjFHAao+b/atRLfayJHzWbvy6ulEjZYpc1uHiz5JNXTwzSdYx4xOCSJ+15q8dBAGgqL2f28bWgOkYYYrrUmsDXrDlU/BEsepM47tU1ZLPTlfaBCdbivmfhuiDiATj2paeVw9bqPByN33xlLjhkeO90QTUAc2HtStKXrz4/Ua8qmLynkw16L2Ya0pQ8Fs0Rnw==:Tnp9SURvMZg8Mtp0po8K5w==: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-----BEGIN SIGNATURE-----e16f0084cb987fcfe58846878fb45386a5eb1c23c0f6322c142440c7069bbc7dfbbc520cd6ee2f383ca9bb75dfd2ac436abaafccbbc1d3df578ac2717448ba7ad49ba89eefd91b50de77ec56ec2cc09ed8c1cf9541faf525f3ef671b192a9fda2ffae1bf2f7640849158b6953962dec566e9223a87a371e310efd6aa530642069858853131dd16aaf59fa01741a48f830fe187c22da056e25758b4f30573e118c08e054265c26918e919cc20125e9990291a3329f33a1c83b8691d99f38f812aa9490cce1df75be94350a51a592bf80e46837f87d069005a2ff4abdf81b7f8cf6f748373918018a4ea3711deb4939f9336c3a8516e349e66b65ece73115d58f7-----END SIGNATURE-----";
        String data = SignUtil.extractData(cipherText);
        String signature = SignUtil.extractSignature(cipherText);

        System.out.println("Data: " + data);
        System.out.println("Signature: " + signature);

        X509Certificate cert = CertificateUtil.readCertificateFromFile("/home/pauloneves/certificados/GabrielGoncalvesCardoso.crt");
        PublicKey pubKey = CertificateUtil.readPublicKeyFromCertificate(cert);
        boolean valid = SignUtil.verifySignature(cipherText, pubKey);

        System.out.println("Is signature valid: " + valid);

    }
}
