package br.com.cresol.desafio.dto;

import java.math.BigDecimal;

/**
 * @author evandro
 *
 */
public class SimulacaoEmprestimo {

	private Long id;
	private Long numeroContrato;
	private String dataSimulacao;
	private String dataValidadeSimulacao;
	private BigDecimal valorContrato;
	private Integer quantidadeParcelas;
	private BigDecimal valorParcela;
	private BigDecimal taxaJurosEmprestimo;
	private Cliente cliente;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public Long getNumeroContrato() {
		return numeroContrato;
	}
	public void setNumeroContrato(Long numeroContrato) {
		this.numeroContrato = numeroContrato;
	}
	public String getDataSimulacao() {
		return dataSimulacao;
	}
	public void setDataSimulacao(String dataSimulacao) {
		this.dataSimulacao = dataSimulacao;
	}
	public String getDataValidadeSimulacao() {
		return dataValidadeSimulacao;
	}
	public void setDataValidadeSimulacao(String string) {
		this.dataValidadeSimulacao = string;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
