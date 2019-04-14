package br.com.renanfretta.emprestimos_online.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		// FIXME: este método deve ser alterado para ser utilizado como autenticação para os demais serviços
		// pois a aplicação fica vulnerável a acesso direto sem restrições de segurança
		Cliente cliente = clienteService.autenticar(cpf, email);
		return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();  
	}
	
	@PostMapping
	public ResponseEntity<Cliente> salvar(@Valid @RequestBody Cliente cliente, HttpServletResponse response) {
		// FIXME: o sistema não apresenta erro ao salvar o mesmo registro com o CPF já cadastrado
		// Este problema acontece quando não usamos uma PK autoincremento com o hibernate.
		// Incluir uma validação antes de salvar
		Cliente clienteBD = clienteService.salvar(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteBD);
	}

}