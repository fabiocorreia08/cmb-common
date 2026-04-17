package br.gov.cmb.commons.certificado.oid;


/**
 * Classe OID 2.16.76.1.3.5<br>
 * <br>
 * Atributos de Pessoa Fisica<br>
 * <br>
 * Atributos:<br>
 *    * Titulo eleitor<br>
 *    * Zona Eleitoral<br>
 *    * Secao<br>
 *    * Municipio e UF<br>
 * <br>
 * Conteudo:<br>
 *   nas primeiras 12 (doze) posicoes, o numero de inscricao do Titulo de Eleitor; <br>
 *   nas 3 (tres) posicoes subsequentes, a Zona Eleitoral; <br>
 *   nas 4 (quatro) posicoes seguintes, a Secao; <br>
 *   nas 22 (vinte e duas) posicoes subsequentes, o municipio e a UF do Titulo de Eleitor. <br>
 * <br>
 * @author pjneves
 *
 */
public class OID_2_16_76_1_3_5 extends OIDGeneric {
	private static final String TITULO = "titulo";
	private static final String ZONA = "zona";
	private static final String SECAO = "secao";
	private static final String MUNICIPIO = "municipio";


	public static final String OID = "2.16.76.1.3.5";

	
	static final OIDFormatField format[] = new OIDFormatField[] { 
									  OIDFormatField.getInstance(TITULO, 12),
									  OIDFormatField.getInstance(ZONA, 3),
									  OIDFormatField.getInstance(SECAO, 4),
									  OIDFormatField.getInstance(MUNICIPIO, 22)
									};
	
	@Override
    public void initialize() {
        super.initialize(format);
    }
	
	/**
	 * Titulo de eleitor
	 * @return Titulo de eleitor
	 */
	public String getTituloEleitor() {
		return oidFieldsMap.get(TITULO);
	}
	
	/**
	 * Zona Eleitoral
	 * @return Zona eleitoral
	 */
	public String getZonaEleitoral() {
		return oidFieldsMap.get(ZONA);
	}
	
	/**
	 * Secao
	 * @return Secao
	 */
	public String getSecao() {
		return oidFieldsMap.get(SECAO);
	}
	
	
	/**
	 * Municipio do titulo de eleitor
	 * @return Municipio do titulo de eleitor
	 */
	public String getMunicipio() {
		String oe = oidFieldsMap.get(MUNICIPIO);
		
		if (oe != null) {
			int len = oe.length();
			oe = len > 0 ? oe.substring(0, len - 2) : oe;
		}
				
		return oe;
	}
	
	/**
	 * UF do Municipio do titulo de eleitor
	 * @return sigla UF
	 */
	public String getUfMunicipio() {
		String oe = oidFieldsMap.get(MUNICIPIO);
		
		if (oe != null) {
			int len = oe.length();
			oe = len > 0 ? oe.substring(len - 2, len) : oe;
		}
				
		return oe;
	}


}
