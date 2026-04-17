package br.gov.cmb.commons.certificado.certificate;

import java.security.cert.X509Certificate;

/**
 * 
 * @author pjneves
 *
 *
 * KeyUsage ::= BIT STRING {
 *  digitalSignature        (0),
 *  nonRepudiation          (1),
 *  keyEncipherment         (2),
 *  dataEncipherment        (3),
 *  keyAgreement            (4),
 *  keyCertSign             (5),
 *  cRLSign                 (6),
 *  encipherOnly            (7),
 *  decipherOnly            (8) 
 *  }
 */
public class KeyUsage {
	private static final String[] KEY_USAGE_DESC = { "digitalSignature", "nonRepudiation", "keyEncipherment", "dataEncipherment", "keyAgreement", "keyCertSign", "cRLSign", "encipherOnly", "decipherOnly" };

	private final boolean[] keyUsage;
	
	public KeyUsage(boolean[] keyUsage) {
		this.keyUsage = keyUsage;
	}
	
	public KeyUsage(X509Certificate cert) {
		this.keyUsage = cert.getKeyUsage();
	}

	public boolean isDigitalSignature() {
		return keyUsage != null ? keyUsage[0] : false;
	}

	public boolean isNonRepudiation() {
		return keyUsage != null ? keyUsage[1] : false;
	}

	public boolean isKeyEncipherment() {
		return keyUsage != null ? keyUsage[2] : false;
	}

	public boolean isDataEncipherment() {
		return keyUsage != null ? keyUsage[3] : false;
	}

	public boolean isKeyAgreement() {
		return keyUsage != null ? keyUsage[4] : false;
	}

	public boolean isKeyCertSign() {
		return keyUsage != null ? keyUsage[5] : false;
	}

	public boolean isCRLSign() {
		return keyUsage != null ? keyUsage[6] : false;
	}

	public boolean isEncipherOnly() {
		return keyUsage != null ? keyUsage[7] : false;
	}

	public boolean isDecipherOnly() {
		return keyUsage != null ? keyUsage[8] : false;
	}

	@Override
	public String toString() {
		String separator = ", ";
		StringBuilder sb = new StringBuilder("[");
		
		if (keyUsage != null) {
			for (int i = 0; i < keyUsage.length; i++) {
				if (keyUsage[i]) {
					if (sb.length() > 1) {
						sb.append(separator);
					}
					sb.append(KEY_USAGE_DESC[i]);
				}
			}
		}
		
		sb.append("]");
		return sb.toString();
	}
}
