package br.gov.cmb.commons.certificado.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Encripta / Desencripta texto usando algoritmo AES/CBC/PKCS5Padding anexando chave secretas simetrica encriptada em RSA com chave publica
 *
 * Formato texto encriptado:
 *
 * Base64(RSA_Encrypt(SecretKey)):Base64(IV):Base64(ENCRYPTED_TEXT)
 *
 * Author: Paulo Neves (23/05/2017)
 */
public class SymmetricCipher {

    public static final String TEXT_PART_SEPARATOR = ":";

    public static String encrypt(String plaintext, PublicKey pubkey) throws Exception {
        SecretKey key = AesCryptUtil.generateSymmetricKey();
        byte[] iv = AesCryptUtil.generateIV();
        byte[] encrypted = AesCryptUtil.encrypt(plaintext, key,iv);
        byte[] encodedKey = key.getEncoded();
        String symmetricKeyB64 = RSACryptUtil.encrypt(encodedKey, pubkey);

        StringBuilder ciphertext = new StringBuilder();
        ciphertext.append( symmetricKeyB64 );
        ciphertext.append(TEXT_PART_SEPARATOR);
        ciphertext.append( Base64.encodeBase64String( iv ) );
        ciphertext.append(TEXT_PART_SEPARATOR);
        ciphertext.append( Base64.encodeBase64String( encrypted ) );

        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, PrivateKey privkey) throws Exception {
        String [] parts = ciphertext.split( TEXT_PART_SEPARATOR );
        byte [] pk = Base64.decodeBase64( parts[0] );
        byte [] iv = Base64.decodeBase64( parts[1] );
        byte [] encrypted = Base64.decodeBase64( parts[2] );
        byte [] asymmetricSecretKey = RSACryptUtil.decrypt(pk, privkey);

        SecretKeySpec keySpec = AesCryptUtil.createSecretKeySpec(asymmetricSecretKey);
        String decryptedTxt = AesCryptUtil.decrypt(encrypted, keySpec, iv);

        return decryptedTxt;
    }


    public static void main(String[] args) throws Exception {
        String cipherText = FileUtils.readFileToString(new File("/home/pauloneves/certificados/selos.txt"),"UTF-8");
        String privateKey = "/home/pauloneves/certificados/pkcs8_key";
        PrivateKey privkey = CertificateUtil.getPrivateKey(privateKey);

        String txt = SymmetricCipher.decrypt(cipherText, privkey);

        FileUtils.writeStringToFile(new File("/home/pauloneves/certificados/file.txt"),txt,"UTF-8");

        System.out.println("Descrypted : \n" + txt);
    }
}
