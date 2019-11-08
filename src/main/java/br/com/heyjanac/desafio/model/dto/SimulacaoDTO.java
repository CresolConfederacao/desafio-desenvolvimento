package br.com.heyjanac.desafio.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SimulacaoDTO implements Serializable {

	private static final long serialVersionUID = -7303732725489015670L;

	private Long id;
	
	private Long numeroContrato;

	private Date dataSimulacao;

	private Date dataValidadeSimulacao;

	private BigDecimal valorContrato;

	private Integer quantidadeParcela;

	private BigDecimal valorParcela;

	private BigDecimal valorTaxaJuros;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(Long numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public Date getDataSimulacao() {
		return dataSimulacao;
	}

	public void setDataSimulacao(Date dataSimulacao) {
		this.dataSimulacao = dataSimulacao;
	}

	public Date getDataValidadeSimulacao() {
		return dataValidadeSimulacao;
	}

	public void setDataValidadeSimulacao(Date dataValidadeSimulacao) {
		this.dataValidadeSimulacao = dataValidadeSimulacao;
	}

	public BigDecimal getValorContrato() {
		return valorContrato;
	}

	public void setValorContrato(BigDecimal valorContrato) {
		this.valorContrato = valorContrato;
	}

	public Integer getQuantidadeParcela() {
		return quantidadeParcela;
	}

	public void setQuantidadeParcela(Integer quantidadeParcela) {
		this.quantidadeParcela = quantidadeParcela;
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

	public BigDecimal getValorTaxaJuros() {
		return valorTaxaJuros;
	}

	public void setValorTaxaJuros(BigDecimal valorTaxaJuros) {
		this.valorTaxaJuros = valorTaxaJuros;
	}

}
