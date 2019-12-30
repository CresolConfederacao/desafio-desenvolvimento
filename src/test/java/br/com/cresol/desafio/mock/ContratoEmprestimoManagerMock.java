package br.com.cresol.desafio.mock;

import java.util.ArrayList;
import java.util.List;

import br.com.cresol.desafio.dto.ContratoEmprestimo;
import br.com.cresol.desafio.manager.ContratoEmprestimoManager;

public class ContratoEmprestimoManagerMock extends ContratoEmprestimoManager {

	private final List<Long> listaContratosAdicionados = new ArrayList<>();
	private final List<Long> listaContratosRemovidos = new ArrayList<>();

	@Override
	protected List<ContratoEmprestimo> recuperaContratosPersistidos() {
		// * Não implementado no mock de testes.
		return null;
	}

	@Override
	protected void persisteAdicaoDeContrato(final ContratoEmprestimo contrato) {
		this.listaContratosAdicionados.add(contrato.getIdContrato());
	}

	@Override
	protected void persisteRemocaoDeContrato(final ContratoEmprestimo contrato) {
		this.listaContratosRemovidos.add(contrato.getIdContrato());
	}

	public void zerarListasAuxiliaresDoMock() {
		this.listaContratosAdicionados.clear();
		this.listaContratosRemovidos.clear();
	}

	public List<Long> getListaContratosAdicionados() {
		return new ArrayList<>(this.listaContratosAdicionados);
	}

	public List<Long> getListaContratosRemovidos() {
		return new ArrayList<>(this.listaContratosRemovidos);
	}
}
