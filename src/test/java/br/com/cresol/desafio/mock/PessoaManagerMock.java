package br.com.cresol.desafio.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.cresol.desafio.dto.Pessoa;
import br.com.cresol.desafio.manager.PessoaManager;

public class PessoaManagerMock extends PessoaManager {

	private final List<String> listaPessoasAdicionados = new ArrayList<>();

	@Override
	protected Map<String, Pessoa> recuperaPessoasPersistidas() {
		// * Não implementado no mock de testes.
		return null;
	}

	@Override
	protected void persisteAdicaoDePessoa(final Pessoa pessoa) {
		this.listaPessoasAdicionados.add(pessoa.getCpf());
	}

	public void zeraListaPessoasAdicionados() {
		this.listaPessoasAdicionados.clear();
	}

	public List<String> getListaPessoasAdicionados() {
		return new ArrayList<>(this.listaPessoasAdicionados);
	}
}
