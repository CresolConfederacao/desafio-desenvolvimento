package br.com.cresol.desafio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class ParcelaEmprestimo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer numeroContrato;
	private Integer numeroDaParcela;
	private BigDecimal valorParcela;
	private Date dataVencimento;

	public ParcelaEmprestimo() {
		super();
	}

	public Integer getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(Integer numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public Integer getNumeroDaParcela() {
		return numeroDaParcela;
	}

	public void setNumeroDaParcela(Integer numeroDaParcela) {
		this.numeroDaParcela = numeroDaParcela;
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

}
