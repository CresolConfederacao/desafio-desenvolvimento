package br.com.cresol.desafio.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.cresol.desafio.dto.ContratoEmprestimo;
import br.com.cresol.desafio.manager.ContratoEmprestimoManager;
import br.com.cresol.desafio.mock.ContratoEmprestimoManagerMock;
import br.com.cresol.desafio.util.CalculoUtil;
import br.com.cresol.desafio.util.ValidacaoException;

public class TestContratoEmprestimoService {

	private ContratoEmprestimoManager initContratoManagerMock() {
		return this.initContratoManagerMock(true);
	}

	private ContratoEmprestimoManager initContratoManagerMock(final boolean efetuaPreCarga) {
		final ContratoEmprestimoManagerMock mock = new ContratoEmprestimoManagerMock();
		if (efetuaPreCarga) {
			final Date dataSimulacao = new Date();

			final Calendar calendarValidade = Calendar.getInstance();
			calendarValidade.setTime(dataSimulacao);
			calendarValidade.add(Calendar.DAY_OF_YEAR, 30);

			// * Cleiton Janke:
			mock.addContrato("008.674.449-67", new BigDecimal(1500), 10, new BigDecimal(195),
					new BigDecimal(0.03, CalculoUtil.MATH_CONTEXT_TAXA_JUROS), dataSimulacao,
					calendarValidade.getTime());
			// * Charles Leclerc:
			mock.addContrato("575.825.218-20", new BigDecimal(1800), 15, new BigDecimal(183),
					new BigDecimal(0.035, CalculoUtil.MATH_CONTEXT_TAXA_JUROS), dataSimulacao,
					calendarValidade.getTime());
			// * Valteri Bottas:
			mock.addContrato("936.256.079-80", new BigDecimal(2400), 12, new BigDecimal(272),
					new BigDecimal(0.03, CalculoUtil.MATH_CONTEXT_TAXA_JUROS), dataSimulacao,
					calendarValidade.getTime());
			mock.zerarListasAuxiliaresDoMock();
		}
		return (ContratoEmprestimoManager) mock;
	}

	private List<Long> getNovosContratosNoMock(final ContratoEmprestimoManager contratoManager) {
		return ((ContratoEmprestimoManagerMock) contratoManager).getListaContratosAdicionados();
	}

	private List<Long> getContratosRemovidosDoMock(final ContratoEmprestimoManager contratoManager) {
		return ((ContratoEmprestimoManagerMock) contratoManager).getListaContratosRemovidos();
	}

	@Test
	public void deveRecuperarDadosContratoExistente() throws Exception {
		final ContratoEmprestimoManager contratoManager = this.initContratoManagerMock();

		final String cpfPessoa = "008.674.449-67"; // * Cleiton Janke
		final BigDecimal valorContrato = new BigDecimal(1500);
		final Integer quantidadeParcelas = 10;

		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(contratoManager);
		final ContratoEmprestimo contrato = contratoService.getDadosContrato(cpfPessoa, valorContrato,
				quantidadeParcelas);

		Assert.assertNotNull("Contrato não encontrado!", contrato);

		System.out.println("deveRecuperarDadosContratoExistente:\n\t" + contrato);
	}

	@Test
	public void deveRecuperarDadosInserindoNovoContrato() throws Exception {
		final ContratoEmprestimoManager contratoManager = this.initContratoManagerMock();

		final String cpfPessoa = "429.142.204-05"; // * Lewis Hamilton
		final BigDecimal valorContrato = new BigDecimal(800);
		final Integer quantidadeParcelas = 7;

		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(contratoManager);
		final ContratoEmprestimo contrato = contratoService.getDadosContrato(cpfPessoa, valorContrato,
				quantidadeParcelas);

		Assert.assertNotNull("Contrato não encontrado!", contrato);
		Assert.assertEquals(cpfPessoa, contrato.getCpfPessoa());
		Assert.assertEquals(valorContrato, contrato.getValorContrato());
		Assert.assertEquals(quantidadeParcelas, contrato.getQuantidadeParcelas());

		final List<Long> novosContratosNoMock = this.getNovosContratosNoMock(contratoManager);
		Assert.assertEquals("Diferença na quantidade de contratos inseridos:", 1, novosContratosNoMock.size());
		Assert.assertEquals("O contrato inserido não é o esperado: ", contrato.getIdContrato(),
				novosContratosNoMock.get(0).longValue());

		System.out.println("deveRecuperarDadosInserindoNovoContrato:\n\t" + contrato);
	}

