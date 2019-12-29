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

/**
 * Serviço interno responsável por obter e registrar os dados dos contratos
 * envolvidos nas requisições de Simulação de Empréstimos.
 * 
 * @author Cleiton Janke
 */
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

	/**
	 * Obtém os dados de um contrato de empréstimo a partir dos parãmetros
	 * informados na requisição.<br>
	 * ► Se os parâmetros forem os mesmo de algum contrato já registrado e ainda
	 * válido (não expirado), então apenas retorna os dados, sem efetuar o
	 * recálculo.<br>
	 * ► Se os parâmetros forem os mesmo, mas o contrato registrado já esteja
	 * expirado, então exclui o contrato registrado e recalcula os valores.<br>
	 * ► Se não houver contrato previamente registrado, efetua os cálculos e
	 * registra os dados.<br>
	 * 
	 * @param cpfPessoa          {@link String} : CPF da pessoa envolvida na
	 *                           requisição do empréstimo.
	 * @param valorContrato      {@link BigDecimal} : valor total do contrato de
	 *                           empréstimo.
	 * @param quantidadeParcelas {@link Integer} : quantidade de parcelas da
	 *                           requisição de empréstimo.
	 * @return {@link ContratoEmprestimo} : objeto com os dados do contrato.
	 * @throws Exception : exceção ao validar os parâmetros ou obter os dados.
	 */
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

	/**
	 * Efetua o cálculo da taxa de juros a ser usada para calcular o valor das
	 * parcelas, tomando por base as seguintes regras:<br>
	 * ► Se o VALOR_CONTRATO for menor ou igual do que
	 * {@link #VALOR_LIMITE_MENOR_TAXA_JUROS}, então a menor taxa de juros, de
	 * "1,8%" é aplicada.<br>
	 * ► Caso contrário a taxa de juros será de "3%".<br>
	 * ► Caso a quantidade de parcelas ultrapasse o
	 * {@link #QUANTIDADE_PARCELAS_SEM_TAXA_ADICIONAL}, então será adicionado uma
	 * taxa de "0,5%" à taxa de juros já definida.<br>
	 * 
	 * @param valorContrato      {@link BigDecimal} : valor do emprestimo, informado
	 *                           na requisição.
	 * @param quantidadeParcelas {@link Integer} : quantidade de parcelas desejadas,
	 *                           informada na requisição.
	 * @param taxaJuros          {@link BigDecimal} : taxa de juros calculada.
	 */
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

	/**
	 * Efetua o cálculo para a data de validade do contrato, isso a partir da data
	 * de simulação informada.<br>
	 * ► A data de validade será de {@link #DIAS_VALIDADE_SIMULACAO_EMPRESTIMO}
	 * depois da data de simulação.
	 * 
	 * @param dataSimulacao {@link Date} : data de simulação do contrato.
	 * @return {@link Date} : data de validade calculada.
	 */
	public static Date calculaDataValidade(final Date dataSimulacao) {
		final Calendar calendarValidade = Calendar.getInstance();
		calendarValidade.setTime(dataSimulacao);
		calendarValidade.add(Calendar.DAY_OF_YEAR, DIAS_VALIDADE_SIMULACAO_EMPRESTIMO);
		return calendarValidade.getTime();
	}

	/**
	 * Verifica se o contrato já está expirado, comparando a data de validade do
	 * contrato informada com a data atual.<br>
	 * ► Se a data de validade for menor do que a data atual o contrato é
	 * considerado expirado.
	 * 
	 * @param dataValidadeContrato {@link Date} : data de validade do contrato.
	 * @return boolean : retorna <b>TRUE</b> caso o contrato esteja expirado.
	 */
	public static boolean isContratoExpirado(final Date dataValidadeContrato) {
		final int comparacaoDataValidadeComAtual = dateOnlyComparator.compare(dataValidadeContrato, new Date());
		return (comparacaoDataValidadeComAtual < 0);
	}
}
