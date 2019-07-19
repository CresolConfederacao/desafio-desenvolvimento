package br.com.cresol.desafio;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;
import br.com.cresol.desafio.service.EmprestimoService;
import br.com.cresol.desafio.util.Util;

public class UnitTest {
	
	EmprestimoService emprestimoService;
	
	SimularEmprestimoPayload payload;
	
	@Before
	public void setUp() {
		emprestimoService = new EmprestimoService();
		
		payload = new SimularEmprestimoPayload();
		payload.setNome("Arnaldo Coelho");
		payload.setCpf(81712077074L);
		payload.setEmail("arnaldo.coelho@gmail.com");
		payload.setValorContrato(BigDecimal.valueOf(12000));
		payload.setQuantidadePercelas(12);
	}
	
	
	@Test
	public void emailErrado() throws Exception {
		//Correto: arnaldo@globo.com
		assertEquals(false, Util.isEmail("arnaldoglobo.com"));
	}
	
	@Test
	public void cpfErrado() throws Exception {
		//Correto: 81712077074
		assertEquals(false, Util.isCPF("81712077073"));
	}
	
	
	@Test
	public void valorParcela() throws Exception {
		
		SimulacaoEmprestimo simulacaoEmprestimo = emprestimoService.simular(payload);
		
		
		assertEquals(BigDecimal.valueOf(1216.0).setScale(3, BigDecimal.ROUND_HALF_EVEN), simulacaoEmprestimo.getValorParcela());
	}

	
	@Test
	public void taxaDeJuros() throws Exception {
		
		SimulacaoEmprestimo simulacaoEmprestimo = emprestimoService.simular(payload);
		
		
		assertEquals(BigDecimal.valueOf(0.018), simulacaoEmprestimo.getTaxaJurosEmprestimo());
	}
	
	@Test(expected = java.rmi.ServerException.class)
	public void maiorQueQuantidadeMaximaParcelasException() throws Exception {
		
		payload.setQuantidadePercelas(25);
		SimulacaoEmprestimo simulacaoEmprestimo = emprestimoService.simular(payload);
		
		
		assertEquals(BigDecimal.valueOf(0.018), simulacaoEmprestimo.getTaxaJurosEmprestimo());
	}
	
	
	@Test
	public void maiorQueQuantidadeMaximaParcelas() throws Exception {

		payload.setQuantidadePercelas(25);
		
		assertEquals(true, emprestimoService.isMaiorMaximoParcelas(payload));
	}

}
