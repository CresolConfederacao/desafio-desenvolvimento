package br.com.cresol.desafio.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ContratoEmprestimo {

	@Override
	public String toString() {
		return String.format(
				"ContratoEmprestimo [idContrato=%s, codDataIdContrato=%s, codSequenciaIdContrato=%s, cpfPessoa=%s, valorContrato=%s, quantidadeParcelas=%s, valorParcelas=%s, taxaJuros=%s, dataSimulacao=%s, dataValidade=%s]",
				idContrato, codDataIdContrato, codSequenciaIdContrato, cpfPessoa, valorContrato, quantidadeParcelas,
				valorParcelas, taxaJuros, dataSimulacao, dataValidade);
	}

	private final long idContrato;
	private final int codDataIdContrato;
	private final int codSequenciaIdContrato;

	private String cpfPessoa;
	private BigDecimal valorContrato;
	private Integer quantidadeParcelas;

	private BigDecimal valorParcelas;
	private BigDecimal taxaJuros;

	private final Date dataSimulacao;
	private Date dataValidade;

	public ContratoEmprestimo(final long idContrato, final int codDataIdContrato, final int codSequenciaIdContrato,
			final Date dataSimulacao) {
		this.idContrato = idContrato;
		this.codDataIdContrato = codDataIdContrato;
		this.codSequenciaIdContrato = codSequenciaIdContrato;
		this.dataSimulacao = dataSimulacao;
	}

	public long getIdContrato() {
		return this.idContrato;
	}

	public int getCodDataIdContrato() {
		return this.codDataIdContrato;
	}

	public int getCodSequenciaIdContrato() {
		return this.codSequenciaIdContrato;
	}

	public String getCpfPessoa() {
		return this.cpfPessoa;
	}

	public void setCpfPessoa(final String cpfPessoa) {
		this.cpfPessoa = cpfPessoa;
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

	public Date getDataValidade() {
		return this.dataValidade;
	}

	public void setDataValidade(final Date dataValidade) {
		this.dataValidade = dataValidade;
	}
}
