package br.com.cresol.desafio.manager;

import java.util.HashMap;
import java.util.Map;

import br.com.cresol.desafio.dto.Pessoa;

public abstract class PessoaManager {

	private final Map<String, Pessoa> mapPessoas = new HashMap<>();

	public PessoaManager() {
		final Map<String, Pessoa> dadosPersistidos = this.recuperaPessoasPersistidas();
		if (dadosPersistidos != null && !dadosPersistidos.isEmpty()) {
			this.mapPessoas.putAll(dadosPersistidos);
		}
	}

	public Pessoa getPessoa(final String cpf) {
		return this.mapPessoas.get(cpf);
	}

	public Pessoa addPessoa(final String nome, final String cpf, final String email) {
		final Pessoa pessoa = new Pessoa(nome, cpf, email);
		this.mapPessoas.put(cpf, pessoa);
		this.persisteAdicaoDePessoa(pessoa);
		return pessoa;
	}

	public Pessoa atualizarDadosPessoa(final String cpf, final String nome, final String email) {
		final Pessoa pessoa = this.getPessoa(cpf);
		pessoa.atualizarDados(nome, email);
		this.persisteAtualizacaoDePessoa(pessoa);
		return pessoa;
	}

	protected abstract Map<String, Pessoa> recuperaPessoasPersistidas();

	protected abstract void persisteAdicaoDePessoa(final Pessoa pessoa);

	protected abstract void persisteAtualizacaoDePessoa(final Pessoa pessoa);
}
