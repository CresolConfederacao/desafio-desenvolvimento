package br.com.heyjanac.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.heyjanac.desafio.model.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

	Cliente findFirstByCpf(String cpf);
	
}