	@Test
	public void deveRecuperarNovosDadosPorContaDeContratoExpirado() throws Exception {
		final ContratoEmprestimoManager contratoManagerSemPreCarga = this.initContratoManagerMock(false);

		final String cpfPessoa = "008.674.449-67"; // * Cleiton Janke
		final BigDecimal valorContrato = new BigDecimal(1500);
		final Integer quantidadeParcelas = 10;

		{// * Efetua pré-carga de contrato já expirado:
			final ContratoEmprestimoManagerMock mock = (ContratoEmprestimoManagerMock) contratoManagerSemPreCarga;

			final Calendar calendarSimulacao = Calendar.getInstance();
			calendarSimulacao.add(Calendar.DAY_OF_YEAR, -31); // "Simulação efetuada a mais de 30 dias"

			final Calendar calendarValidade = Calendar.getInstance();
			calendarValidade.setTimeInMillis(calendarSimulacao.getTimeInMillis());
			calendarValidade.add(Calendar.DAY_OF_YEAR, 30); // "Data de validade já expirada"

			// * Cleiton Janke:
			mock.addContrato(cpfPessoa, valorContrato, quantidadeParcelas, new BigDecimal(195),
					new BigDecimal(0.03, CalculoUtil.MATH_CONTEXT_TAXA_JUROS), calendarSimulacao.getTime(),
					calendarValidade.getTime());
			mock.zerarListasAuxiliaresDoMock();
		}

		final ContratoEmprestimo contratoExpirado = contratoManagerSemPreCarga.getContrato(cpfPessoa, valorContrato,
				quantidadeParcelas);

		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(contratoManagerSemPreCarga);
		final ContratoEmprestimo contrato = contratoService.getDadosContrato(cpfPessoa, valorContrato,
				quantidadeParcelas);

		Assert.assertNotNull("Contrato não encontrado!", contrato);
		Assert.assertEquals(cpfPessoa, contrato.getCpfPessoa());
		Assert.assertEquals(valorContrato, contrato.getValorContrato());
		Assert.assertEquals(quantidadeParcelas, contrato.getQuantidadeParcelas());

		Assert.assertNotEquals(contratoExpirado.getIdContrato(), contrato.getIdContrato());

		final List<Long> contratosRemovidosDoMock = this.getContratosRemovidosDoMock(contratoManagerSemPreCarga);
		Assert.assertEquals("Diferença na quantidade de contratos removidos:", 1, contratosRemovidosDoMock.size());
		Assert.assertEquals("O contrato removido não é o que estava expirado: ", contratoExpirado.getIdContrato(),
				contratosRemovidosDoMock.get(0).longValue());

		final List<Long> novosContratosNoMock = this.getNovosContratosNoMock(contratoManagerSemPreCarga);
		Assert.assertEquals("Diferença na quantidade de contratos inseridos:", 1, novosContratosNoMock.size());
		Assert.assertEquals("O contrato inserido não é o esperado: ", contrato.getIdContrato(),
				novosContratosNoMock.get(0).longValue());

		System.out.println("deveRecuperarNovosDadosPorContaDeContratoExpirado:\n--> Expirado:\n\t" + contratoExpirado
				+ "\n--> Novo:\n\t" + contrato);
	}

