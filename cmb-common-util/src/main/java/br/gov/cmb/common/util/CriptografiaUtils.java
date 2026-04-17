package br.gov.cmb.common.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.hash.Hashing;

import br.gov.cmb.common.exception.runtime.CMBRuntimeException;

public final class CriptografiaUtils {
	
	private CriptografiaUtils() {}
	
	private static final String AES = "AES";
	private static final String UTF_8 = "UTF-8";
	private static final String SUN_JCE = "SunJCE";
	private static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
	private static final String IV = "AAAAAAAAAAAAAAAA";

	public static String getMD5(String valor){
		return Hashing.md5().hashBytes(valor.getBytes()).toString();
	}

	public static String criptografar(String textoPuro, String chaveencriptacao) {
		try{
			SecretKeySpec key = gerarChaveCriptografada(chaveencriptacao);
			Cipher encripta = gerarCrifra();
			encripta.init(Cipher.ENCRYPT_MODE, key,	new IvParameterSpec(IV.getBytes(UTF_8)));
			
			byte[] texto = encripta.doFinal(textoPuro.getBytes(UTF_8));
			return Base64.getEncoder().encodeToString(texto);
		}
		catch(Exception e){
			throw new CMBRuntimeException(e);
		}
	}

	public static String descriptografar(String textoEncriptado, String chaveencriptacao) {
		try{
			SecretKeySpec key = gerarChaveCriptografada(chaveencriptacao);
			Cipher decripta = gerarCrifra();
			decripta.init(Cipher.DECRYPT_MODE, key,	new IvParameterSpec(IV.getBytes(UTF_8)));
		
			byte[] textoBase64 = Base64.getDecoder().decode(textoEncriptado.getBytes(UTF_8));
			return new String(decripta.doFinal(textoBase64), UTF_8);
		}
		catch(Exception e){
			throw new CMBRuntimeException(e);
		}
	}
	
	public static String gerarHashMAC(String textoPuro, String chave){
		try{
			SecretKeySpec keySpec = new SecretKeySpec(getMD5(chave).getBytes(UTF_8), "HmacMD5");
	
			Mac mac = Mac.getInstance("HmacMD5");
			mac.init(keySpec);
			byte[] result = mac.doFinal(textoPuro.getBytes());
	
			
			return Base64.getEncoder().encodeToString(result);
		}
		catch(Exception e){
			throw new CMBRuntimeException(e);
		}
	}
	
	private static Cipher gerarCrifra() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
		Cipher decripta = Cipher.getInstance(AES_CBC_PKCS5_PADDING, SUN_JCE);
		return decripta;
	}

	private static SecretKeySpec gerarChaveCriptografada(String chaveencriptacao) throws UnsupportedEncodingException {
		SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes(UTF_8), AES);
		return key;
	}
}
