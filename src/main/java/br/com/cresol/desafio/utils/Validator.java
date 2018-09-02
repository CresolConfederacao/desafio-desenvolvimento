package br.com.cresol.desafio.utils;

import java.util.regex.Pattern;

public class Validator {

	public static boolean validaEmail(String email) {
		Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
		return pattern.matcher(email).matches();
	}

	public static boolean validaCPF(String cpf) {
		if ((cpf==null) || (cpf.length()!=11)) 
			return false;
		int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
		Integer digitoUm = calcularDigito(cpf.substring(0,9), pesoCPF);
		Integer digitoDois = calcularDigito(cpf.substring(0,9) + digitoUm, pesoCPF);
		return cpf.equals(cpf.substring(0,9) + digitoUm.toString() + digitoDois.toString());
	}
	
	private static int calcularDigito(String numeros, int[] peso) {
		int soma = 0;
	    for (int indice=numeros.length()-1, digito; indice >= 0; indice-- ) {
	       digito = Integer.parseInt(numeros.substring(indice,indice+1));
	       soma += digito*peso[peso.length-numeros.length()+indice];
	    }
	    soma = 11 - soma % 11;
	    return soma > 9 ? 0 : soma;
	}
	
}