	@Test
	public void deveCalcularTaxaJurosEsperada() throws Exception {
		{// * Teste 1:
			final BigDecimal valorContrato = new BigDecimal(1500);
			final Integer quantidadeParcelas = 10;
			final BigDecimal taxaJurosEsperada = new BigDecimal(0.03, CalculoUtil.MATH_CONTEXT_TAXA_JUROS);

			final BigDecimal taxaJurosCalculada = ContratoEmprestimoService.calculaTaxaJuros(valorContrato,
					quantidadeParcelas);

			Assert.assertEquals(
					String.format("Esperado <%s>, mas calculado <%s>", taxaJurosEsperada, taxaJurosCalculada), 0,
					taxaJurosEsperada.compareTo(taxaJurosCalculada));

			System.out.println("deveCalcularTaxaJurosEsperada - teste 1:\n\t" + taxaJurosCalculada);
		}
		{// * Teste 2:
			final BigDecimal valorContrato = new BigDecimal(800);
			final Integer quantidadeParcelas = 8;
			final BigDecimal taxaJurosEsperada = new BigDecimal(0.018, CalculoUtil.MATH_CONTEXT_TAXA_JUROS);

			final BigDecimal taxaJurosCalculada = ContratoEmprestimoService.calculaTaxaJuros(valorContrato,
					quantidadeParcelas);

			Assert.assertEquals(
					String.format("Esperado <%s>, mas calculado <%s>", taxaJurosEsperada, taxaJurosCalculada), 0,
					taxaJurosEsperada.compareTo(taxaJurosCalculada));

			System.out.println("deveCalcularTaxaJurosEsperada - teste 2:\n\t" + taxaJurosCalculada);
		}
		{// * Teste 3:
			final BigDecimal valorContrato = new BigDecimal(1800);
			final Integer quantidadeParcelas = 18;
			final BigDecimal taxaJurosEsperada = new BigDecimal(0.035, CalculoUtil.MATH_CONTEXT_TAXA_JUROS);

			final BigDecimal taxaJurosCalculada = ContratoEmprestimoService.calculaTaxaJuros(valorContrato,
					quantidadeParcelas);

			Assert.assertEquals(
					String.format("Esperado <%s>, mas calculado <%s>", taxaJurosEsperada, taxaJurosCalculada), 0,
					taxaJurosEsperada.compareTo(taxaJurosCalculada));

			System.out.println("deveCalcularTaxaJurosEsperada - teste 3:\n\t" + taxaJurosCalculada);
		}
		{// * Teste 4:
			final BigDecimal valorContrato = new BigDecimal(500);
			final Integer quantidadeParcelas = 15;
			final BigDecimal taxaJurosEsperada = new BigDecimal(0.023, CalculoUtil.MATH_CONTEXT_TAXA_JUROS);

			final BigDecimal taxaJurosCalculada = ContratoEmprestimoService.calculaTaxaJuros(valorContrato,
					quantidadeParcelas);

			Assert.assertEquals(
					String.format("Esperado <%s>, mas calculado <%s>", taxaJurosEsperada, taxaJurosCalculada), 0,
					taxaJurosEsperada.compareTo(taxaJurosCalculada));

			System.out.println("deveCalcularTaxaJurosEsperada - teste 4:\n\t" + taxaJurosCalculada);
		}
	}

	@Test
	public void deveCalcularValorParcelasEsperado() throws Exception {
		{// * Teste 1:
			final BigDecimal valorContrato = new BigDecimal(1500);
			final Integer quantidadeParcelas = 10;
			final BigDecimal taxaJuros = new BigDecimal(0.03, CalculoUtil.MATH_CONTEXT_TAXA_JUROS);
			final BigDecimal valorParcelasEsperado = new BigDecimal(195, CalculoUtil.MATH_CONTEXT_VALOR_PARCELAS);

			final BigDecimal valorParcelasCalculado = ContratoEmprestimoService.calculaValorParcelas(valorContrato,
					quantidadeParcelas, taxaJuros);

			Assert.assertEquals(
					String.format("Esperado <%s>, mas calculado <%s>", valorParcelasEsperado, valorParcelasCalculado),
					0, valorParcelasEsperado.compareTo(valorParcelasCalculado));

			System.out.println("deveCalcularValorParcelasEsperado - teste 1:\n\t" + valorParcelasCalculado);
		}
		{// * Teste 2:
			final BigDecimal valorContrato = new BigDecimal(1800);
			final Integer quantidadeParcelas = 15;
			final BigDecimal taxaJuros = new BigDecimal(0.035, CalculoUtil.MATH_CONTEXT_TAXA_JUROS);
			final BigDecimal valorParcelasEsperado = new BigDecimal(183, CalculoUtil.MATH_CONTEXT_VALOR_PARCELAS);

			final BigDecimal valorParcelasCalculado = ContratoEmprestimoService.calculaValorParcelas(valorContrato,
					quantidadeParcelas, taxaJuros);

			Assert.assertEquals(
					String.format("Esperado <%s>, mas calculado <%s>", valorParcelasEsperado, valorParcelasCalculado),
					0, valorParcelasEsperado.compareTo(valorParcelasCalculado));

			System.out.println("deveCalcularValorParcelasEsperado - teste 2:\n\t" + valorParcelasCalculado);
		}
		{// * Teste 3:
			final BigDecimal valorContrato = new BigDecimal(800);
			final Integer quantidadeParcelas = 8;
			final BigDecimal taxaJuros = new BigDecimal(0.018, CalculoUtil.MATH_CONTEXT_TAXA_JUROS);
			final BigDecimal valorParcelasEsperado = new BigDecimal(114.4, CalculoUtil.MATH_CONTEXT_VALOR_PARCELAS);

			final BigDecimal valorParcelasCalculado = ContratoEmprestimoService.calculaValorParcelas(valorContrato,
					quantidadeParcelas, taxaJuros);

			Assert.assertEquals(
					String.format("Esperado <%s>, mas calculado <%s>", valorParcelasEsperado, valorParcelasCalculado),
					0, valorParcelasEsperado.compareTo(valorParcelasCalculado));

			System.out.println("deveCalcularValorParcelasEsperado - teste 3:\n\t" + valorParcelasCalculado);
		}
		{// * Teste 4:
			final BigDecimal valorContrato = new BigDecimal(900);
			final Integer quantidadeParcelas = 20;
			final BigDecimal taxaJuros = new BigDecimal(0.023, CalculoUtil.MATH_CONTEXT_TAXA_JUROS);
			final BigDecimal valorParcelasEsperado = new BigDecimal(65.7, CalculoUtil.MATH_CONTEXT_VALOR_PARCELAS);

			final BigDecimal valorParcelasCalculado = ContratoEmprestimoService.calculaValorParcelas(valorContrato,
					quantidadeParcelas, taxaJuros);

			Assert.assertEquals(
					String.format("Esperado <%s>, mas calculado <%s>", valorParcelasEsperado, valorParcelasCalculado),
					0, valorParcelasEsperado.compareTo(valorParcelasCalculado));

			System.out.println("deveCalcularValorParcelasEsperado - teste 4:\n\t" + valorParcelasCalculado);
		}
	}

