package br.com.cresol.desafio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author evandro
 *
 */
public class SimulacaoEmprestimo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer numeroContrato;
	private Date dataSimulacao;
	private Date dataValidadeSimulacao;
	private BigDecimal valorContrato;
	private Integer quantidadeParcelas;
	private BigDecimal valorParcela;
	private BigDecimal taxaJurosEmprestimo;

	public SimulacaoEmprestimo() {
		super();
	}

	public SimulacaoEmprestimo(SimulacaoEmprestimo simulacaoEmprestimo) {
		super();
		this.numeroContrato = simulacaoEmprestimo.numeroContrato;
		this.dataSimulacao = simulacaoEmprestimo.dataSimulacao;
		this.dataValidadeSimulacao = simulacaoEmprestimo.dataValidadeSimulacao;
		this.valorContrato = simulacaoEmprestimo.valorContrato;
		this.quantidadeParcelas = simulacaoEmprestimo.quantidadeParcelas;
		this.valorParcela = simulacaoEmprestimo.valorParcela;
		this.taxaJurosEmprestimo = simulacaoEmprestimo.taxaJurosEmprestimo;
	}

	public Integer getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(Integer numeroContrato) {
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

	public Integer getQuantidadeParcelas() {
		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(Integer quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

	public BigDecimal getTaxaJurosEmprestimo() {
		return taxaJurosEmprestimo;
	}

	public void setTaxaJurosEmprestimo(BigDecimal taxaJurosEmprestimo) {
		this.taxaJurosEmprestimo = taxaJurosEmprestimo;
	}

	@Override
	public String toString() {
		return "SimulacaoEmprestimo [numeroContrato=" + numeroContrato + ", dataSimulacao=" + dataSimulacao
				+ ", dataValidadeSimulacao=" + dataValidadeSimulacao + ", valorContrato=" + valorContrato
				+ ", quantidadeParcelas=" + quantidadeParcelas + ", valorParcela=" + valorParcela
				+ ", taxaJurosEmprestimo=" + taxaJurosEmprestimo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataSimulacao == null) ? 0 : dataSimulacao.hashCode());
		result = prime * result + ((dataValidadeSimulacao == null) ? 0 : dataValidadeSimulacao.hashCode());
		result = prime * result + ((numeroContrato == null) ? 0 : numeroContrato.hashCode());
		result = prime * result + ((quantidadeParcelas == null) ? 0 : quantidadeParcelas.hashCode());
		result = prime * result + ((taxaJurosEmprestimo == null) ? 0 : taxaJurosEmprestimo.hashCode());
		result = prime * result + ((valorContrato == null) ? 0 : valorContrato.hashCode());
		result = prime * result + ((valorParcela == null) ? 0 : valorParcela.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimulacaoEmprestimo other = (SimulacaoEmprestimo) obj;
		if (dataSimulacao == null) {
			if (other.dataSimulacao != null)
				return false;
		} else if (!dataSimulacao.equals(other.dataSimulacao))
			return false;
		if (dataValidadeSimulacao == null) {
			if (other.dataValidadeSimulacao != null)
				return false;
		} else if (!dataValidadeSimulacao.equals(other.dataValidadeSimulacao))
			return false;
		if (numeroContrato == null) {
			if (other.numeroContrato != null)
				return false;
		} else if (!numeroContrato.equals(other.numeroContrato))
			return false;
		if (quantidadeParcelas == null) {
			if (other.quantidadeParcelas != null)
				return false;
		} else if (!quantidadeParcelas.equals(other.quantidadeParcelas))
			return false;
		if (taxaJurosEmprestimo == null) {
			if (other.taxaJurosEmprestimo != null)
				return false;
		} else if (!taxaJurosEmprestimo.equals(other.taxaJurosEmprestimo))
			return false;
		if (valorContrato == null) {
			if (other.valorContrato != null)
				return false;
		} else if (!valorContrato.equals(other.valorContrato))
			return false;
		if (valorParcela == null) {
			if (other.valorParcela != null)
				return false;
		} else if (!valorParcela.equals(other.valorParcela))
			return false;
		return true;
	}

}
