package br.com.cresol.desafio.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.cresol.desafio.dto.Cliente;
import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmprestimoServiceTest {

	EmprestimoService emprestimoService;
	
	@Before
	public void setUp() throws Exception {
		emprestimoService = new EmprestimoService();
	}

	@Test
	public void testASimular() {
		SimularEmprestimoPayload payload = getPayload();
		
		SimulacaoEmprestimo simulacao = emprestimoService.simular(payload);
		assertEquals(payload.getQuantidadeDeParcelas(), simulacao.getQuantidadeDeParcelas());
		assertEquals(payload.getValorDoContrato(), simulacao.getValorDoContrato(), 0.0f);
		assertEquals(payload.getCpf(), simulacao.getCpfDoCliente());
		assertEquals(0.018f, simulacao.getTaxaDeJuros(), 0.0f);
		assertEquals(218f, simulacao.getValorDaParcela(), 0.0f);
		
	}

	@Test
	public void testBBuscarSimulacoes() {
		Cliente cliente = getCliente();
		
		List<SimulacaoEmprestimo> simulacoes = emprestimoService.buscarSimulacoes(cliente);
		assertFalse(simulacoes.isEmpty());
		assertTrue(simulacoes.size() == 1);
	}

	@Test
	public void testCRemoverSimulacao() throws Exception {
		SimulacaoEmprestimo simulacao = emprestimoService.simular(getPayload());
		
		emprestimoService.removerSimulacao(simulacao.getNumeroDoContrato());
	}

	@Test
	public void testDContratar() throws Exception {
		SimulacaoEmprestimo simulacao = emprestimoService.simular(getPayload());
		
		emprestimoService.contratar(simulacao.getNumeroDoContrato());
	}

	private SimularEmprestimoPayload getPayload() {
		SimularEmprestimoPayload payload = new SimularEmprestimoPayload();
		Cliente cliente = getCliente();
		payload.setCpf(cliente.getCpf());
		payload.setEmail(cliente.getEmail());
		payload.setNome(cliente.getNome());
		payload.setQuantidadeDeParcelas(5);
		payload.setValorDoContrato(1000f);
		return payload;
	}
	
	private Cliente getCliente() {
		Cliente cliente = new Cliente();
		cliente.setCpf("46095280290");
		cliente.setEmail("gustavo@email.com");
		cliente.setNome("Gustavo");
		return cliente;
	}

}
