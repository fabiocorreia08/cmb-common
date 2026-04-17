package br.gov.cmb.commons.certificado.oid;


/**
 * Classe OID 2.16.76.1.3.8<br>
 * <br>
 * Atributos de Certificado de Equipaentos<br>
 * <br>
 * Atributos:<br>
 *    * nome empresarial constante do CNPJ sem abreviacoes
 * <br>
 * Conteudo:<br>
 *   Nome empresarial constante do CNPJ (Cadastro Nacional de Pessoa Juridica), sem abreviacoes, se o certificado for de pessoa juridica;<br>
 * <br>
 * @author pjneves
 *
 */
public class OID_2_16_76_1_3_8 extends OIDGeneric {

	public static final String OID = "2.16.76.1.3.2";

	
	/**
	 * Nome empresarial constante do CNPJ sem abreviacoes
	 * @return nome empresarial constante do CNPJ sem abreviacoes
	 */
	public String getNome() {
		return super.getData();
	}
}
