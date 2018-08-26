package br.com.cresol.desafio.dto;

import java.io.Serializable;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nome;
	private Integer CPF;
	private String email;

	public Cliente() {
		super();
	}
	
	public Cliente(String nome, Integer CPF, String email) {
		super();
		this.nome = nome;
		this.CPF = CPF;
		this.email = email;
	}



	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCPF() {
		return CPF;
	}

	public void setCPF(Integer cPF) {
		CPF = cPF;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
