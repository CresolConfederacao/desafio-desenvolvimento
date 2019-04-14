package br.com.renanfretta.emprestimos_online.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.renanfretta.emprestimos_online.models.Cliente;
import br.com.renanfretta.emprestimos_online.repositories.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente buscarClientePeloId(Long cpf) {
		Optional<Cliente> clienteBD = clienteRepository.findById(cpf);
		if (!clienteBD.isPresent())
			throw new EmptyResultDataAccessException(1);
		return clienteBD.get();
	}

	public Cliente autenticar(Long cpf, String email) {
		Cliente clienteBD = buscarClientePeloId(cpf);
		if (clienteBD.getEmail().equals(email))
			return clienteBD; 
		throw new EmptyResultDataAccessException(1);
	}

	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

}