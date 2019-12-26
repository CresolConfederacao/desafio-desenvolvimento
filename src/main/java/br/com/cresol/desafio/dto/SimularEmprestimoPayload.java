package br.com.cresol.desafio.dto;

import java.math.BigDecimal;

/**
 * @author evandro
 *
 */
public class SimularEmprestimoPayload {

	@Override
	public String toString() {
		return String.format(
				"SimularEmprestimoPayload [nome=%s, cpf=%s, email=%s, valorContrato=%s, quantidadeParcelas=%s]", nome,
				cpf, email, valorContrato, quantidadeParcelas);
	}

	private String nome;
	private String cpf;
	private String email;
	private BigDecimal valorContrato;
	private Integer quantidadeParcelas;

	public String getNome() {
		return this.nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(final String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
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
}
