package br.com.cresol.desafio.dto;

import java.util.Objects;

public class Pessoa {

	@Override
	public String toString() {
		return String.format("Pessoa [nome=%s, cpf=%s, email=%s]", nome, cpf, email);
	}

	private String nome;
	private final String cpf;
	private String email;

	public Pessoa(final String nome, final String cpf, final String email) {
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
	}

	public boolean isMesmosDados(final String nome, final String cpf, final String email) {
		final Pessoa dadosInformados = new Pessoa(nome, cpf, email);
		return this.equals(dadosInformados);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Pessoa)) {
			return false;
		}
		final Pessoa pessoa = (Pessoa) obj;
		return Objects.equals(this.nome, pessoa.nome) && Objects.equals(this.cpf, pessoa.cpf)
				&& Objects.equals(this.email, pessoa.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.nome, this.cpf, this.email);
	}

	public void atualizarDados(final String nome, final String email) {
		this.nome = nome;
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return this.cpf;
	}

	public String getEmail() {
		return this.email;
	}
}
