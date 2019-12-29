package br.com.cresol.desafio.util;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.StringUtils;

public final class PayloadUtil {

	private static final String MASCARA_PADRAO_CPF = "###.###.###-##";

	public static String parseCpfToString(final Long cpfAsLong) {
		if (cpfAsLong == null) {
			return null;
		}
		String cpfAsString = Long.toString(cpfAsLong < 0 ? cpfAsLong * -1 : cpfAsLong);
		final int numerosFaltantes = (ValidacaoUtil.TAMANHO_CPF_NUMERICO - cpfAsString.length());
		if (numerosFaltantes > 0) {
			final StringBuilder complemento = new StringBuilder();
			for (int i = 0; i < numerosFaltantes; i++) {
				complemento.append("0");
			}
			cpfAsString = (complemento.toString() + cpfAsString);
		}
		String mascara = MASCARA_PADRAO_CPF;
		final int numerosExcedentes = (numerosFaltantes * -1);
		if (numerosExcedentes > 0) {
			final StringBuilder excedente = new StringBuilder();
			for (int i = 0; i < numerosExcedentes; i++) {
				excedente.append("#");
			}
			mascara = (excedente.toString() + mascara);
		}
		String cpfFormatado = cpfAsString;
		try {
			MaskFormatter mask = new MaskFormatter(mascara);
			mask.setValueContainsLiteralCharacters(false);
			cpfFormatado = mask.valueToString(cpfAsString);
		} catch (ParseException e) {
			throw new RuntimeException("Falha ao formatar o CPF convertido de Long para String.", e);
		}
		return cpfFormatado;
	}

	public static Long parseCpfToLong(final String cpfAsString) {
		if (StringUtils.isBlank(cpfAsString)) {
			return null;
		}
		final String cpfToParse = cpfAsString.replaceAll("[^0-9]", "");
		final long cpfAsLong = Long.parseLong(cpfToParse);
		return cpfAsLong;
	}
}
