package br.com.cresol.desafio.manager;

import br.com.cresol.desafio.dto.Pessoa;

public interface PessoaManager {

	Pessoa getPessoa(final String cpf);
	
	Pessoa addPessoa(final String nome, final String cpf, final String email);
}
