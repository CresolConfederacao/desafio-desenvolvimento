package br.com.cresol.desafio.manager.impl;

import java.util.Map;

import br.com.cresol.desafio.dto.Pessoa;
import br.com.cresol.desafio.manager.PessoaManager;

public class PessoaManagerImpl extends PessoaManager {

	@Override
	protected Map<String, Pessoa> recuperaPessoasPersistidas() {
		// TODO-REVER - Implementar uma persistencia (ex.: diretório "standalone/data")
		return null;
	}

	@Override
	protected void persisteAdicaoDePessoa(Pessoa pessoa) {
		// TODO-REVER - Implementar uma persistencia (ex.: diretório "standalone/data")
	}

	@Override
	protected void persisteAtualizacaoDePessoa(Pessoa pessoa) {
		// TODO-REVER - Implementar uma persistencia (ex.: diretório "standalone/data")
	}

}
