package br.com.cresol.desafio.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.cresol.desafio.dto.Pessoa;
import br.com.cresol.desafio.manager.PessoaManager;
import br.com.cresol.desafio.mock.PessoaManagerMock;
import br.com.cresol.desafio.util.ValidacaoException;

public class TestPessoaService {

	private PessoaManager initPessoaManagerMock() {
		final PessoaManagerMock mock = new PessoaManagerMock();
		mock.addPessoa("Cleiton Janke", "008.674.449-67", "cleiton.janke@gmail.com");
		mock.addPessoa("Charles Leclerc", "575.825.218-20", "charles.leclerc@gmail.com");
		mock.addPessoa("Valteri Bottas", "936.256.079-80", "valteri.bottas@gmail.com");
		mock.zeraListaPessoasAdicionados();
		return (PessoaManager) mock;
	}

	private List<String> getNovasPessoasNoMock(final PessoaManager pessoaManager) {
		return ((PessoaManagerMock) pessoaManager).getListaPessoasAdicionados();
	}

	@Test
	public void deveRecuperarDadosPessoaExistente() throws Exception {
		final PessoaManager pessoaManager = this.initPessoaManagerMock();

		final String nome = "Cleiton Janke";
		final String cpf = "008.674.449-67";
		final String email = "cleiton.janke@gmail.com";

		final PessoaService pessoaService = new PessoaService(pessoaManager);
		final Pessoa pessoa = pessoaService.getDadosPessoa(nome, cpf, email);

		Assert.assertNotNull("Pessoa não encontrada!", pessoa);
		Assert.assertEquals("Diferença na quantidade de pessoas inseridas:", 0,
				this.getNovasPessoasNoMock(pessoaManager).size());
		Assert.assertEquals(nome, pessoa.getNome());
		Assert.assertEquals(cpf, pessoa.getCpf());
		Assert.assertEquals(email, pessoa.getEmail());
	}

	@Test
	public void deveRecuperarDadosInserindoNovaPessoa() throws Exception {
		final PessoaManager pessoaManager = this.initPessoaManagerMock();

		final String nome = "Lewis Hamilton";
		final String cpf = "429.142.204-05";
		final String email = "lewis.hamilton@gmail.com";

		final PessoaService pessoaService = new PessoaService(pessoaManager);
		final Pessoa pessoa = pessoaService.getDadosPessoa(nome, cpf, email);

		Assert.assertNotNull("Pessoa não encontrada!", pessoa);
		Assert.assertEquals(nome, pessoa.getNome());
		Assert.assertEquals(cpf, pessoa.getCpf());
		Assert.assertEquals(email, pessoa.getEmail());

		final List<String> novasPessoasNoMock = this.getNovasPessoasNoMock(pessoaManager);
		Assert.assertEquals("Diferença na quantidade de pessoas inseridas:", 1, novasPessoasNoMock.size());
		Assert.assertEquals("A pessoa inserida não é a esperada: ", cpf, novasPessoasNoMock.get(0));
	}

	@Test(expected = ValidacaoException.class)
	public void deveFalharPorCPFInvalidoParaPessoa() throws Exception {
		final PessoaManager pessoaManager = this.initPessoaManagerMock();

		final String nome = "Sebastian Vettel";
		final String cpf = "555.263.170-22"; // * CPF correto: "550.263.170-22"
		final String email = "sebastian.vettel@gmail.com";

		final PessoaService pessoaService = new PessoaService(pessoaManager);
		final Pessoa pessoa = pessoaService.getDadosPessoa(nome, cpf, email);

		Assert.assertNull("Dados foram recuperados mesmo com e-mail inválido!", pessoa);
	}

	@Test(expected = ValidacaoException.class)
	public void deveFalharPorEmailInvalidoParaPessoa() throws Exception {
		final PessoaManager pessoaManager = this.initPessoaManagerMock();

		final String nome = "Cleiton Janke";
		final String cpf = "008.674.449-67";
		final String email = "cleiton.janke@gmail.";

		final PessoaService pessoaService = new PessoaService(pessoaManager);
		final Pessoa pessoa = pessoaService.getDadosPessoa(nome, cpf, email);

		Assert.assertNull("Dados foram recuperados mesmo com e-mail inválido!", pessoa);
	}
}
