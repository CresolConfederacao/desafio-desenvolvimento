package br.com.cresol.desafio.service;

import br.com.cresol.desafio.dto.ContratoEmprestimo;
import br.com.cresol.desafio.dto.Pessoa;
import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;

/**
 * @author evandro
 *
 */
public class EmprestimoService {

	private final PessoaService pessoaService;
	private final ContratoEmprestimoService contratoService;

	public EmprestimoService(final PessoaService pessoaService, final ContratoEmprestimoService contratoService) {
		this.pessoaService = pessoaService;
		this.contratoService = contratoService;
	}

	public SimulacaoEmprestimo simular(final SimularEmprestimoPayload payload) throws Exception {
		if (payload == null) {
			throw new Exception("Não foram recebidos os dados da requisição.");
		}

		final Pessoa pessoa = this.pessoaService.getDadosPessoa(payload.getNome(), payload.getCpfAsString(),
				payload.getEmail());
		if (pessoa == null) {
			throw new Exception(
					"Pessoa não encontrada para os dados informados na requisição: " + String.valueOf(payload));
		}

		final ContratoEmprestimo contrato = this.contratoService.getDadosContrato(pessoa.getCpf(),
				payload.getValorContrato(), payload.getQuantidadeParcelas());
		if (contrato == null) {
			throw new Exception("Contrato e empréstimo não encontrado para os dados informados na requisição: "
					+ String.valueOf(payload));
		}

		final SimulacaoEmprestimo simulacaoEmprestimo = new SimulacaoEmprestimo();
		simulacaoEmprestimo.setIdContrato(contrato.getIdContrato());
		simulacaoEmprestimo.setDataSimulacao(contrato.getDataSimulacao());
		simulacaoEmprestimo.setDataValidade(contrato.getDataValidade());
		simulacaoEmprestimo.setValorContrato(contrato.getValorContrato());
		simulacaoEmprestimo.setQuantidadeParcelas(contrato.getQuantidadeParcelas());
		simulacaoEmprestimo.setValorParcelas(contrato.getValorParcelas());
		simulacaoEmprestimo.setTaxaJuros(contrato.getTaxaJuros());
		return simulacaoEmprestimo;
	}

}
