package br.com.cresol.desafio.exception;

public class RegraNegocioException extends Exception {

	private static final long serialVersionUID = 1L;

	public RegraNegocioException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RegraNegocioException(String arg0) {
		super(arg0);
	}

	public RegraNegocioException(Throwable arg0) {
		super(arg0);
	}

	
	
}
