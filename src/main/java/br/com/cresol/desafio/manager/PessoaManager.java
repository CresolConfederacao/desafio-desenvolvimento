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
		final Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);
		pessoa.setCpf(cpf);
		pessoa.setEmail(email);
		this.mapPessoas.put(cpf, pessoa);
		this.persisteAdicaoDePessoa(pessoa);
		return pessoa;
	}

	protected abstract Map<String, Pessoa> recuperaPessoasPersistidas();

	protected abstract void persisteAdicaoDePessoa(final Pessoa pessoa);
}
