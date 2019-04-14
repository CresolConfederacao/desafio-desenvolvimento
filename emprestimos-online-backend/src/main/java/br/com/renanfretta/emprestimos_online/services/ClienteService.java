package br.com.renanfretta.emprestimos_online.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renanfretta.emprestimos_online.models.Cliente;
import br.com.renanfretta.emprestimos_online.repositories.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente autenticar(Long cpf, String email) {
		return clienteRepository.findByCpfAndEmail(cpf, email);
	}

	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

}