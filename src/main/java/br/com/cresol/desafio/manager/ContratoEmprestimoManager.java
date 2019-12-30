package br.com.cresol.desafio.manager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.cresol.desafio.dto.ContratoEmprestimo;

public abstract class ContratoEmprestimoManager {

	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
	private static final DecimalFormat sequenceFormatter = new DecimalFormat("000000");

	private final List<ContratoEmprestimo> listaContratos = new ArrayList<>();

	private static int getCodDataIdContrato(final Date dataSimulacao) {
		final String dataFormatada = dateFormatter.format(dataSimulacao);
		return Integer.parseInt(dataFormatada);
	}

	private static long getIdContrato(final int codDataIdContrato, final int codSequenciaIdContrato) {
		final StringBuilder idContratoBuilder = new StringBuilder();
		idContratoBuilder.append(codDataIdContrato);
		idContratoBuilder.append(sequenceFormatter.format(codSequenciaIdContrato));
		final long idContrato = Long.parseLong(idContratoBuilder.toString());
		return idContrato;
	}

	public ContratoEmprestimoManager() {
		final List<ContratoEmprestimo> dadosPersistidos = this.recuperaContratosPersistidos();
		if (dadosPersistidos != null && !dadosPersistidos.isEmpty()) {
			this.listaContratos.addAll(dadosPersistidos);
		}
	}

	public ContratoEmprestimo getContrato(final String cpfPessoa, final BigDecimal valorContrato,
			final Integer quantidadeParcelas) {
		final Optional<ContratoEmprestimo> contratoEncontrado = this.listaContratos.stream()
				.filter(contrato -> contrato.getCpfPessoa().equals(cpfPessoa)
						&& contrato.getValorContrato().equals(valorContrato)
						&& contrato.getQuantidadeParcelas().equals(quantidadeParcelas))
				.findFirst();
		return (contratoEncontrado.isPresent() ? contratoEncontrado.get() : null);
	}

	public ContratoEmprestimo addContrato(final String cpfPessoa, final BigDecimal valorContrato,
			final Integer quantidadeParcelas, final BigDecimal valorParcelas, final BigDecimal taxaJuros,
			final Date dataSimulacao, final Date dataValidade) {
		final int codDataIdContrato = getCodDataIdContrato(dataSimulacao);
		final int codSequenciaIdContrato = (this.getUltimoCodSequenciaIdContratoPorCodData(codDataIdContrato) + 1);
		final long idContrato = getIdContrato(codDataIdContrato, codSequenciaIdContrato);

		final ContratoEmprestimo contrato = new ContratoEmprestimo(idContrato, codDataIdContrato,
				codSequenciaIdContrato, dataSimulacao);
		contrato.setCpfPessoa(cpfPessoa);
		contrato.setValorContrato(valorContrato);
		contrato.setQuantidadeParcelas(quantidadeParcelas);
		contrato.setValorParcelas(valorParcelas);
		contrato.setTaxaJuros(taxaJuros);
		contrato.setDataValidade(dataValidade);

		this.listaContratos.add(contrato);
		this.persisteAdicaoDeContrato(contrato);
		return contrato;
	}

	public boolean removerContrato(final long idContrato, final String cpfPessoa) {
		boolean contratoRemovido = false;
		final List<ContratoEmprestimo> contratosParaRemover = this.listaContratos.stream()
				.filter(contrato -> contrato.getIdContrato() == idContrato && contrato.getCpfPessoa().equals(cpfPessoa))
				.collect(Collectors.toList());
		if (contratosParaRemover != null && contratosParaRemover.size() == 1) {
			final ContratoEmprestimo contrato = contratosParaRemover.get(0);
			contratoRemovido = this.listaContratos.remove(contrato);
			if (contratoRemovido) {
				this.persisteRemocaoDeContrato(contrato);
			}
		}
		return contratoRemovido;
	}

	protected abstract List<ContratoEmprestimo> recuperaContratosPersistidos();

	protected abstract void persisteAdicaoDeContrato(final ContratoEmprestimo contrato);

	protected abstract void persisteRemocaoDeContrato(final ContratoEmprestimo contrato);

	private int getUltimoCodSequenciaIdContratoPorCodData(final int codDataIdContrato) {
		final Optional<Integer> ultimoCodSequencia = this.listaContratos.stream()
				.filter(contrato -> contrato.getCodDataIdContrato() == codDataIdContrato)
				.map(contrato -> contrato.getCodSequenciaIdContrato()).max(Integer::compare);
		return (ultimoCodSequencia.isPresent() ? ultimoCodSequencia.get() : 0);
	}
}
