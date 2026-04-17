package br.gov.cmb.commons.certificado.oid;


/**
 * Classe OID 2.16.76.1.3.9<br>
 * <br>
 * Atributos de Pessoa Fisica<br>
 * <br>
 * Atributos:<br>
 *    * RIC
 * <br>
 * Conteudo:<br>
 *   nas primeiras 11 (onze) posicoes, o numero de Registro de Identidade Civil.<br>
 * <br>
 * @author pjneves
 *
 */
public class OID_2_16_76_1_3_9 extends OIDGeneric {
	private static final String RIC = "ric";

	public static final String OID = "2.16.76.1.3.9";

	
	static final OIDFormatField format[] = new OIDFormatField[] { 
									  OIDFormatField.getInstance(RIC, 11)
									};
	
	@Override
    public void initialize() {
        super.initialize(format);
    }
	
	/**
	 * N�mero de Registro de Identidade Civil
	 * @return numero de Registro de Identidade Civil
	 */
	public String getRIC() {
		return oidFieldsMap.get(RIC);
	}
}
