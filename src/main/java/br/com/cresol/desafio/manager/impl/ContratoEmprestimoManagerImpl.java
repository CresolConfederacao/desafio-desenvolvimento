package br.com.cresol.desafio.manager.impl;

import java.util.List;

import br.com.cresol.desafio.dto.ContratoEmprestimo;
import br.com.cresol.desafio.manager.ContratoEmprestimoManager;

public class ContratoEmprestimoManagerImpl extends ContratoEmprestimoManager {

	@Override
	protected List<ContratoEmprestimo> recuperaContratosPersistidos() {
		// TODO-REVER - Implementar uma persistencia (ex.: diretório "standalone/data")
		return null;
	}

	@Override
	protected void persisteAdicaoDeContrato(ContratoEmprestimo contrato) {
		// TODO-REVER - Implementar uma persistencia (ex.: diretório "standalone/data")
	}

	@Override
	protected void persisteRemocaoDeContrato(ContratoEmprestimo contrato) {
		// TODO-REVER - Implementar uma persistencia (ex.: diretório "standalone/data")
	}

}
