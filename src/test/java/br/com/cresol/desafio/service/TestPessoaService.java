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
		mock.zerarListasAuxiliaresDoMock();
		return (PessoaManager) mock;
	}

	private List<String> getNovasPessoasNoMock(final PessoaManager pessoaManager) {
		return ((PessoaManagerMock) pessoaManager).getListaPessoasAdicionadas();
	}

	private List<String> getPessoasAtualizadasNoMock(final PessoaManager pessoaManager) {
		return ((PessoaManagerMock) pessoaManager).getListaPessoasAtualizadas();
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

	@Test
	public void deveRecuperarDadosAtualizadosDePessoaComMesmoCpfMasNomeDiferente() throws Exception {
		final PessoaManager pessoaManager = this.initPessoaManagerMock();

		final String nome = "Cleiton Edgar Janke Duarte";
		final String cpf = "008.674.449-67";
		final String email = "cleiton.janke@gmail.com";

		final String nomePessoaExistente;
		final String cpfPessoaExistente;
		final String emailPessoaExistente;
		{
			final Pessoa pessoaExistente = pessoaManager.getPessoa(cpf);
			nomePessoaExistente = pessoaExistente.getNome();
			cpfPessoaExistente = pessoaExistente.getCpf();
			emailPessoaExistente = pessoaExistente.getEmail();
		}

		final PessoaService pessoaService = new PessoaService(pessoaManager);
		final Pessoa pessoa = pessoaService.getDadosPessoa(nome, cpf, email);

		Assert.assertNotNull("Pessoa não encontrada!", pessoa);
		Assert.assertEquals("Diferença na quantidade de pessoas inseridas:", 0,
				this.getNovasPessoasNoMock(pessoaManager).size());
		Assert.assertEquals(nome, pessoa.getNome());
		Assert.assertEquals(cpf, pessoa.getCpf());
		Assert.assertEquals(email, pessoa.getEmail());

		final List<String> pessoasAtualizadasNoMock = this.getPessoasAtualizadasNoMock(pessoaManager);
		Assert.assertEquals("Diferença na quantidade de pessoas com dados atualizados:", 1,
				pessoasAtualizadasNoMock.size());
		Assert.assertEquals("A pessoa atualizada não é a esperada: ", cpf, pessoasAtualizadasNoMock.get(0));

		Assert.assertEquals(cpfPessoaExistente, pessoa.getCpf());
		Assert.assertEquals(emailPessoaExistente, pessoa.getEmail());
		Assert.assertNotEquals(nomePessoaExistente, pessoa.getNome());

		System.out.println("deveRecuperarDadosAtualizadosDePessoaComMesmoCpfMasNomeDiferente:\n--> Existente:\n\t"
				+ nomePessoaExistente + "\n--> Atualizado:\n\t" + pessoa.getNome());
	}

	@Test
	public void deveRecuperarDadosAtualizadosDePessoaComMesmoCpfMasEmailDiferente() throws Exception {
		final PessoaManager pessoaManager = this.initPessoaManagerMock();

		final String nome = "Valteri Bottas";
		final String cpf = "936.256.079-80";
		final String email = "bottas@mercedes.com";

		final String nomePessoaExistente;
		final String cpfPessoaExistente;
		final String emailPessoaExistente;
		{
			final Pessoa pessoaExistente = pessoaManager.getPessoa(cpf);
			nomePessoaExistente = pessoaExistente.getNome();
			cpfPessoaExistente = pessoaExistente.getCpf();
			emailPessoaExistente = pessoaExistente.getEmail();
		}

		final PessoaService pessoaService = new PessoaService(pessoaManager);
		final Pessoa pessoa = pessoaService.getDadosPessoa(nome, cpf, email);

		Assert.assertNotNull("Pessoa não encontrada!", pessoa);
		Assert.assertEquals("Diferença na quantidade de pessoas inseridas:", 0,
				this.getNovasPessoasNoMock(pessoaManager).size());
		Assert.assertEquals(nome, pessoa.getNome());
		Assert.assertEquals(cpf, pessoa.getCpf());
		Assert.assertEquals(email, pessoa.getEmail());

		final List<String> pessoasAtualizadasNoMock = this.getPessoasAtualizadasNoMock(pessoaManager);
		Assert.assertEquals("Diferença na quantidade de pessoas com dados atualizados:", 1,
				pessoasAtualizadasNoMock.size());
		Assert.assertEquals("A pessoa atualizada não é a esperada: ", cpf, pessoasAtualizadasNoMock.get(0));

		Assert.assertEquals(nomePessoaExistente, pessoa.getNome());
		Assert.assertEquals(cpfPessoaExistente, pessoa.getCpf());
		Assert.assertNotEquals(emailPessoaExistente, pessoa.getEmail());

		System.out.println("deveRecuperarDadosAtualizadosDePessoaComMesmoCpfMasEmailDiferente:\n--> Existente:\n\t"
				+ emailPessoaExistente + "\n--> Atualizado:\n\t" + pessoa.getEmail());
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
