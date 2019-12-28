package br.com.cresol.desafio.service;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import br.com.cresol.desafio.dto.Pessoa;
import br.com.cresol.desafio.manager.PessoaManager;
import br.com.cresol.desafio.util.ValidacaoException;
import br.com.cresol.desafio.util.ValidacaoUtil;

public class PessoaService {

	private final PessoaManager pessoaManager;

	public PessoaService(final PessoaManager pessoaManager) throws Exception {
		if (Objects.isNull(pessoaManager)) {
			throw new Exception("Gerenciador de pessoas não pode ser nulo!");
		}
		this.pessoaManager = pessoaManager;
	}

	public Pessoa getDadosPessoa(final String nome, final String cpf, final String email) throws Exception {
		this.validaParametros(nome, cpf, email);

		Pessoa pessoa = this.pessoaManager.getPessoa(cpf);
		if (pessoa == null) {
			pessoa = this.pessoaManager.addPessoa(nome, cpf, email);
		} else if (!pessoa.isMesmosDados(nome, cpf, email)) {
			pessoa = null;
			pessoa = this.pessoaManager.atualizarDadosPessoa(cpf, nome, email);
		}

		return pessoa;
	}

	private void validaParametros(final String nome, final String cpf, final String email) throws ValidacaoException {
		if (StringUtils.isBlank(nome)) {
			throw new ValidacaoException("Necessário informar um nome!");
		}
		if (!ValidacaoUtil.isCpfValido(cpf)) {
			throw new ValidacaoException("O CPF informado é inválido: " + String.valueOf(cpf));
		}
		if (!ValidacaoUtil.isEmailValido(email)) {
			throw new ValidacaoException("O e-mail informado é inválido: " + String.valueOf(email));
		}
	}

}
