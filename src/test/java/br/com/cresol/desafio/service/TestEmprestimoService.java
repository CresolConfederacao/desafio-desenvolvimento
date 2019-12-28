package br.com.cresol.desafio.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTimeComparator;
import org.junit.Assert;
import org.junit.Test;

import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;
import br.com.cresol.desafio.manager.ContratoEmprestimoManager;
import br.com.cresol.desafio.manager.PessoaManager;
import br.com.cresol.desafio.util.CalculoUtil;
import br.com.cresol.desafio.util.MockUtil;
import br.com.cresol.desafio.util.ValidacaoException;

public class TestEmprestimoService {

	/**
	 * Formata a data para o padrão esperado como prefixo no ID_CONTRATO:
	 */
	private static final SimpleDateFormat dateFormatterParaIdContrato = new SimpleDateFormat("yyyyMMdd");
	/**
	 * TAMANHO_ID_CONTRATO = TAMANHO_PREFIXO_DATA + TAMANHO_POSFIXO_NRO_SEQUENCIAL:
	 */
	private static final int tamanhoIdContrato = (dateFormatterParaIdContrato.toPattern().length() + 6);
	/**
	 * Compara duas datas desconsiderando a parte das horas-minutos-segundos,
	 * permitindo verificar a diferença apenas no que se trata de ano-mês-dia.
	 */
	private static final DateTimeComparator dateOnlyComparator = DateTimeComparator.getDateOnlyInstance();

	@Test
	public void deveEfetuarNovaSimulacaoDeEmprestimo() throws Exception {
		final PessoaManager pessoaManager = MockUtil.initPessoaManagerMock();
		final ContratoEmprestimoManager contratoManager = MockUtil.initContratoManagerMock();

		final SimularEmprestimoPayload payload = new SimularEmprestimoPayload();
		payload.setNome("Lando Norris");
		payload.setCpf("673.350.557-68");
		payload.setEmail("lando.norris@gmail.com");
		payload.setValorContrato(new BigDecimal(2400));
		payload.setQuantidadeParcelas(10);

		final BigDecimal valorParcelasEsperado = new BigDecimal(312, CalculoUtil.MATH_CONTEXT_VALOR_PARCELAS);
		final BigDecimal taxaJurosEsperada = new BigDecimal(0.03, CalculoUtil.MATH_CONTEXT_TAXA_JUROS);
		final Date dataSimulacaoAproximada = new Date();
		final Date dataValidadeAproximada;
		{
			final Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 30);
			dataValidadeAproximada = calendar.getTime();
		}
		final String prefixoIdContrato = dateFormatterParaIdContrato.format(dataSimulacaoAproximada);

		final PessoaService pessoaService = new PessoaService(pessoaManager);
		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(contratoManager);

		final EmprestimoService emprestimoService = new EmprestimoService(pessoaService, contratoService);
		final SimulacaoEmprestimo simulacaoEmprestimo = emprestimoService.simular(payload);

		Assert.assertNotNull("Não retornou uma simulação de empréstimo!", simulacaoEmprestimo);
		{// * Testes sobre "idContrato":
			final Long idContrato = simulacaoEmprestimo.getIdContrato();
			Assert.assertNotNull("Simulação não possui número de contrato!", idContrato);
			final String stringIdContrato = Long.toString(idContrato);
			Assert.assertEquals("O 'idContrato' não está no tamanho esperado", tamanhoIdContrato,
					stringIdContrato.length());
			Assert.assertTrue(String.format(
					"O 'idContrato' não está iniciando com o padrão esperado (de data da simulação) - Inicio esperado: <%s>; idContrato: <%s>",
					prefixoIdContrato, stringIdContrato), stringIdContrato.startsWith(prefixoIdContrato));
		}
		Assert.assertEquals(0, payload.getValorContrato().compareTo(simulacaoEmprestimo.getValorContrato()));
		Assert.assertEquals(payload.getQuantidadeParcelas(), simulacaoEmprestimo.getQuantidadeParcelas());
		Assert.assertEquals(0, valorParcelasEsperado.compareTo(simulacaoEmprestimo.getValorParcelas()));
		Assert.assertEquals(0, taxaJurosEsperada.compareTo(simulacaoEmprestimo.getTaxaJuros()));
		Assert.assertEquals(0,
				dateOnlyComparator.compare(dataSimulacaoAproximada, simulacaoEmprestimo.getDataSimulacao()));
		Assert.assertEquals(0,
				dateOnlyComparator.compare(dataValidadeAproximada, simulacaoEmprestimo.getDataValidade()));

