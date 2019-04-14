package br.com.renanfretta.emprestimos_online.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanfretta.emprestimos_online.models.SimularEmprestimo;
import br.com.renanfretta.emprestimos_online.services.SimularEmprestimoService;

@RestController
@RequestMapping("/simulacoes")
public class SimularEmprestimoResource {
	
	@Autowired
	private SimularEmprestimoService simularEmprestimoService;
	
	@PostMapping("/simular")
	public ResponseEntity<SimularEmprestimo> salvar(@Valid @RequestBody SimularEmprestimo simularEmprestimo, HttpServletResponse response) {
		SimularEmprestimo simularEmprestimoBD = simularEmprestimoService.salvar(simularEmprestimo);
		return ResponseEntity.status(HttpStatus.CREATED).body(simularEmprestimoBD);
	}

}