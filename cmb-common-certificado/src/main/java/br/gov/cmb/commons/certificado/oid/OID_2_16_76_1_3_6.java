package br.gov.cmb.commons.certificado.oid;


/**
 * Classe OID 2.16.76.1.3.6<br>
 * <br>
 * Atributos de Pessoa Fisica<br>
 * <br>
 * Atributos:<br>
 *    * INSS CEI
 * <br>
 * Conteudo:<br>
 *   nas 12 (doze) posicoes o numero do Cadastro Especifico do INSS (CEI) da pessoa fisica titular do certificado.<br>
 * <br>
 * @author pjneves
 *
 */
public class OID_2_16_76_1_3_6 extends OIDGeneric {
	private static final String CEI = "cei";

	public static final String OID = "2.16.76.1.3.6";

	
	static final OIDFormatField format[] = new OIDFormatField[] { 
									  OIDFormatField.getInstance(CEI, 12)
									};
	
	@Override
    public void initialize() {
        super.initialize(format);
    }
	
	/**
	 * Numero do Cadastro Especifico do INSS (CEI)
	 * @return numero do Cadastro Especifico do INSS (CEI)
	 */
	public String getINSSCei() {
		return oidFieldsMap.get(CEI);
	}
}