		final List<String> novasPessoasNoMock = MockUtil.getNovasPessoasNoMock(pessoaManager);
		Assert.assertEquals("Diferença na quantidade de pessoas inseridas:", 1, novasPessoasNoMock.size());
		Assert.assertEquals("A pessoa inserida não é a esperada: ", payload.getCpf(), novasPessoasNoMock.get(0));
		final List<Long> novosContratosNoMock = MockUtil.getNovosContratosNoMock(contratoManager);
		Assert.assertEquals("Diferença na quantidade de contratos inseridos:", 1, novosContratosNoMock.size());
		Assert.assertEquals("O contrato inserido não é o esperado: ", simulacaoEmprestimo.getIdContrato().longValue(),
				novosContratosNoMock.get(0).longValue());

		System.out.println("deveEfetuarNovaSimulacaoDeEmprestimo:\n\t" + simulacaoEmprestimo);
	}

	@Test
	public void deveRecuperarSimulacaoDeDadosExistentes() throws Exception {
		final PessoaManager pessoaManager = MockUtil.initPessoaManagerMock();
		final ContratoEmprestimoManager contratoManager = MockUtil.initContratoManagerMock();

		final SimularEmprestimoPayload payload = new SimularEmprestimoPayload();
		payload.setNome("Cleiton Janke");
		payload.setCpf("008.674.449-67");
		payload.setEmail("cleiton.janke@gmail.com");
		payload.setValorContrato(new BigDecimal(1500));
		payload.setQuantidadeParcelas(10);

		final BigDecimal valorParcelasEsperado = new BigDecimal(195, CalculoUtil.MATH_CONTEXT_VALOR_PARCELAS);
		final BigDecimal taxaJurosEsperada = new BigDecimal(0.03, CalculoUtil.MATH_CONTEXT_TAXA_JUROS);
		final Date dataSimulacaoAproximada = new Date();
		final Date dataValidadeAproximada;
		{
			final Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 30);
			dataValidadeAproximada = calendar.getTime();
		}
		final String prefixoIdContrato = dateFormatterParaIdContrato.format(dataSimulacaoAproximada);

		final PessoaService pessoaService = new PessoaService(pessoaManager);
		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(contratoManager);

		final EmprestimoService emprestimoService = new EmprestimoService(pessoaService, contratoService);
		final SimulacaoEmprestimo simulacaoEmprestimo = emprestimoService.simular(payload);

		Assert.assertNotNull("Não retornou uma simulação de empréstimo!", simulacaoEmprestimo);
		{// * Testes sobre "idContrato":
			final Long idContrato = simulacaoEmprestimo.getIdContrato();
			Assert.assertNotNull("Simulação não possui número de contrato!", idContrato);
			final String stringIdContrato = Long.toString(idContrato);
			Assert.assertEquals("O 'idContrato' não está no tamanho esperado", tamanhoIdContrato,
					stringIdContrato.length());
			Assert.assertTrue(String.format(
					"O 'idContrato' não está iniciando com o padrão esperado (de data da simulação) - Inicio esperado: <%s>; idContrato: <%s>",
					prefixoIdContrato, stringIdContrato), stringIdContrato.startsWith(prefixoIdContrato));
		}
		Assert.assertEquals(0, payload.getValorContrato().compareTo(simulacaoEmprestimo.getValorContrato()));
		Assert.assertEquals(payload.getQuantidadeParcelas(), simulacaoEmprestimo.getQuantidadeParcelas());
		Assert.assertEquals(0, valorParcelasEsperado.compareTo(simulacaoEmprestimo.getValorParcelas()));
		Assert.assertEquals(0, taxaJurosEsperada.compareTo(simulacaoEmprestimo.getTaxaJuros()));
		Assert.assertEquals(0,
				dateOnlyComparator.compare(dataSimulacaoAproximada, simulacaoEmprestimo.getDataSimulacao()));
		Assert.assertEquals(0,
				dateOnlyComparator.compare(dataValidadeAproximada, simulacaoEmprestimo.getDataValidade()));

		Assert.assertEquals("Diferença na quantidade de pessoas inseridas:", 0,
				MockUtil.getNovasPessoasNoMock(pessoaManager).size());
		Assert.assertEquals("Diferença na quantidade de pessoas com dados atualizados:", 0,
				MockUtil.getPessoasAtualizadasNoMock(pessoaManager).size());
		Assert.assertEquals("Diferença na quantidade de contratos inseridos:", 0,
				MockUtil.getNovosContratosNoMock(contratoManager).size());

		System.out.println("deveRecuperarSimulacaoDeDadosExistentes:\n\t" + simulacaoEmprestimo);
	}

	@Test
	public void deveRecuperarNovaSimulacaoParaPessoaExistente() throws Exception {
		final PessoaManager pessoaManager = MockUtil.initPessoaManagerMock();
		final ContratoEmprestimoManager contratoManager = MockUtil.initContratoManagerMock();

		final SimularEmprestimoPayload payload = new SimularEmprestimoPayload();
		payload.setNome("Charles Leclerc");
		payload.setCpf("575.825.218-20");
		payload.setEmail("charles.leclerc@gmail.com");
		payload.setValorContrato(new BigDecimal(600));
		payload.setQuantidadeParcelas(6);

		final BigDecimal valorParcelasEsperado = new BigDecimal(110.8, CalculoUtil.MATH_CONTEXT_VALOR_PARCELAS);
		final BigDecimal taxaJurosEsperada = new BigDecimal(0.018, CalculoUtil.MATH_CONTEXT_TAXA_JUROS);
		final Date dataSimulacaoAproximada = new Date();
		final Date dataValidadeAproximada;
		{
			final Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 30);
			dataValidadeAproximada = calendar.getTime();
		}
		final String prefixoIdContrato = dateFormatterParaIdContrato.format(dataSimulacaoAproximada);

		final PessoaService pessoaService = new PessoaService(pessoaManager);
		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(contratoManager);

		final EmprestimoService emprestimoService = new EmprestimoService(pessoaService, contratoService);
		final SimulacaoEmprestimo simulacaoEmprestimo = emprestimoService.simular(payload);

		Assert.assertNotNull("Não retornou uma simulação de empréstimo!", simulacaoEmprestimo);
		{// * Testes sobre "idContrato":
			final Long idContrato = simulacaoEmprestimo.getIdContrato();
			Assert.assertNotNull("Simulação não possui número de contrato!", idContrato);
			final String stringIdContrato = Long.toString(idContrato);
			Assert.assertEquals("O 'idContrato' não está no tamanho esperado", tamanhoIdContrato,
					stringIdContrato.length());
			Assert.assertTrue(String.format(
					"O 'idContrato' não está iniciando com o padrão esperado (de data da simulação) - Inicio esperado: <%s>; idContrato: <%s>",
					prefixoIdContrato, stringIdContrato), stringIdContrato.startsWith(prefixoIdContrato));
		}
		Assert.assertEquals(0, payload.getValorContrato().compareTo(simulacaoEmprestimo.getValorContrato()));
		Assert.assertEquals(payload.getQuantidadeParcelas(), simulacaoEmprestimo.getQuantidadeParcelas());
		Assert.assertEquals(0, valorParcelasEsperado.compareTo(simulacaoEmprestimo.getValorParcelas()));
		Assert.assertEquals(0, taxaJurosEsperada.compareTo(simulacaoEmprestimo.getTaxaJuros()));
		Assert.assertEquals(0,
				dateOnlyComparator.compare(dataSimulacaoAproximada, simulacaoEmprestimo.getDataSimulacao()));
		Assert.assertEquals(0,
				dateOnlyComparator.compare(dataValidadeAproximada, simulacaoEmprestimo.getDataValidade()));

		Assert.assertEquals("Diferença na quantidade de pessoas inseridas:", 0,
				MockUtil.getNovasPessoasNoMock(pessoaManager).size());
		Assert.assertEquals("Diferença na quantidade de pessoas com dados atualizados:", 0,
				MockUtil.getPessoasAtualizadasNoMock(pessoaManager).size());
		final List<Long> novosContratosNoMock = MockUtil.getNovosContratosNoMock(contratoManager);
		Assert.assertEquals("Diferença na quantidade de contratos inseridos:", 1, novosContratosNoMock.size());
		Assert.assertEquals("O contrato inserido não é o esperado: ", simulacaoEmprestimo.getIdContrato().longValue(),
				novosContratosNoMock.get(0).longValue());
		Assert.assertEquals("Diferença na quantidade de contratos removidos:", 0,
				MockUtil.getContratosRemovidosDoMock(contratoManager).size());

		System.out.println("deveRecuperarNovaSimulacaoParaPessoaExistente:\n\t" + simulacaoEmprestimo);
	}

	@Test(expected = ValidacaoException.class)
	public void deveFalharSimulacaoPorCpfInvalido() throws Exception {
		final PessoaManager pessoaManager = MockUtil.initPessoaManagerMock();
		final ContratoEmprestimoManager contratoManager = MockUtil.initContratoManagerMock();

		final SimularEmprestimoPayload payload = new SimularEmprestimoPayload();
		payload.setNome("Sebastian Vettel");
		payload.setCpf("555.263.170-22"); // * CPF correto: "550.263.170-22"
		payload.setEmail("sebastian.vettel@gmail.com");
		payload.setValorContrato(new BigDecimal(1000));
		payload.setQuantidadeParcelas(10);

		final PessoaService pessoaService = new PessoaService(pessoaManager);
		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(contratoManager);

		final EmprestimoService emprestimoService = new EmprestimoService(pessoaService, contratoService);
		final SimulacaoEmprestimo simulacaoEmprestimo = emprestimoService.simular(payload);

		Assert.assertNull("Retornou uma simulação de empréstimo mesmo com CPF inválido!", simulacaoEmprestimo);

		System.out.println("deveFalharSimulacaoPorCpfInvalido:\n\t" + simulacaoEmprestimo);
	}

	@Test(expected = ValidacaoException.class)
	public void deveFalharSimulacaoPorEmailInvalido() throws Exception {
		final PessoaManager pessoaManager = MockUtil.initPessoaManagerMock();
		final ContratoEmprestimoManager contratoManager = MockUtil.initContratoManagerMock();

		final SimularEmprestimoPayload payload = new SimularEmprestimoPayload();
		payload.setNome("Valteri Bottas");
		payload.setCpf("936.256.079-80");
		payload.setEmail("valteri.bottas@gmail.");
		payload.setValorContrato(new BigDecimal(1000));
		payload.setQuantidadeParcelas(10);

		final PessoaService pessoaService = new PessoaService(pessoaManager);
		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(contratoManager);

		final EmprestimoService emprestimoService = new EmprestimoService(pessoaService, contratoService);
		final SimulacaoEmprestimo simulacaoEmprestimo = emprestimoService.simular(payload);

		Assert.assertNull("Retornou uma simulação de empréstimo mesmo com quantidade de parcelas acima do permitido!",
				simulacaoEmprestimo);

		System.out.println("deveFalharSimulacaoPorEmailInvalido:\n\t" + simulacaoEmprestimo);
	}

	@Test(expected = ValidacaoException.class)
	public void deveFalharSimulacaoPorQuantidadeParcelasAcimaPermitido() throws Exception {
		final PessoaManager pessoaManager = MockUtil.initPessoaManagerMock();
		final ContratoEmprestimoManager contratoManager = MockUtil.initContratoManagerMock();

		final SimularEmprestimoPayload payload = new SimularEmprestimoPayload();
		payload.setNome("Sebastian Vettel");
		payload.setCpf("550.263.170-22");
		payload.setEmail("sebastian.vettel@gmail.com");
		payload.setValorContrato(new BigDecimal(1000));
		payload.setQuantidadeParcelas(25); // "máximo permitido = 24"

		final PessoaService pessoaService = new PessoaService(pessoaManager);
		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(contratoManager);

		final EmprestimoService emprestimoService = new EmprestimoService(pessoaService, contratoService);
		final SimulacaoEmprestimo simulacaoEmprestimo = emprestimoService.simular(payload);

		Assert.assertNull("Retornou uma simulação de empréstimo mesmo com quantidade de parcelas acima do permitido!",
				simulacaoEmprestimo);

		System.out.println("deveFalharSimulacaoPorQuantidadeParcelasAcimaPermitido:\n\t" + simulacaoEmprestimo);
	}
}
