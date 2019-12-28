package br.com.cresol.desafio.manager.impl;

import java.util.Map;

import br.com.cresol.desafio.dto.Pessoa;
import br.com.cresol.desafio.manager.PessoaManager;

public class PessoaManagerImpl extends PessoaManager {

	private static final PessoaManagerImpl instance = new PessoaManagerImpl();

	public static PessoaManager getInstance() {
		return instance;
	}

	private PessoaManagerImpl() {
		super();
	}

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
