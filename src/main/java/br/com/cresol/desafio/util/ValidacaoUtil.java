package br.com.cresol.desafio.util;

import org.apache.commons.lang3.StringUtils;

public final class ValidacaoUtil {

	private static final String IS_VALID_EMAIL = "[\\w&&[^_]][\\w-.+]+[@][\\w-]+[.][\\w-]+([.][\\w-]+)*";

	/**
	 * Verifica se um CPF é válido.
	 * 
	 * @param cpf {@link String} : CPF a ser validado.
	 * @return boolean : <b>TRUE</b> caso o CPF seja válido.
	 */
	public static boolean isCpfValido(final String cpf) {
		if (StringUtils.isBlank(cpf)) {
			return false;
		}

		String cpfNumerico = cpf.trim().replaceAll("[\\.\\-\\/]", "");
		if (!StringUtils.isNumeric(cpfNumerico)) {
			return false;
		}

		boolean cpfValido = false;
		if (cpfNumerico.length() == 11) {
			int[] digitos = new int[11];
			for (int i = 0; i < 11; i++) {
				try {
					digitos[i] = Integer.parseInt(String.valueOf(cpfNumerico.charAt(i)));
				} catch (NumberFormatException e) {
					return false;
				}
			}

			int somaDV1 = 0;
			int somaDV2 = 0;
			for (int i = 0; i < 9; i++) {
				somaDV1 += digitos[i] * (10 - i);
				somaDV2 += digitos[i] * (11 - i);
			}
			somaDV2 += digitos[9] * 2;

			int restDV1;
			restDV1 = 11 - somaDV1 % 11;
			restDV1 = restDV1 == 10 ? 0 : (restDV1 == 11 ? 0 : restDV1);

			int restDV2;
			restDV2 = 11 - somaDV2 % 11;
			restDV2 = restDV2 == 10 ? 0 : (restDV2 == 11 ? 0 : restDV2);

			cpfValido = (restDV1 == digitos[9]) && (restDV2 == digitos[10]);
		}
		return cpfValido;
	}

	/**
	 * Valida um endereço de e-mail. Permite apenas e-mails que possuam: na primeira
	 * parte (antes do @), qualquer letra ou número, incluindo '-', '_' e '.'; na
	 * segunda parte (depois do @ e antes de algum ponto (.), qualquer letra ou
	 * número, incluindo '_' e '-'; na terceira parte (entre o primeiro e o segundo
	 * ponto (.), qualquer letra ou número, incluindo '_' e '-'; não existindo
	 * limite de quantas partes devem ser informadas após a terceira, seguindo as
	 * mesmas regras que a segunda e terceira partes.
	 * 
	 * @param email E-mail a ser validado
	 * @return Caso o e-mail seja aceito retornará true
	 */
	public static boolean isEmailValido(final String email) {
		if (email == null) {
			return false;
		}
		return email.matches(IS_VALID_EMAIL);
	}
}
