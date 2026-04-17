package br.gov.cmb.commons.certificado.oid;


/**
 * Classe OID 2.16.76.1.3.2<br>
 * <br>
 * Atributos de Pessoa Juridico<br>
 * <br>
 * Atributos:<br>
 *    * Nome do Responsavel do certificado
 * <br>
 * Conteudo:<br>
 *   nome do responsavel pelo certificado;.<br>
 * <br>
 * @author pjneves
 *
 */
public class OID_2_16_76_1_3_2 extends OIDGeneric {

	public static final String OID = "2.16.76.1.3.2";

	
	/**
	 * Nome do responsavel
	 * @return numero do Cadastro Especifico do INSS (CEI)
	 */
	public String getNome() {
		return super.getData();
	}
}
