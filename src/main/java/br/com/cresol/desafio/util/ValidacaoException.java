package br.com.cresol.desafio.util;

public class ValidacaoException extends Exception {

	private static final long serialVersionUID = -3299022215899085491L;

	public ValidacaoException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ValidacaoException(final String message) {
		super(message);
	}

	public ValidacaoException(final Throwable cause) {
		super(cause);
	}
}
