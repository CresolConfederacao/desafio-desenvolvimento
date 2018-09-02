package br.com.cresol.desafio.service;

import br.com.cresol.desafio.dto.Cliente;
import br.com.cresol.desafio.repository.Repository;

public class ClienteService {
	
	public void adicionaCliente(String cpf, String nome, String email) {
		boolean clienteExistente = Repository.getCliente(cpf) != null;
		if (!clienteExistente) {
			Cliente cliente = new Cliente();
			cliente.setCpf(cpf);
			cliente.setEmail(email);
			cliente.setNome(nome);
			Repository.addCliente(cliente);
		}
	}

	public Cliente buscaCliente(String cpf, String email) {
		Cliente cliente = Repository.getCliente(cpf);
		if (cliente != null && cliente.getEmail().equals(email)) {
			return cliente;
		}
		return null;
	}

}
