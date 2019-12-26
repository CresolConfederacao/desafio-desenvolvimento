package br.com.cresol.desafio.manager;

import java.util.HashMap;
import java.util.Map;

import br.com.cresol.desafio.dto.Pessoa;

/*
 * TODO-REVER - Implementar uma forma de persistencia (exemplo: diretório "standalone/data")
 */
public class PessoaManagerImpl implements PessoaManager {

	private final Map<String, Pessoa> mapPessoas = new HashMap<>();

	@Override
	public Pessoa getPessoa(final String cpf) {
		return this.mapPessoas.get(cpf);
	}

	@Override
	public Pessoa addPessoa(final String nome, final String cpf, final String email) {
		final Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);
		pessoa.setCpf(cpf);
		pessoa.setEmail(email);
		this.mapPessoas.put(cpf, pessoa);
		return pessoa;
	}

}
