package br.gov.cmb.commons.certificado.oid;


/**
 * Classe OID 2.16.76.1.3.3<br>
 * <br>
 * Atributos de Pessoa Juridica<br>
 * <br>
 * Atributos:<br>
 *    * CNPJ
 * <br>
 * Conteudo:<br>
 *   nas 14 (quatorze) posicoes o numero do Cadastro Nacional de Pessoa Juridica (CNPJ) da pessoa juridica titular do certificado;<br>
 * <br>
 * @author pjneves
 *
 */
public class OID_2_16_76_1_3_3 extends OIDGeneric {
	private static final String CNPJ = "cnpj";

	public static final String OID = "2.16.76.1.3.3";

	
	static final OIDFormatField format[] = new OIDFormatField[] { 
									  OIDFormatField.getInstance(CNPJ, 14)
									};
	
	@Override
    public void initialize() {
        super.initialize(format);
    }
	
	/**
	 * N�mero do Cadastro Nacional de Pessoa Juridica (CNPJ)
	 * @return CNPJ
	 */
	public String getCNPJ() {
		return oidFieldsMap.get(CNPJ);
	}
}
