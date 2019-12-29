package br.com.cresol.desafio.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import org.joda.time.DateTimeComparator;

import br.com.cresol.desafio.dto.ContratoEmprestimo;
import br.com.cresol.desafio.manager.ContratoEmprestimoManager;
import br.com.cresol.desafio.util.CalculoUtil;
import br.com.cresol.desafio.util.ValidacaoUtil;

public class ContratoEmprestimoService {

	private static final BigDecimal VALOR_LIMITE_MENOR_TAXA_JUROS = new BigDecimal(1000);
	private static final int QUANTIDADE_PARCELAS_SEM_TAXA_ADICIONAL = 12;

	private static final BigDecimal CONSTANTE_CALCULO_VALOR_PARCELAS = new BigDecimal(1);

	private static final int DIAS_VALIDADE_SIMULACAO_EMPRESTIMO = 30;

	private static final DateTimeComparator dateOnlyComparator = DateTimeComparator.getDateOnlyInstance();

	private final ContratoEmprestimoManager contratoManager;

	public ContratoEmprestimoService(final ContratoEmprestimoManager contratoManager) throws Exception {
		if (Objects.isNull(contratoManager)) {
			throw new Exception("Gerenciador de contratos está nulo.");
		}
		this.contratoManager = contratoManager;
	}

	public ContratoEmprestimo getDadosContrato(final String cpfPessoa, final BigDecimal valorContrato,
			final Integer quantidadeParcelas) throws Exception {
		ValidacaoUtil.validaParametrosParaContrato(cpfPessoa, valorContrato, quantidadeParcelas);
		ContratoEmprestimo contrato = this.contratoManager.getContrato(cpfPessoa, valorContrato, quantidadeParcelas);
		if (contrato == null) {
			contrato = this.criaNovoContrato(cpfPessoa, valorContrato, quantidadeParcelas);
		} else if (isContratoExpirado(contrato.getDataValidade())) {
			if (this.contratoManager.removerContrato(contrato.getIdContrato(), contrato.getCpfPessoa())) {
				contrato = null;
				contrato = this.criaNovoContrato(cpfPessoa, valorContrato, quantidadeParcelas);
			} else {
				throw new Exception("Não foi possível remover o contrato expirado: " + String.valueOf(contrato));
			}
		}
		return contrato;
	}

	private ContratoEmprestimo criaNovoContrato(final String cpfPessoa, final BigDecimal valorContrato,
			final Integer quantidadeParcelas) {
		ContratoEmprestimo contrato;
		final BigDecimal taxaJuros = calculaTaxaJuros(valorContrato, quantidadeParcelas);
		final BigDecimal valorParcelas = calculaValorParcelas(valorContrato, quantidadeParcelas, taxaJuros);
		final Date dataSimulacao = new Date();
		final Date dataValidade = calculaDataValidade(dataSimulacao);
		contrato = this.contratoManager.addContrato(cpfPessoa, valorContrato, quantidadeParcelas, valorParcelas,
				taxaJuros, dataSimulacao, dataValidade);
		return contrato;
	}

	public static BigDecimal calculaTaxaJuros(final BigDecimal valorContrato, final Integer quantidadeParcelas) {
		final BigDecimal taxaJuros;
		if (valorContrato.compareTo(VALOR_LIMITE_MENOR_TAXA_JUROS) <= 0) {
			taxaJuros = new BigDecimal(0.018, CalculoUtil.MATH_CONTEXT_TAXA_JUROS);
		} else {
			taxaJuros = new BigDecimal(0.03, CalculoUtil.MATH_CONTEXT_TAXA_JUROS);
		}
		if (quantidadeParcelas > QUANTIDADE_PARCELAS_SEM_TAXA_ADICIONAL) {
			return taxaJuros.add(new BigDecimal(0.005, CalculoUtil.MATH_CONTEXT_TAXA_JUROS),
					CalculoUtil.MATH_CONTEXT_TAXA_JUROS);
		}
		return taxaJuros;
	}

	/**
	 * Efetua o cálculo do valor de cada parcela, tomando por base a seguinte
	 * fórmula:<br>
	 * ► valorContrato * (1 + (quantidadeParcelas * taxaJuros)) / quantidadeParcelas
	 * 
	 * @param valorContrato      {@link BigDecimal} : valor do emprestimo, informado
	 *                           na requisição.
	 * @param quantidadeParcelas {@link Integer} : quantidade de parcelas desejadas,
	 *                           informada na requisição.
	 * @param taxaJuros          {@link BigDecimal} : taxa de juros a sera aplicada,
	 *                           calculada pelo método
	 *                           {@link #calculaTaxaJuros(BigDecimal, Integer)}.
	 * @return {@link BigDecimal} : valor de cada parcela.
	 */
	public static BigDecimal calculaValorParcelas(final BigDecimal valorContrato, final Integer quantidadeParcelas,
			final BigDecimal taxaJuros) {
		final BigDecimal quantidadeParcelasBigDec = new BigDecimal(quantidadeParcelas);
		final BigDecimal parte1MultiplicaQuantidadeTaxa = quantidadeParcelasBigDec.multiply(taxaJuros,
				CalculoUtil.MATH_CONTEXT_VALOR_PARCELAS);
		final BigDecimal parte2SomaConstante = parte1MultiplicaQuantidadeTaxa.add(CONSTANTE_CALCULO_VALOR_PARCELAS,
				CalculoUtil.MATH_CONTEXT_VALOR_PARCELAS);
		final BigDecimal parte3MultiplicaComValorContrato = valorContrato.multiply(parte2SomaConstante,
				CalculoUtil.MATH_CONTEXT_VALOR_PARCELAS);
		final BigDecimal valorParcelas = parte3MultiplicaComValorContrato.divide(quantidadeParcelasBigDec,
				CalculoUtil.MATH_CONTEXT_VALOR_PARCELAS);
		return valorParcelas;
	}

	public static Date calculaDataValidade(final Date dataSimulacao) {
		final Calendar calendarValidade = Calendar.getInstance();
		calendarValidade.setTime(dataSimulacao);
		calendarValidade.add(Calendar.DAY_OF_YEAR, DIAS_VALIDADE_SIMULACAO_EMPRESTIMO);
		return calendarValidade.getTime();
	}

	public static boolean isContratoExpirado(final Date dataValidadeContrato) {
		final int comparacaoDataValidadeComAtual = dateOnlyComparator.compare(dataValidadeContrato, new Date());
		return (comparacaoDataValidadeComAtual < 0);
	}
}
