package br.com.cresol.desafio.util;

import java.math.BigDecimal;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public final class ValidacaoUtil {

	private static final String IS_VALID_EMAIL = "[\\w&&[^_]][\\w-.+]+[@][\\w-]+[.][\\w-]+([.][\\w-]+)*";

	private static final BigDecimal ZERO_AS_BIG_DECIMAL = new BigDecimal(0);

	private static final int QUANTIDADE_MAXIMA_DE_PARCELAS = 24;

	public static void validaParametrosParaPessoas(final String nome, final String cpf, final String email)
			throws ValidacaoException {
		if (StringUtils.isBlank(nome)) {
			throw new ValidacaoException("Necessário informar o nome da pessoa para a requisição.");
		}
		if (!ValidacaoUtil.isCpfValido(cpf)) {
			throw new ValidacaoException(
					String.format("O CPF informado é inválido <%s>.", StringUtils.isBlank(cpf) ? "sem CPF" : cpf));
		}
		if (!ValidacaoUtil.isEmailValido(email)) {
			throw new ValidacaoException(String.format("O e-mail informado é inválido <%s>.",
					StringUtils.isBlank(email) ? "sem e-mail" : email));
		}
	}

	public static void validaParametrosParaContrato(final String cpfPessoa, final BigDecimal valorContrato,
			final Integer quantidadeParcelas) throws ValidacaoException {
		if (!ValidacaoUtil.isCpfValido(cpfPessoa)) {
			throw new ValidacaoException(String.format("O CPF de pessoa informado para o contrato é inválido <%s>.",
					StringUtils.isBlank(cpfPessoa) ? "sem CPF" : cpfPessoa));
		}
		if (!ValidacaoUtil.isValorContratoValido(valorContrato)) {
			throw new ValidacaoException("Necessário informar o valor de contrato para a requisição.");
		}
		final int validaQuantidadeParcelas = ValidacaoUtil.validaQuantidadeParcelas(quantidadeParcelas);
		if (validaQuantidadeParcelas < 0) {
			throw new ValidacaoException("Necessário informar a quantidade de parcelas para a requisição.");
		} else if (validaQuantidadeParcelas > 0) {
			throw new ValidacaoException(String.format(
					"A quantidade de parcelas informada está acima do máximo permitido - máximo <%s>, recebido <%s>.",
					QUANTIDADE_MAXIMA_DE_PARCELAS, String.valueOf(quantidadeParcelas)));
		}
	}

	/**
	 * Verifica se um CPF é válido.
	 * 
	 * @param cpf {@link String} : CPF a ser validado.
	 * @return boolean : <b>TRUE</b> caso o CPF seja válido.
	 */
	private static boolean isCpfValido(final String cpf) {
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
	private static boolean isEmailValido(final String email) {
		if (email == null) {
			return false;
		}
		return email.matches(IS_VALID_EMAIL);
	}

	/**
	 * Valida se o valor do contrato está valido, sendo que <b>não deve ser nulo</b>
	 * e ser <b>maior do que zero</b>.
	 * 
	 * @param valorContrato {@link BigDecimal} : valor do contrato.
	 * @return boolean : retorna <b>TRUE</b> caso esteja válido.
	 */
	private static boolean isValorContratoValido(final BigDecimal valorContrato) {
		if (Objects.isNull(valorContrato)) {
			return false;
		}
		return (valorContrato.compareTo(ZERO_AS_BIG_DECIMAL) > 0);
	}

	/**
	 * Valida a quantidade de parcelas informadas, com os seguintes critérios:<br>
	 * ► Deve haver no mínimo "1 (uma)" parcela;<br>
	 * ► Quantidade não deve ultrapassar a
	 * {@link #QUANTIDADE_MAXIMA_DE_PARCELAS}.<br>
	 * <br>
	 * •• O retorno inteiro (int) indica se a quantidade é válida, com a seguinte
	 * interpretação:<br>
	 * ► Retorno negativo (-1): quantidade <b>inválida</b> por não atender ao
	 * <b>mínimo exigido</b>;<br>
	 * ► Retorno positivo (1): quantidade <b>inválida</b> por não atender ao
	 * <b>máximo permitido</b>;<br>
	 * ► Retorno zero (0): quantidade <b>está válida</b>.
	 * 
	 * @param quantidadeParcelas {@link Integer} : quantidade de parcelas a ser
	 *                           validada.
	 * @return int
	 */
	private static int validaQuantidadeParcelas(final Integer quantidadeParcelas) {
		if (Objects.isNull(quantidadeParcelas) || quantidadeParcelas <= 0) {
			return -1; // * Retorna negativo, pois a quantidade mínima não foi atendida.
		}
		if (quantidadeParcelas > QUANTIDADE_MAXIMA_DE_PARCELAS) {
			return 1; // * Retorna positivo, pois a quantidade máxima não foi atendida.
		}
		return 0; // * Retorna zero, pois a quantidade está válida.
	}
}
