package br.com.renanfretta.emprestimos_online.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.renanfretta.emprestimos_online.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	public Cliente findByCpfAndEmail(Long cpf, String email);

}