package br.com.renanfretta.emprestimos_online.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanfretta.emprestimos_online.models.Cliente;
import br.com.renanfretta.emprestimos_online.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@PostMapping("/autenticar")
	public ResponseEntity<Cliente> autenticar(@RequestParam(required = true) Long cpf, @RequestParam(required = true) String email) {
		Cliente cliente = clienteService.autenticar(cpf, email);
		return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();  
	}

}