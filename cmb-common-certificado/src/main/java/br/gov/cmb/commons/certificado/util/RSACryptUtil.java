package br.gov.cmb.commons.certificado.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Utilitário de encriptação de arquivos com algoritmos RSA
 *
 * author: Paulo Neves (23/05/2017)
 */
public class RSACryptUtil {

    public static final String RSA_ALGORITHM = "RSA";
    public static final String UTF_8_CHARSETNAME = "UTF-8";

    /**
     * Encripta um texto usando chave pública
     * @param rawText texto a ser encriptado
     * @param publicKey chave publica
     * @return texto encriptado no enconde Base64
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static String encrypt(String rawText, PublicKey publicKey) throws IOException, GeneralSecurityException {
        return encrypt(rawText.getBytes(UTF_8_CHARSETNAME), publicKey);
    }

    /**
     * Encripta um texto usando chave pública
     * @param rawText texto a ser encriptado
     * @param publicKey chave publica
     * @return texto encriptado no enconde Base64
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static String encrypt(byte[] rawText, PublicKey publicKey) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64String(cipher.doFinal(rawText));
    }

    /**
     * Desencripta um texto usando chave privada
     * @param cipherText texto encriptado
     * @param privateKey chave privada
     * @return texto desencriptado
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static String decrypt(String cipherText, PrivateKey privateKey) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.decodeBase64(cipherText)), UTF_8_CHARSETNAME);
    }

    /**
     * Desencripta um texto usando chave privada
     * @param cipherText texto encriptado
     * @param privateKey chave privada
     * @return texto desencriptado
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static byte[] decrypt(byte[] cipherText, PrivateKey privateKey) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(cipherText);
    }
}
