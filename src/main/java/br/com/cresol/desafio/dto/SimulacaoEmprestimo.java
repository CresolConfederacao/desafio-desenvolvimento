package br.com.cresol.desafio.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author evandro
 *
 */
public class SimulacaoEmprestimo {

	@Override
	public String toString() {
		return String.format(
				"SimulacaoEmprestimo [idContrato=%s, valorContrato=%s, quantidadeParcelas=%s, valorParcelas=%s, taxaJuros=%s, dataSimulacao=%s, dataValidade=%s]",
				idContrato, valorContrato, quantidadeParcelas, valorParcelas, taxaJuros, dataSimulacao, dataValidade);
	}

	private Long idContrato;
	private BigDecimal valorContrato;
	private Integer quantidadeParcelas;
	private BigDecimal valorParcelas;
	private BigDecimal taxaJuros;
	private Date dataSimulacao;
	private Date dataValidade;

	public Long getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(final Long idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getValorContrato() {
		return this.valorContrato;
	}

	public void setValorContrato(final BigDecimal valorContrato) {
		this.valorContrato = valorContrato;
	}

	public Integer getQuantidadeParcelas() {
		return this.quantidadeParcelas;
	}

	public void setQuantidadeParcelas(final Integer quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}

	public BigDecimal getValorParcelas() {
		return this.valorParcelas;
	}

	public void setValorParcelas(final BigDecimal valorParcelas) {
		this.valorParcelas = valorParcelas;
	}

	public BigDecimal getTaxaJuros() {
		return this.taxaJuros;
	}

	public void setTaxaJuros(final BigDecimal taxaJuros) {
		this.taxaJuros = taxaJuros;
	}

	public Date getDataSimulacao() {
		return this.dataSimulacao;
	}

	public void setDataSimulacao(final Date dataSimulacao) {
		this.dataSimulacao = dataSimulacao;
	}

	public Date getDataValidade() {
		return this.dataValidade;
	}

	public void setDataValidade(final Date dataValidade) {
		this.dataValidade = dataValidade;
	}
}
