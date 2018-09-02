package br.com.cresol.desafio.dto;

import java.util.Date;

public class ParcelaEmprestimo {

	private String numeroDoContrato;
	private int numeroDaParcela;
	private float valorDaParcela;
	private Date dataDoVencimento;
	
	public String getNumeroDoContrato() {
		return numeroDoContrato;
	}
	public void setNumeroDoContrato(String numeroDoContrato) {
		this.numeroDoContrato = numeroDoContrato;
	}
	public int getNumeroDaParcela() {
		return numeroDaParcela;
	}
	public void setNumeroDaParcela(int numeroDaParcela) {
		this.numeroDaParcela = numeroDaParcela;
	}
	public float getValorDaParcela() {
		return valorDaParcela;
	}
	public void setValorDaParcela(float valorDaParcela) {
		this.valorDaParcela = valorDaParcela;
	}
	public Date getDataDoVencimento() {
		return dataDoVencimento;
	}
	public void setDataDoVencimento(Date dataDoVencimento) {
		this.dataDoVencimento = dataDoVencimento;
	}
	
}
