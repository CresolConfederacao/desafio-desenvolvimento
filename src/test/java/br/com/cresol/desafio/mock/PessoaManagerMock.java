package br.com.cresol.desafio.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.cresol.desafio.dto.Pessoa;
import br.com.cresol.desafio.manager.PessoaManager;

public class PessoaManagerMock implements PessoaManager {

	private final Map<String, Pessoa> mapPessoas = new HashMap<>();
	
	private final List<String> listaPessoasAdicionados = new ArrayList<>();
	
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
		this.listaPessoasAdicionados.add(cpf);
		return pessoa;
	}

	public void zeraListaPessoasAdicionados() {
		this.listaPessoasAdicionados.clear();
	}
	
	public List<String> getListaPessoasAdicionados() {
		return new ArrayList<>(this.listaPessoasAdicionados);
	}
}
