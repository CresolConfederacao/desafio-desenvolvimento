package br.com.renanfretta.emprestimos_online.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.renanfretta.emprestimos_online.models.SimularEmprestimo;

public interface SimularEmprestimoRepository extends JpaRepository<SimularEmprestimo, Integer> {
	
}