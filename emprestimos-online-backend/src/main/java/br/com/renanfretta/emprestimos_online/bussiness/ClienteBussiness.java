package br.com.renanfretta.emprestimos_online.bussiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.renanfretta.emprestimos_online.models.Cliente;
import br.com.renanfretta.emprestimos_online.services.ClienteService;

@Service
public class ClienteBussiness {
	
	@Autowired
	private ClienteService clienteService;
	
	public void validaCliente(Cliente cliente) {
		Cliente clienteBD;
		try {
			clienteBD = clienteService.buscarClientePeloId(cliente.getCpf());	
		} catch (EmptyResultDataAccessException e) {
			throw new IllegalArgumentException("O cliente informado não é válido.");
		}
		if (!cliente.getEmail().equalsIgnoreCase(clienteBD.getEmail()))
			throw new IllegalArgumentException("O cliente informado não é válido.");
	}
	
}