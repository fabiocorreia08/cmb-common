package br.gov.cmb.common.util;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidadorUtil {
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private ValidadorUtil() {
	}

	public static boolean isEmailValido(final String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean isCNPJValido(String cnpj) {
		return isCNPJValido(cnpj, false);
	}

	public static boolean isCNPJValido(String cnpj, boolean formatted) {
		if (cnpj != null && formatted) {
			cnpj = FormatadorUtils.limparFormatacao(cnpj);
		}

		if (cnpj.length() < 14) {
			cnpj = String.format("%014d", Long.parseLong(cnpj));
		}

		int soma = 0;
		String cnpjCalc = cnpj.substring(0, 12);

		char[] chrCnpj = cnpj.toCharArray();
		for (int i = 0; i < 4; i++) {
			if (chrCnpj[i] - 48 >= 0 && chrCnpj[i] - 48 <= 9) {
				soma += (chrCnpj[i] - 48) * (6 - (i + 1));
			}
		}

		for (int i = 0; i < 8; i++) {
			if (chrCnpj[i + 4] - 48 >= 0 && chrCnpj[i + 4] - 48 <= 9) {
				soma += (chrCnpj[i + 4] - 48) * (10 - (i + 1));
			}
		}

		int dig = 11 - soma % 11;
		cnpjCalc = (new StringBuilder(String.valueOf(cnpjCalc)))
				.append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();
		soma = 0;
		for (int i = 0; i < 5; i++) {
			if (chrCnpj[i] - 48 >= 0 && chrCnpj[i] - 48 <= 9) {
				soma += (chrCnpj[i] - 48) * (7 - (i + 1));
			}
		}

		for (int i = 0; i < 8; i++) {
			if (chrCnpj[i + 5] - 48 >= 0 && chrCnpj[i + 5] - 48 <= 9) {
				soma += (chrCnpj[i + 5] - 48) * (10 - (i + 1));
			}
		}

		dig = 11 - soma % 11;
		cnpjCalc = (new StringBuilder(String.valueOf(cnpjCalc)))
				.append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();

		return cnpj.equals(cnpjCalc);
	}

	public static boolean isCPFValido(String cpf, boolean formatted, boolean ignoreRepeated) {
		if (cpf != null && formatted) {
			cpf = FormatadorUtils.limparFormatacao(cpf);
		}

		if (!ignoreRepeated && (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999") || (cpf.length() != 11))) {
			return false;
		}

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);

			if ((r == 10) || (r == 11)) {
				dig10 = '0';
			} else {
				dig10 = (char) (r + 48);
			}

			sm = 0;
			peso = 11;

			for (i = 0; i < 10; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);

			if ((r == 10) || (r == 11)) {
				dig11 = '0';
			} else {
				dig11 = (char) (r + 48);
			}

			if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10))) {
				return (true);
			} else {
				return (false);
			}
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public static boolean isCPFValido(String cpf) {
		return isCPFValido(cpf, false, false);
	}

}
