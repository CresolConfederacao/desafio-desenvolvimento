package br.com.cresol.desafio.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;

import br.com.cresol.desafio.dto.Cliente;
import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;
import br.com.cresol.desafio.exception.RegraNegocioException;

/**
 * @author evandro
 *
 */
public class EmprestimoService {

	private static int nmSerial = 1;
	private static SimpleDateFormat dt = new SimpleDateFormat("yyyyyMMdd");
	private HashSet<Cliente> colectionCliente = new HashSet<>();
	private static int nmMaxParcelas = 24;

	public SimulacaoEmprestimo simular(SimularEmprestimoPayload payload) throws Exception {

		// Garante que não duplique Cliente
		colectionCliente.add(new Cliente(payload.getNome(), payload.getCPF(), payload.getEmail()));

		SimulacaoEmprestimo simulacaoEmprestimo = getRN05(payload);

		return simulacaoEmprestimo;
	}

	/**
	 * R1 – Numero do Contrato. O numero do contrato deve ser composto pela
	 * sequencia: AAAAMMDD+000000 onde 00000 sequencial de 6 dígitos
	 * 
	 * @param dtHoje
	 * @return numeroContrato
	 */

	private Integer getRN01(Date dtHoje) throws RegraNegocioException {

		// String NmContrato = dt.format(dtHoje) + String.format("%06d",
		// getSerial()); Integer não comporta
		String NmContrato = dt.format(dtHoje) + getSerial();
		return Integer.parseInt(NmContrato);
	}

	/**
	 * R2 – Valor da Parcela. Calcular o valor da parcela conforme: Taxa de juros (R3)
	 * valorContato*(1+(quantideParcelas*taxajuros))/quantidadeParcelas
	 * 
	 * @param valorContrato
	 * @param qtdParcelas
	 * @param taxajuros
	 * @return valorParcela
	 */
	private BigDecimal getRN02(BigDecimal valorContrato, Integer qtdParcelas, BigDecimal taxajuros) {

		taxajuros = taxajuros.divide(new BigDecimal("100"));

		BigDecimal valorParcela = new BigDecimal(qtdParcelas).multiply(taxajuros);
		valorParcela = valorParcela.add(new BigDecimal("1"));
		valorParcela = valorParcela.divide(new BigDecimal(qtdParcelas), MathContext.DECIMAL128);
		valorParcela = valorParcela.multiply(valorContrato);

		return valorParcela;
	}

	/**
	 * R3 – Taxa de Juros. Sendo valor contrato menor ou igual a R$1.000,00
	 * atribuir 1,8% como percentual de taxa de juros, se não: atribuir 3%.
	 * Sendo a quantidade de parcelas maior que 12 parcelas, adicionar 0,5% a
	 * taxa de juros
	 * 
	 * @param valorContrato
	 * @param qtdParcelas
	 * @return
	 */

	private BigDecimal getRN03(BigDecimal valorContrato, Integer qtdParcelas) {
		BigDecimal taxajuros;

		if (valorContrato.compareTo(new BigDecimal("1000")) >= 0) {
			taxajuros = new BigDecimal("3");
		} else {
			taxajuros = new BigDecimal("1.8");
		}

		if (qtdParcelas > 12) {
			taxajuros = taxajuros.add(new BigDecimal("0.5"));
		}

		return taxajuros;
	}

	/**
	 * R4 – Quantidade Máxima de Parcelas. Quantidade máxima de parcelas: 24
	 * 
	 * @param qtdParcelas
	 * @return qtdParcelas
	 * @throws Exception
	 */
	private Integer getRN04(Integer qtdParcelas) throws RegraNegocioException {
		if (qtdParcelas > nmMaxParcelas) {
			throw new RegraNegocioException("Numero de parcelas excedido");
		}
		return qtdParcelas;
	}

	/**
	 * R5 – Retorno dados Simulação Contrato Empréstimo.
	 * 
	 * @param payload
	 * @return simulacaoEmprestimo
	 * @throws Exception
	 */
	private SimulacaoEmprestimo getRN05(SimularEmprestimoPayload payload) throws RegraNegocioException {
		Date dtHoje = new Date(System.currentTimeMillis());
		SimulacaoEmprestimo simulacaoEmprestimo = new SimulacaoEmprestimo();
		simulacaoEmprestimo = new SimulacaoEmprestimo();
		simulacaoEmprestimo.setNumeroContrato(getRN01(dtHoje));
		simulacaoEmprestimo.setDataSimulacao(dtHoje);
		simulacaoEmprestimo.setDataValidadeSimulacao(getDateVencimento(dtHoje));
		simulacaoEmprestimo.setQuantidadeParcelas(getRN04(payload.getQuantidadeParcelas()));
		simulacaoEmprestimo
				.setTaxaJurosEmprestimo(getRN03(payload.getValorContrato(), payload.getQuantidadeParcelas()));
		simulacaoEmprestimo.setValorParcela(getRN02(payload.getValorContrato(), payload.getQuantidadeParcelas(),
				simulacaoEmprestimo.getTaxaJurosEmprestimo()));
		simulacaoEmprestimo.setValorContrato(payload.getValorContrato());

		return simulacaoEmprestimo;
	}

	public Date getDateVencimento(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.DATE, +30);
		return new Date(c.getTimeInMillis());
	}

	public static int getSerial() throws RegraNegocioException {
		if (nmSerial > 999999) { // 6 digitos limite
			throw new RegraNegocioException("Numero Serial excedeu limite.");
		}
		return nmSerial++;
	}

}
