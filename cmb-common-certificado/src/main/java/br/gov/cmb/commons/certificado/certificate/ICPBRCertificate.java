package br.gov.cmb.commons.certificado.certificate;

import br.gov.cmb.commons.certificado.util.ConvertUtil;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ICPBRCertificate {
    public static final String OID_A1_CERTIFICATE = "2.16.76.1.2.1";
    public static final String OID_A2_CERTIFICATE = "2.16.76.1.2.2";
    public static final String OID_A3_CERTIFICATE = "2.16.76.1.2.3";
    public static final String OID_A4_CERTIFICATE = "2.16.76.1.2.4";
    public static final String OID_S1_CERTIFICATE = "2.16.76.1.2.101";
    public static final String OID_S2_CERTIFICATE = "2.16.76.1.2.102";
    public static final String OID_S3_CERTIFICATE = "2.16.76.1.2.103";
    public static final String OID_S4_CERTIFICATE = "2.16.76.1.2.104";
    public static final String OID_T3_CERTIFICATE = "2.16.76.1.2.303";
    public static final String OID_T4_CERTIFICATE = "2.16.76.1.2.304";
	
	
	private X509Certificate certificate = null;
	private br.gov.cmb.commons.certificado.certificate.KeyUsage keyUsage = null;
	private ICPBRX509Principal issuer = null;
	private ICPBRX509Principal subject = null;
	private ICPBRCertificateExtras extras = null;
	
	
	public ICPBRCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }
	
	
	public X509Certificate getCertificate() {
		return certificate;
	}
	
	public ICPBRX509Principal getCertificateIssuer() throws IOException {
        if (issuer == null) {
        	issuer = new ICPBRX509Principal(certificate.getIssuerX500Principal());
        }
        return issuer;
    }
	
	public ICPBRX509Principal getCertificateSubject() throws IOException {
        if (subject == null) {
        	subject = new ICPBRX509Principal(certificate.getSubjectX500Principal());
        }
        return subject;
    }
	
	public BigInteger getSerialNumber() {
        return certificate.getSerialNumber();
    }
	
	public String getHEXSerialNumber() {
		return ConvertUtil.toHexa(certificate.getSerialNumber());
	}
	
	 public Date getBeforeDate() {
	        return certificate.getNotBefore();
	 }
	 
	 public Date getAfterDate() {
	        return certificate.getNotAfter();
	 }
	 
	public boolean isPeriodValid() {
		boolean isValid = true;
		
		try {
			certificate.checkValidity();
		} catch (CertificateExpiredException | CertificateNotYetValidException e) {
			isValid = false;
		}

		return isValid;
	}
	
	public boolean isPeriodValidAt(Date date) {
		boolean isValid = true;
		
		try {
			certificate.checkValidity(date);
		} catch (CertificateExpiredException | CertificateNotYetValidException e) {
			isValid = false;
		}

		return isValid;
	}
	
	public String getType() {
		return certificate.getType();
	}
	
	public String getSignAlgorithm() {
		return certificate.getSigAlgName();
	}
	
	
	public br.gov.cmb.commons.certificado.certificate.KeyUsage getKeyUsage() {
		if (keyUsage == null) {
			keyUsage = new br.gov.cmb.commons.certificado.certificate.KeyUsage(certificate.getKeyUsage());
		}
		
		return keyUsage;
	}
	
	public ICPBRCertificateExtras getExtras() {
		if (extras == null) {
			extras = new ICPBRCertificateExtras(this.certificate);
		}
		
		return extras;
	}
	
	
	public ResponsibleCertificateExtras getResponsibleExtras() {
		
		if (this.getExtras().isCertificatePF()) {
			return getExtras().getICPBRCertificateExtrasPF();
		} else if (this.getExtras().isCertificatePJ()) {
			return getExtras().getICPBRCertificateExtrasPJ();
		} else if (this.getExtras().isCertificateEquipment()) {
			return getExtras().getICPBRCertificateExtrasEquip();
		}
		
		return null;
	}

    /**
     * Pol�tica de Certificado de Assinatura Digital
     * 
     * Nivel do Certificado (A1, A2, A3, A4, S1, S2, S3, S4, T3, T4).<br>
     *
     * @return String Nivel do certificado ou null se n�o tiver politica definida
     */
    public String getNivelCertificado() {
        try {
            DLSequence sequence = (DLSequence) getExtensionValue(Extension.certificatePolicies.getId());
            if (sequence != null) {
                for (int pos = 0; pos < sequence.size(); pos++) {
                    DLSequence sequence2 = (DLSequence) sequence.getObjectAt(pos);
                    ASN1ObjectIdentifier policyIdentifier = (ASN1ObjectIdentifier) sequence2.getObjectAt(0);
                    PolicyInformation policyInformation = new PolicyInformation(policyIdentifier);
                    String id = policyInformation.getPolicyIdentifier().getId();
                    if (id == null) {
                        continue;
                    }

                    if (id.startsWith(OID_A1_CERTIFICATE)) {
                        return "A1";
                    }
                    if (id.startsWith(OID_A2_CERTIFICATE)) {
                        return "A2";
                    }
                    if (id.startsWith(OID_A3_CERTIFICATE)) {
                        return "A3";
                    }
                    if (id.startsWith(OID_A4_CERTIFICATE)) {
                        return "A4";
                    }
                    if (id.startsWith(OID_S1_CERTIFICATE)) {
                        return "S1";
                    }
                    if (id.startsWith(OID_S2_CERTIFICATE)) {
                        return "S2";
                    }
                    if (id.startsWith(OID_S3_CERTIFICATE)) {
                        return "S3";
                    }
                    if (id.startsWith(OID_S4_CERTIFICATE)) {
                        return "S4";
                    }
                    if (id.startsWith(OID_T3_CERTIFICATE)) {
                        return "T3";
                    }
                    if (id.startsWith(OID_T4_CERTIFICATE)) {
                        return "T4";
                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
	 * Checks whether given X.509 certificate is self-signed.
	 */
	public boolean isSelfSigned() throws CertificateException, NoSuchAlgorithmException, NoSuchProviderException {
		try {
			// Try to verify certificate signature with its own public key
			PublicKey key = certificate.getPublicKey();
			certificate.verify(key);	
			return true;
		} catch (SignatureException sigEx) {
			// Invalid signature --> not self-signed
			return false;
		} catch (InvalidKeyException keyEx) {
			// Invalid key --> not self-signed
			return false;
		}
	}
	
	/**
     * Verifica se � um Certificado AC (Authority Certificate).<br>
     * * <b>true</b> - Se for CA.<br>
     * * <b>false</b> - Se for Certificado de usu�rio final.<br>
     *
     * @return boolean
     */
    public boolean isCertificadoAc() {
        return certificate.getBasicConstraints() >= 0;
    }
    
    /**
     * Obt�m o conte�do de um determinado OID
     *
     * @param oid A identifica��o do campo
     *
     * @return O conte�do relacionado ao OID informado
     */
    @SuppressWarnings("resource")
	public ASN1Primitive getExtensionValue(String oid) {
        byte[] extensionValue = certificate.getExtensionValue(oid);
        if (extensionValue == null) {
            return null;
        }
        try {
            DEROctetString oct = (DEROctetString) (new ASN1InputStream(extensionValue).readObject());
            return (new ASN1InputStream(oct.getOctets()).readObject());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
	/**
	 * Extrai lista de urls com as listas dos certificado revogados.
	 */
	public List<String> getCrlDistributionPoints() throws IOException {
		byte[] crldpExt = certificate.getExtensionValue(Extension.cRLDistributionPoints.getId());
		if (crldpExt == null) {
			List<String> emptyList = new ArrayList<String>();
			return emptyList;
		}
		CRLDistPoint distPoints = CRLDistPoint.getInstance(X509ExtensionUtil.fromExtensionValue(crldpExt));
		
		List<String> crlUrls = new ArrayList<String>(5);
		for (DistributionPoint dp : distPoints.getDistributionPoints()) {
			DistributionPointName dpn = dp.getDistributionPoint();
			// Look for URIs in fullName
			if (dpn != null) {
				if (dpn.getType() == DistributionPointName.FULL_NAME) {
					GeneralName[] genNames = GeneralNames.getInstance(dpn.getName()).getNames();
					// Look for an URI
					for (int j = 0; j < genNames.length; j++) {
						if (genNames[j].getTagNo() == GeneralName.uniformResourceIdentifier) {
							String url = DERIA5String.getInstance(genNames[j].getName()).getString();
							crlUrls.add(url);
						}
					}
				}
			}
		}

        
		return crlUrls;
	}

    
	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder(0);
        try {
            SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            sb.append("---------------------------------\n");
            sb.append("    INFORMACOES DO CERTIFICADO   \n");
            sb.append("---------------------------------\n");
            sb.append("Certificado AC..: ").append(this.getCertificateIssuer().getPrincipalName()).append("\n");
            sb.append("Serial Number...: ").append(this.getHEXSerialNumber()).append("\n");
            sb.append("Certificado para: ").append(this.getCertificateSubject().getPrincipalName()).append("\n");            
            sb.append("Validade........: de ").append(dtFormat.format(this.getBeforeDate())).append(" ate ").append(dtFormat.format(this.getAfterDate())).append("\n");
            sb.append("Valido ? .......: ").append(this.isPeriodValid()).append("\n");
            sb.append("Certificado AC ?: ").append(this.isCertificadoAc()).append("\n");
            sb.append("isSelfSigned....: ").append(this.isSelfSigned()).append("\n");
            sb.append("---------------------------------\n");
            sb.append("Tipo Certificado: ").append(this.getNivelCertificado()).append("\n");
            sb.append("Algoritmo.......: ").append(this.getSignAlgorithm()).append("\n");
            sb.append("Tipo de Uso . . : ").append(this.getKeyUsage()).append("\n");
            sb.append("---------------------------------\n");
            sb.append("Extras ICP-BRASIL - DOC-ICP-04:\n");
            sb.append("Email...........: ").append(this.getExtras().getEmail()).append("\n");
            sb.append("Certificado PF..: ").append(this.getExtras().isCertificatePF()).append("\n");
            if (this.getExtras().isCertificatePF()) {
                ICPBRCertificateExtrasPF extraPF = this.getExtras().getICPBRCertificateExtrasPF();
                sb.append("CPF...............: ").append(extraPF.getCPF()).append("\n");
                sb.append("Data Nascimento...: ").append(extraPF.getDataNascimento()).append("\n");
                sb.append("PIS...............: ").append(extraPF.getNIS()).append("\n");
                sb.append("Rg................: ").append(extraPF.getRG()).append("\n");
                sb.append("Orgao Expedidor RG: ").append(extraPF.getOrgaoExpedidorRg()).append("\n");
                sb.append("UF RG.............: ").append(extraPF.getUfOrgaoExpedidorRg()).append("\n");
                sb.append("INSS CEI..........: ").append(extraPF.getINSSCei()).append("\n");
                sb.append("Titulo............: ").append(extraPF.getTituloEleitor()).append("\n");
                sb.append("Secao.............: ").append(extraPF.getSecaoTituloEleitoral()).append("\n");
                sb.append("Zona..............: ").append(extraPF.getZonaEleitoral()).append("\n");
                sb.append("Municipio Titulo..: ").append(extraPF.getMunicipioTituloEleitoral()).append("\n");
                sb.append("UF Titulo.........: ").append(extraPF.getUfMunicipioTituloEleitoral()).append("\n");
            }

            sb.append("---------------------------------\n");
            sb.append("Certificado PJ...: ").append(this.getExtras().isCertificatePJ()).append("\n");
            if (this.getExtras().isCertificatePJ()) {
            	ICPBRCertificateExtrasPJ extraPJ = this.getExtras().getICPBRCertificateExtrasPJ();
                sb.append("CNPJ..............: ").append(extraPJ.getCNPJ()).append("\n");
                sb.append("CPF...............: ").append(extraPJ.getCPF()).append("\n");
                sb.append("Data Nascimento...: ").append(extraPJ.getDataNascimento()).append("\n");
                sb.append("PIS...............: ").append(extraPJ.getNIS()).append("\n");
                sb.append("Rg................: ").append(extraPJ.getRG()).append("\n");
                sb.append("Orgao Expedidor RG: ").append(extraPJ.getOrgaoExpedidorRg()).append("\n");
                sb.append("UF RG.............: ").append(extraPJ.getUfOrgaoExpedidorRg()).append("\n");
                sb.append("INSS CEI..........: ").append(extraPJ.getINSSCei()).append("\n");
                sb.append("Responsavel.......: ").append(extraPJ.getNomeResponsavel()).append("\n");
            }
            
            sb.append("---------------------------------\n");
            sb.append("Certificado Equip...: ").append(this.getExtras().isCertificateEquipment()).append("\n");
            if (this.getExtras().isCertificateEquipment()) {
            	ICPBRCertificateExtrasEquip extraEquip = this.getExtras().getICPBRCertificateExtrasEquip();
                sb.append("CNPJ..............: ").append(extraEquip.getCNPJ()).append("\n");
                sb.append("CPF...............: ").append(extraEquip.getCPF()).append("\n");
                sb.append("Data Nascimento...: ").append(extraEquip.getDataNascimento()).append("\n");
                sb.append("PIS...............: ").append(extraEquip.getNIS()).append("\n");
                sb.append("Rg................: ").append(extraEquip.getRG()).append("\n");
                sb.append("Orgao Expedidor RG: ").append(extraEquip.getOrgaoExpedidorRg()).append("\n");
                sb.append("UF RG.............: ").append(extraEquip.getUfOrgaoExpedidorRg()).append("\n");
                sb.append("Responsavel.......: ").append(extraEquip.getNomeResponsavel()).append("\n");
                sb.append("Nome Empresa......: ").append(extraEquip.getNomeEmpresarial()).append("\n");
            }
            sb.append("---------------------------------\n");
            sb.append("CRL DistPoint . : ").append(this.getCrlDistributionPoints()).append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
