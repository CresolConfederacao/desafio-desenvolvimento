package br.com.heyjanac.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.heyjanac.desafio.model.Emprestimo;

@Repository
public interface IEmprestimoRepository extends JpaRepository<Emprestimo, Long> {

	Emprestimo findByNumeroContrato(Long numeroContrato);
	
	@Query(value="SELECT * FROM TB_EMPRESTIMO WHERE NU_STATUS = 0 ", nativeQuery=true)
	Emprestimo findByNumeroContratoSimulado(Long numeroContrato);
	
	@Query(value="SELECT * FROM TB_EMPRESTIMO WHERE NU_STATUS = 1 ", nativeQuery=true)
	Emprestimo findByNumeroContratoEfetivado(Long numeroContrato);
	
	@Query(value="SELECT MAX(NU_CONTRATO) FROM TB_EMPRESTIMO ",nativeQuery=true)
	Long findByUltimoNumeroContrato();
	
}
