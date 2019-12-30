package br.com.cresol.desafio.service;

import java.util.Objects;

import br.com.cresol.desafio.dto.Pessoa;
import br.com.cresol.desafio.manager.PessoaManager;
import br.com.cresol.desafio.util.ValidacaoUtil;

/**
 * Serviço interno responsável por obter e registrar os dados das pessoas
 * envolvidas nas requisições de Simulação de Empréstimos.
 * 
 * @author Cleiton Janke
 */
public class PessoaService {

	private final PessoaManager pessoaManager;

	public PessoaService(final PessoaManager pessoaManager) throws Exception {
		if (Objects.isNull(pessoaManager)) {
			throw new Exception("Gerenciador de pessoas está nulo.");
		}
		this.pessoaManager = pessoaManager;
	}

	/**
	 * Obtém os dados da pessoa envolvida na requisição do empréstimo.<br>
	 * ► Se alguma pessoa registrada for encontrada pelo parâmetro 'CPF', e os
	 * demais parâmetros forem os mesmos dos dados deste registros, então apenas
	 * retorna os dados já registrados.<br>
	 * ► Se houver alteração nos demais parâmetros, então atualiza os dados e
	 * retorna o registro.<br>
	 * ► Se não houver pessoa previamente registrada, cadastra os novos dados.<br>
	 * 
	 * @param nome  {@link String} : nome da pessoa.
	 * @param cpf   {@link String} : CPF da pessoa.
	 * @param email {@link String} : e-mail da pessoa.
	 * @return {@link Pessoa} : objeto com os dados registrados da pessoa.
	 * @throws Exception : exceção ao validar os parâmetros ou obter os dados.
	 */
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
