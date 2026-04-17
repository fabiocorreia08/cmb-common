package br.gov.cmb.common.util;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

import br.gov.cmb.common.exception.runtime.CMBRuntimeException;

public final class FormatadorUtils {

	private static final String TELEFONE_MASK = "(##) ####-####";
	private static final String CELULAR_MASK = "(##) #?####-####";
	private static final String CPF_MASK = "###.###.###-##";
	private static final String CNPJ_MASK = "##.###.###/####-##";
	private static final String ZIPCODE_MASK = "#####-###";
	private static final String PIS = "###.#####.##-#";
	
	public FormatadorUtils() {}

	public static String limparFormatacao(String value) {
		return value != null ? value.replaceAll("[^0-9]", "") : null;
	}
		
	public static String formatarCpf(String cpf) {
		return format(CPF_MASK, cpf);
	}

	public static String formatarCnpj(String cnpj){
		return format(CNPJ_MASK, cnpj);
	}
	
	public static String formatarPis(String pis) {
		return format(PIS, pis);
	}
		
	public static String formatarCep(String cep){
		return format(ZIPCODE_MASK, cep);
	}
	
	public static String formatarTelefone(String telefone){
		return format(TELEFONE_MASK, telefone);
	}
	
	public static String formatarCelular(String celular){
		return format(CELULAR_MASK, celular);
	}

	private static String format(String pattern, Object value) {
        MaskFormatter mask;
        try {
            mask = new MaskFormatter(pattern);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(value);
        } catch (ParseException e) {
        	throw new CMBRuntimeException(e);
        }
    }
	

}