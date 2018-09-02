package br.com.cresol.desafio.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.cresol.desafio.dto.Cliente;
import br.com.cresol.desafio.dto.ContratoEmprestimo;
import br.com.cresol.desafio.dto.ParcelaEmprestimo;
import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;
import br.com.cresol.desafio.repository.Repository;

/**
 * @author evandro
 *
 */
public class EmprestimoService {
	
	private static int sequencial = 0;

	public SimulacaoEmprestimo simular(SimularEmprestimoPayload payload) {
		SimulacaoEmprestimo simulacaoEmprestimo = new SimulacaoEmprestimo();
		String numeroDoContrato = criaNumeroDoContrato();
		simulacaoEmprestimo.setNumeroDoContrato(numeroDoContrato);
		simulacaoEmprestimo.setQuantidadeDeParcelas(payload.getQuantidadeDeParcelas());
		simulacaoEmprestimo.setValorDoContrato(payload.getValorDoContrato());
		simulacaoEmprestimo.setTaxaDeJuros(payload.getValorDoContrato() <= 1000 ? 0.018f : 0.03f);
		
		float valorDaParcela = calculaValorDaParcela(simulacaoEmprestimo);
		simulacaoEmprestimo.setValorDaParcela(valorDaParcela);
		
		Calendar calendar = Calendar.getInstance();
		simulacaoEmprestimo.setData(calendar.getTime());
		calendar.add(Calendar.DATE, 30);
		simulacaoEmprestimo.setDataDeValidade(calendar.getTime());
		simulacaoEmprestimo.setCpfDoCliente(payload.getCpf());
		
		Repository.addSimulacaoEmprestimo(simulacaoEmprestimo);
		return simulacaoEmprestimo;
	}

	private float calculaValorDaParcela(SimulacaoEmprestimo simulacaoEmprestimo) {
		float valorDaParcela = simulacaoEmprestimo.getValorDoContrato()
				*(1+(simulacaoEmprestimo.getQuantidadeDeParcelas()*simulacaoEmprestimo.getTaxaDeJuros()))
				/simulacaoEmprestimo.getQuantidadeDeParcelas();
		return valorDaParcela;
	}

	private String criaNumeroDoContrato() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String numeroDoContrato = simpleDateFormat.format(Calendar.getInstance().getTime()) 
				+ String.format("%06d", ++sequencial);
		return numeroDoContrato;
	}

	public List<SimulacaoEmprestimo> buscarSimulacoes(Cliente cliente) {
		return Repository.findAllSimulacaoEmprestimo(cliente);
	}

	public void removerSimulacao(String numeroDoContrato) throws Exception {
		SimulacaoEmprestimo simulacao = Repository.findSimulacaoEmprestimo(numeroDoContrato);
		if (simulacao == null) {
			throw new Exception("Simulação não encontrada");
		}
		Repository.deleteSimulacao(simulacao);
	}

	public ContratoEmprestimo contratar(String numeroDoContrato) throws Exception {
		ContratoEmprestimo contrato = Repository.findContratoEmprestimo(numeroDoContrato);
		if (contrato != null) { //TODO com mais tempo aqui caberia a utilização de exceções customizadas e loggers
			throw new Exception("Contrato já existe.");
		}
		SimulacaoEmprestimo simulacao = Repository.findSimulacaoEmprestimo(numeroDoContrato);
		if (simulacao.getDataDeValidade().before(Calendar.getInstance().getTime())) {
			throw new Exception("Simulação vencida.");
		}
		contrato = criaContrato(simulacao);
		Repository.addContrato(contrato);
		return contrato;
	}

	private ContratoEmprestimo criaContrato(SimulacaoEmprestimo simulacao) {
		ContratoEmprestimo contrato = new ContratoEmprestimo();
		contrato.setDataDeContratacao(Calendar.getInstance().getTime());
		contrato.setIof(0f); //TODO calculo não especificado
		contrato.setNumeroDoContrato(simulacao.getNumeroDoContrato());
		contrato.setQuantidadeDeParcelas(simulacao.getQuantidadeDeParcelas());
		contrato.setTaxaDeJuros(simulacao.getTaxaDeJuros());
		contrato.setValorDoContrato(simulacao.getValorDoContrato());
		List<ParcelaEmprestimo> parcelas = criaParcelas(simulacao.getQuantidadeDeParcelas(), simulacao.getValorDaParcela(),
				simulacao.getNumeroDoContrato());
		contrato.setParcelas(parcelas);
		return contrato;
	}

	private List<ParcelaEmprestimo> criaParcelas(int quantidadeDeParcelas, float valorDaParcela, String numeroDoContrato) {
		List<ParcelaEmprestimo> parcelas = new ArrayList<ParcelaEmprestimo>();
		for (int numeroDaParcela = 1; numeroDaParcela <= quantidadeDeParcelas; numeroDaParcela++) {
			ParcelaEmprestimo parcela = new ParcelaEmprestimo();
			parcela.setDataDoVencimento(Calendar.getInstance().getTime()); //TODO não especificado
			parcela.setNumeroDaParcela(numeroDaParcela);
			parcela.setValorDaParcela(valorDaParcela);
			parcela.setNumeroDoContrato(numeroDoContrato);
			parcelas.add(parcela);
		}
		return parcelas;
	}

}
