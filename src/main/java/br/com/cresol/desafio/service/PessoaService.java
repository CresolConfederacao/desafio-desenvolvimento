package br.com.cresol.desafio.service;

import java.util.Objects;

import br.com.cresol.desafio.dto.Pessoa;
import br.com.cresol.desafio.manager.PessoaManager;
import br.com.cresol.desafio.util.ValidacaoUtil;

public class PessoaService {

	private final PessoaManager pessoaManager;

	public PessoaService(final PessoaManager pessoaManager) throws Exception {
		if (Objects.isNull(pessoaManager)) {
			throw new Exception("Gerenciador de pessoas está nulo.");
		}
		this.pessoaManager = pessoaManager;
	}

	public Pessoa getDadosPessoa(final String nome, final String cpf, final String email) throws Exception {
		ValidacaoUtil.validaParametrosParaPessoas(nome, cpf, email);

		Pessoa pessoa = this.pessoaManager.getPessoa(cpf);
		if (pessoa == null) {
			pessoa = this.pessoaManager.addPessoa(nome, cpf, email);
		} else if (!pessoa.isMesmosDados(nome, cpf, email)) {
			pessoa = null;
			pessoa = this.pessoaManager.atualizarDadosPessoa(cpf, nome, email);
		}

		return pessoa;
	}
}