	@Test
	public void deveCalcularDataValidadeEsperada() throws Exception {
		final Date dataSimulacao = new Date();
		final Date dataValidadeEsperada;
		{// * Prepara a "data de validade esperada":
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataSimulacao);
			calendar.add(Calendar.DAY_OF_YEAR, 30);
			dataValidadeEsperada = calendar.getTime();
		}

		final Date dataValidadeCalculada = ContratoEmprestimoService.calculaDataValidade(dataSimulacao);

		Assert.assertEquals(dataValidadeEsperada, dataValidadeCalculada);

		System.out.println("deveCalcularDataValidadeEsperada:\n\t" + dataValidadeCalculada);
	}

	@Test
	public void deveAceitarDataValidadeDoContratoAindaValida() throws Exception {
		final Date dataValidadeDoContrato;
		{// * Prepara a "data de validade do contrato":
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			// "Data de validade é a atual, mas com algumas minutos de atraso":
			calendar.add(Calendar.MINUTE, -5);
			dataValidadeDoContrato = calendar.getTime();
		}

		final boolean contratoExpirado = ContratoEmprestimoService.isContratoExpirado(dataValidadeDoContrato);

		Assert.assertFalse(contratoExpirado);

		System.out.println("deveAceitarDataValidadeDoContrato:\n\t" + dataValidadeDoContrato);
	}

	@Test
	public void deveConsiderarDataValidadeDoContratoExpirada() throws Exception {
		final Date dataValidadeDoContrato;
		{// * Prepara a "data de validade do contrato":
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, -1); // "Comparação é feita com a data atual"
			dataValidadeDoContrato = calendar.getTime();
		}

		final boolean contratoExpirado = ContratoEmprestimoService.isContratoExpirado(dataValidadeDoContrato);

		Assert.assertTrue(contratoExpirado);

		System.out.println("deveConsiderarDataValidadeDoContratoExpirada:\n\t" + dataValidadeDoContrato);
	}

	@Test(expected = ValidacaoException.class)
	public void deveFalharPorCpfPessoaInvalido() throws Exception {
		final ContratoEmprestimoManager contratoManager = this.initContratoManagerMock();

		final String cpfPessoa = "555.263.170-22"; // * Sebastian Vettel - CPF correto "550.263.170-22"
		final BigDecimal valorContrato = new BigDecimal(1000);
		final Integer quantidadeParcelas = 10;

		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(contratoManager);
		final ContratoEmprestimo contrato = contratoService.getDadosContrato(cpfPessoa, valorContrato,
				quantidadeParcelas);

		Assert.assertNull("Dados foram recuperados mesmo com CPF de pessoa inválido!", contrato);

		System.out.println("deveFalharPorCpfPessoaInvalido:\n\t" + contrato);
	}

	@Test(expected = ValidacaoException.class)
	public void deveFalharPorValorContratoNulo() throws Exception {
		final ContratoEmprestimoManager contratoManager = this.initContratoManagerMock();

		final String cpfPessoa = "550.263.170-22"; // * Sebastian Vettel
		final BigDecimal valorContrato = null; // "sem valor de contrato informado"
		final Integer quantidadeParcelas = 10;

		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(contratoManager);
		final ContratoEmprestimo contrato = contratoService.getDadosContrato(cpfPessoa, valorContrato,
				quantidadeParcelas);

		Assert.assertNull("Dados foram recuperados mesmo com valor de contrato nulo!", contrato);

		System.out.println("deveFalharPorValorContratoNulo:\n\t" + contrato);
	}

	@Test(expected = ValidacaoException.class)
	public void deveFalharPorValorContratoAbaixoMinimo() throws Exception {
		final ContratoEmprestimoManager contratoManager = this.initContratoManagerMock();

		final String cpfPessoa = "550.263.170-22"; // * Sebastian Vettel
		final BigDecimal valorContrato = new BigDecimal(0); // ("valor de contrato mínimo" > 0)
		final Integer quantidadeParcelas = 10;

		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(contratoManager);
		final ContratoEmprestimo contrato = contratoService.getDadosContrato(cpfPessoa, valorContrato,
				quantidadeParcelas);

		Assert.assertNull("Dados foram recuperados mesmo com valor de contrato abaixo do mínimo exigido!", contrato);

		System.out.println("deveFalharPorValorContratoAbaixoMinimo:\n\t" + contrato);
	}

	@Test(expected = ValidacaoException.class)
	public void deveFalharPorQuantidadeParcelasNula() throws Exception {
		final ContratoEmprestimoManager contratoManager = this.initContratoManagerMock();

		final String cpfPessoa = "550.263.170-22"; // * Sebastian Vettel
		final BigDecimal valorContrato = new BigDecimal(900);

		final Integer quantidadeParcelas = null; // "sem quantidade informada"

		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(contratoManager);
		final ContratoEmprestimo contrato = contratoService.getDadosContrato(cpfPessoa, valorContrato,
				quantidadeParcelas);

		Assert.assertNull("Dados foram recuperados mesmo com quantidade de parcelas nula!", contrato);

		System.out.println("deveFalharPorQuantidadeParcelasNula:\n\t" + contrato);
	}

	@Test(expected = ValidacaoException.class)
	public void deveFalharPorQuantidadeParcelasAbaixoDoMinimo() throws Exception {
		final ContratoEmprestimoManager contratoManager = this.initContratoManagerMock();

		final String cpfPessoa = "550.263.170-22"; // * Sebastian Vettel
		final BigDecimal valorContrato = new BigDecimal(900);

		final Integer quantidadeParcelas = 0; // ("mínimo exigido" = 1)

		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(contratoManager);
		final ContratoEmprestimo contrato = contratoService.getDadosContrato(cpfPessoa, valorContrato,
				quantidadeParcelas);

		Assert.assertNull("Dados foram recuperados mesmo com quantidade de parcelas abaixo do mínimo exigido!",
				contrato);

		System.out.println("deveFalharPorQuantidadeParcelasAbaixoDoMinimo:\n\t" + contrato);
	}

	@Test(expected = ValidacaoException.class)
	public void deveFalharPorQuantidadeParcelasAcimaDoMaximo() throws Exception {
		final ContratoEmprestimoManager contratoManager = this.initContratoManagerMock();

		final String cpfPessoa = "550.263.170-22"; // * Sebastian Vettel
		final BigDecimal valorContrato = new BigDecimal(900);

		final Integer quantidadeParcelas = 25; // ("máximo permitido" = 24)

		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(contratoManager);
		final ContratoEmprestimo contrato = contratoService.getDadosContrato(cpfPessoa, valorContrato,
				quantidadeParcelas);

		Assert.assertNull("Dados foram recuperados mesmo com quantidade de parcelas abaixo do máximo permitido!",
				contrato);

		System.out.println("deveFalharPorQuantidadeParcelasAcimaDoMaximo:\n\t" + contrato);
	}
}
