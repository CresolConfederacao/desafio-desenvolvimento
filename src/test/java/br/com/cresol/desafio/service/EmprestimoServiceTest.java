package br.com.cresol.desafio.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

import java.math.BigDecimal;
import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;
import br.com.cresol.desafio.exception.RegraNegocioException;

public class EmprestimoServiceTest {

	public static EmprestimoService emprestimoService = new EmprestimoService();

	public SimularEmprestimoPayload getInstanceSimularEmprestimoPayload() {
		SimularEmprestimoPayload payload = new SimularEmprestimoPayload();
		payload.setCPF(12345678);
		payload.setEmail("abonatti2@gmail.com");
		payload.setNome("Andre");
		payload.setQuantidadeParcelas(12);
		payload.setValorContrato(new BigDecimal("999"));
		return payload;
	}

	@Test
	public void simula_CaminhoFeliz() throws Exception {
		SimularEmprestimoPayload payload = getInstanceSimularEmprestimoPayload();

		SimulacaoEmprestimo actual = emprestimoService.simular(payload);

		SimulacaoEmprestimo expected = new SimulacaoEmprestimo(actual);
		expected.setTaxaJurosEmprestimo(new BigDecimal("1.8"));
		expected.setValorParcela(new BigDecimal("101.2319999999999999999999999999999667"));

		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void simula_Taxa() throws Exception {
		EmprestimoService emprestimoService = new EmprestimoService();
		SimularEmprestimoPayload payload = getInstanceSimularEmprestimoPayload();
		payload.setValorContrato(new BigDecimal("1000"));

		SimulacaoEmprestimo actual = emprestimoService.simular(payload);

		SimulacaoEmprestimo expected = new SimulacaoEmprestimo(actual);
		expected.setTaxaJurosEmprestimo(new BigDecimal("3"));
		expected.setValorParcela(new BigDecimal("113.3333333333333333333333333333333000"));

		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void simula_Taxa_Acrescimo() throws Exception {
		EmprestimoService emprestimoService = new EmprestimoService();
		SimularEmprestimoPayload payload = getInstanceSimularEmprestimoPayload();
		payload.setValorContrato(new BigDecimal("1000"));
		payload.setQuantidadeParcelas(15);

		SimulacaoEmprestimo actual = emprestimoService.simular(payload);

		System.out.println(actual.toString());

		SimulacaoEmprestimo expected = new SimulacaoEmprestimo(actual);
		expected.setTaxaJurosEmprestimo(new BigDecimal("3.5"));
		expected.setValorParcela(new BigDecimal("101.6666666666666666666666666666667000"));

		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void simularRN04() throws Exception {
		SimularEmprestimoPayload payload = getInstanceSimularEmprestimoPayload();
		payload.setQuantidadeParcelas(24);

		emprestimoService.simular(payload);
	}

	@Test(expected = RegraNegocioException.class)
	public void simularProblemaRN04() throws Exception {
		SimularEmprestimoPayload payload = getInstanceSimularEmprestimoPayload();
		payload.setQuantidadeParcelas(25);

		emprestimoService.simular(payload);
	}

}
