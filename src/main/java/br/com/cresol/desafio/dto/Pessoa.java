package br.com.cresol.desafio.dto;

public class Pessoa {

	@Override
	public String toString() {
		return String.format("Pessoa [nome=%s, cpf=%s, email=%s]", nome, cpf, email);
	}

	private String nome;
	private String cpf;
	private String email;

	public String getNome() {
		return nome;
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
}
