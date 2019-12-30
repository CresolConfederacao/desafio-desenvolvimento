package br.com.cresol.desafio.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.cresol.desafio.manager.ContratoEmprestimoManager;
import br.com.cresol.desafio.manager.PessoaManager;
import br.com.cresol.desafio.mock.ContratoEmprestimoManagerMock;
import br.com.cresol.desafio.mock.PessoaManagerMock;

public final class MockUtil {

	public static PessoaManager initPessoaManagerMock() {
		return initPessoaManagerMock(true);
	}

	public static PessoaManager initPessoaManagerMock(final boolean efetuaPreCarga) {
		final PessoaManagerMock mock = new PessoaManagerMock();
		if (efetuaPreCarga) {
			mock.addPessoa("Cleiton Janke", "008.674.449-67", "cleiton.janke@gmail.com");
			mock.addPessoa("Charles Leclerc", "575.825.218-20", "charles.leclerc@gmail.com");
			mock.addPessoa("Valteri Bottas", "936.256.079-80", "valteri.bottas@gmail.com");
			mock.zerarListasAuxiliaresDoMock();
		}
		return (PessoaManager) mock;
	}

	public static ContratoEmprestimoManager initContratoManagerMock() {
		return initContratoManagerMock(true);
	}

	public static List<String> getNovasPessoasNoMock(final PessoaManager pessoaManager) {
		return ((PessoaManagerMock) pessoaManager).getListaPessoasAdicionadas();
	}

	public static List<String> getPessoasAtualizadasNoMock(final PessoaManager pessoaManager) {
		return ((PessoaManagerMock) pessoaManager).getListaPessoasAtualizadas();
	}

	public static ContratoEmprestimoManager initContratoManagerMock(final boolean efetuaPreCarga) {
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

	public static List<Long> getNovosContratosNoMock(final ContratoEmprestimoManager contratoManager) {
		return ((ContratoEmprestimoManagerMock) contratoManager).getListaContratosAdicionados();
	}

	public static List<Long> getContratosRemovidosDoMock(final ContratoEmprestimoManager contratoManager) {
		return ((ContratoEmprestimoManagerMock) contratoManager).getListaContratosRemovidos();
	}
}
