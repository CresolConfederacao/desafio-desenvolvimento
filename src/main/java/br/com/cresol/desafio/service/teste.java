package br.com.cresol.desafio.service;

import java.math.BigDecimal;
import java.math.MathContext;

public class teste {

	public static void main(String[] args) {
		
		BigDecimal valorContrato = new BigDecimal("1000");
		Integer qtdParcelas = new Integer(12);
		BigDecimal taxajuros = new  BigDecimal("1.8").divide(new BigDecimal("100"));
		
		BigDecimal valorParcela = new BigDecimal(qtdParcelas).multiply(taxajuros);
		valorParcela = valorParcela.add(new BigDecimal("1"));
		valorParcela = valorParcela.divide(new BigDecimal(qtdParcelas), MathContext.DECIMAL128);
		valorParcela = valorParcela.multiply(valorContrato);
		
		System.out.println(valorParcela);
		
	}

	@SuppressWarnings("unused")
	private static void taxa() {
		BigDecimal valorContrato = new BigDecimal("1.8");
		
		if (valorContrato.compareTo(new BigDecimal("1000")) >=0) {
			System.out.println(">=0");
		}else{
			System.out.println("<0");
		}
		
		System.out.println(valorContrato);
		System.out.println(valorContrato.add(new BigDecimal("0.5")));
	}

}
