package br.com.heyjanac.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.heyjanac.desafio.exception.ResourceNotFoundException;
import br.com.heyjanac.desafio.model.Emprestimo;
import br.com.heyjanac.desafio.service.EmprestimoService;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

	@Autowired
	private EmprestimoService emprestimoService;

	public EmprestimoController() {
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "")
	public ResponseEntity<List<Emprestimo>> listarTodos() {
		return ResponseEntity.ok(emprestimoService.listarTodos());
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/{numeroContrato}")
	public ResponseEntity<Emprestimo> buscarPorNumeroContrato(
			@PathVariable(value = "numeroContrato") Long numeroContrato) throws ResourceNotFoundException {
		return ResponseEntity.ok(emprestimoService.buscarPorNumeroContrato(numeroContrato));
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, value = "")
	public Emprestimo simularEmprestimo(@RequestBody Emprestimo emprestimo) throws ResourceNotFoundException {
		return emprestimoService.salvarSimulacao(emprestimo);
	}

	@RequestMapping(method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{numeroContrato}")
	public Emprestimo contratarEmprestimo(@PathVariable(value = "numeroContrato") Long numeroContrato) throws ResourceNotFoundException {
		return emprestimoService.contratar(numeroContrato);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public void deletarEmprestimoPorId(@PathVariable(value = "id") Long id) {
		emprestimoService.deletar(id);
	}

}
