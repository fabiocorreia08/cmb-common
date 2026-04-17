package br.gov.cmb.commons.certificado.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * Encripta / Desencripta texto usando algoritmo AES/CBC/PKCS5Padding
 *
 * Author: Paulo Neves (23/05/2017)
 */
public class AesCryptUtil {

    public static final String UTF_8_CHARSET_NAME = "UTF-8";
    public static final String AES_ALGORITHM = "AES";
    public static final String AES_CBC_PKCS5_PADDING_FORMAT = "AES/CBC/PKCS5Padding";


    /**
     * Gera SecretKey para usar na encriptação / desencriptação
     * @return Chave Secreta
     * @throws Exception
     */
    public static SecretKey generateSymmetricKey() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance(AES_ALGORITHM);
        generator.init(128);
        SecretKey key = generator.generateKey();
        return key;
    }

    /**
     * Cria SecretKeySpec
     * @param symmetricKey
     * @return
     */
    public static SecretKeySpec createSecretKeySpec(byte[] symmetricKey) {
        SecretKeySpec keySpec = new SecretKeySpec(symmetricKey, AES_ALGORITHM);

        return keySpec;
    }

    /**
     * Ecnripta texto usando SecretKey key e SecureRandom iv
     * @param plaintext texto a ser encriptado
     * @param key Chave de encriptação
     * @param iv SecureRandom bytes
     * @return bytes do texto encriptado
     * @throws Exception
     */
    public static byte[] encrypt(String plaintext, Key key, byte[] iv ) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING_FORMAT);
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        cipher.init( Cipher.ENCRYPT_MODE, key, ivspec);
        return cipher.doFinal(plaintext.getBytes(UTF_8_CHARSET_NAME));
    }

    /**
     * Decripta texto encriptado
     * @param ciphertext texto encriptado
     * @param keySpec Chave de desencriptacao
     * @param iv SecureRandom bytes
     * @return bytes do texto desencriptado
     * @throws Exception
     */
    public static byte[] decrypt( String ciphertext, SecretKeySpec keySpec, byte[] iv ) throws Exception {
        Cipher cipher = Cipher.getInstance( AES_CBC_PKCS5_PADDING_FORMAT );
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        cipher.init( Cipher.DECRYPT_MODE, keySpec, ivspec);
        return cipher.doFinal(Base64.decodeBase64(ciphertext));
    }

    /**
     * Decripta texto encriptado
     * @param ciphertext texto encriptado
     * @param keySpec Chave de desencriptacao
     * @param iv SecureRandom bytes
     * @return bytes do texto desencriptado
     * @throws Exception
     */
    public static String decrypt( byte[] ciphertext, SecretKeySpec keySpec, byte[] iv ) throws Exception {
        Cipher cipher = Cipher.getInstance( AES_CBC_PKCS5_PADDING_FORMAT );
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        cipher.init( Cipher.DECRYPT_MODE, keySpec, ivspec);
        return new String(cipher.doFinal(ciphertext), UTF_8_CHARSET_NAME);
    }

    /**
     * Gera array SecretRandom
     * @return array
     */
    public static byte [] generateIV() {
        SecureRandom random = new SecureRandom();
        byte [] iv = new byte [16];
        random.nextBytes( iv );
        return iv;
    }
}
