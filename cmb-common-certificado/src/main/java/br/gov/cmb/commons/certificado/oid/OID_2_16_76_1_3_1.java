package br.gov.cmb.commons.certificado.oid;


/**
 * Classe OID 2.16.76.1.3.1<br>
 * <br>
 * Atributos de Pessoa Fisica<br>
 * <br>
 * Atributos:<br>
 *    * Data Nascimento do titular<br>
 *    * CPF<br>
 *    * NIS (PIS,PASEP ou CI)<br>
 *    * RG<br>
 *    * Org�o Expedidor do RG e UF<br>
 * <br>
 * Conteudo:<br>
 *   nas primeiras 8 (oito) posicoes, a data de nascimento   do   titular,   no   formato   ddmmaaaa;<br>   
 *   nas 11 (onze) posicoes subsequentes, o Cadastro de Pessoa Fisica (CPF) do titular; <br>
 *   nas 11 (onze) posicoes subsequentes, o Numero de Identificacao Social- NIS (PIS, PASEP ou CI);<br> 
 *   nas 15 (quinze) posicoes   subsequentes, o numero do Registro Geral - RG do titular; <br>
 *   nas 10 (dez) posicoes subsequentes, as siglas do Orgao expedidor do RG e respectiva UF<br>
 * <br>
 * @author pjneves
 *
 */
public class OID_2_16_76_1_3_1 extends OIDGeneric {
	private static final String ORGAO_EXPEDIDOR = "orgaoExpedidor";
	private static final String RG = "rg";
	private static final String NIS = "nis";
	private static final String CPF = "cpf";
	private static final String DT_NASCIMENTO = "dtNascimento";


	public static final String OID = "2.16.76.1.3.1";

	
	static final OIDFormatField format[] = new OIDFormatField[] { 
									  OIDFormatField.getInstance(DT_NASCIMENTO, 8),
									  OIDFormatField.getInstance(CPF, 11),
									  OIDFormatField.getInstance(NIS, 11),
									  OIDFormatField.getInstance(RG, 15),
									  OIDFormatField.getInstance(ORGAO_EXPEDIDOR, 10)
									};
	
	@Override
    public void initialize() {
        super.initialize(format);
    }
	
	/**
	 * Data nascimento no formato ddmmaaaa
	 * @return Data nascimento no formato ddmmaaaa
	 */
	public String getDataNascimento() {
		return oidFieldsMap.get(DT_NASCIMENTO);
	}
	
	/**
	 * Cadastro de Pessoa Fisica (CPF) do titular
	 * @return CPF
	 */
	public String getCpf() {
		return oidFieldsMap.get(CPF);
	}
	
	/**
	 * N�mero de Identificacao Social- NIS (PIS, PASEP ou CI)
	 * @return NIS
	 */
	public String getNis() {
		return oidFieldsMap.get(NIS);
	}
	
	/**
	 * numero do Registro Geral - RG do titular
	 * @return RD do titular
	 */
	public String getRg() {
		return oidFieldsMap.get(RG);
	}
	
	/**
	 * siglas do Orgao expedidor do RG
	 * @return siglas do orgao expedidor
	 */
	public String getOrgaoExpedidorRg() {
		String oe = oidFieldsMap.get(ORGAO_EXPEDIDOR);
		
		if (oe != null) {
			int len = oe.length();
			oe = len > 0 ? oe.substring(0, len - 2) : oe;
		}
				
		return oe;
	}
	
	/**
	 * UF do orgao expedidor do RG
	 * @return sigla UF
	 */
	public String getUfOrgaoExpedidorRg() {
		String oe = oidFieldsMap.get(ORGAO_EXPEDIDOR);
		
		if (oe != null) {
			int len = oe.length();
			oe = len > 0 ? oe.substring(len - 2, len) : oe;
		}
				
		return oe;
	}


}
