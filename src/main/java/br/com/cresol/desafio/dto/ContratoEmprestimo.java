package br.com.cresol.desafio.dto;

import java.util.Date;
import java.util.List;

public class ContratoEmprestimo {
	
	private String numeroDoContrato;
	private Date dataDeContratacao;
	private float valorDoContrato;
	private int quantidadeDeParcelas;
	private float taxaDeJuros;
	private float iof;
	private List<ParcelaEmprestimo> parcelas;
	
	public String getNumeroDoContrato() {
		return numeroDoContrato;
	}
	public void setNumeroDoContrato(String numeroDoContrato) {
		this.numeroDoContrato = numeroDoContrato;
	}
	public Date getDataDeContratacao() {
		return dataDeContratacao;
	}
	public void setDataDeContratacao(Date dataDeContratacao) {
		this.dataDeContratacao = dataDeContratacao;
	}
	public float getValorDoContrato() {
		return valorDoContrato;
	}
	public void setValorDoContrato(float valorDoContrato) {
		this.valorDoContrato = valorDoContrato;
	}
	public int getQuantidadeDeParcelas() {
		return quantidadeDeParcelas;
	}
	public void setQuantidadeDeParcelas(int quantidadeDeParcelas) {
		this.quantidadeDeParcelas = quantidadeDeParcelas;
	}
	public float getTaxaDeJuros() {
		return taxaDeJuros;
	}
	public void setTaxaDeJuros(float taxaDeJuros) {
		this.taxaDeJuros = taxaDeJuros;
	}
	public float getIof() {
		return iof;
	}
	public void setIof(float iof) {
		this.iof = iof;
	}
	public List<ParcelaEmprestimo> getParcelas() {
		return parcelas;
	}
	public void setParcelas(List<ParcelaEmprestimo> parcelas) {
		this.parcelas = parcelas;
	}

}
