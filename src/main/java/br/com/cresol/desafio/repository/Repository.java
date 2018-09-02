package br.com.cresol.desafio.repository;

import java.util.ArrayList;
import java.util.List;

import br.com.cresol.desafio.dto.Cliente;
import br.com.cresol.desafio.dto.ContratoEmprestimo;
import br.com.cresol.desafio.dto.SimulacaoEmprestimo;

public class Repository {
	
	private static List<Cliente> clientes = new ArrayList<Cliente>();
	private static List<SimulacaoEmprestimo> simulacaoEmprestimos = new ArrayList<SimulacaoEmprestimo>();
	private static List<ContratoEmprestimo> contratoEmprestimos = new ArrayList<ContratoEmprestimo>();

	public static Cliente getCliente(String cpf) {
		for (Cliente cliente : clientes) {
			if (cliente.getCpf().equals(cpf)) {
				return cliente;
			}
		}
		return null;
	}
	
	public static void addCliente(Cliente cliente) {
		clientes.add(cliente);
	}
	
	public static void addSimulacaoEmprestimo(SimulacaoEmprestimo simulacaoEmprestimo) {
		simulacaoEmprestimos.add(simulacaoEmprestimo);
	}
	
	public static List<SimulacaoEmprestimo> findAllSimulacaoEmprestimo(){
		return simulacaoEmprestimos;
	}
	
	public static List<SimulacaoEmprestimo> findAllSimulacaoEmprestimo(Cliente cliente){
		List<SimulacaoEmprestimo> simulacoes = new ArrayList<SimulacaoEmprestimo>();
		for (SimulacaoEmprestimo simulacao : simulacaoEmprestimos) {
			if (simulacao.getCpfDoCliente().equals(cliente.getCpf())) {
				simulacoes.add(simulacao);
			}
		}
		return simulacoes;
	}
	
	public static SimulacaoEmprestimo findSimulacaoEmprestimo(String numeroDoContrato){
		for (SimulacaoEmprestimo simulacao : simulacaoEmprestimos) {
			if (simulacao.getNumeroDoContrato().equals(numeroDoContrato)) {
				return simulacao;
			}
		}
		return null;
	}

	public static void deleteSimulacao(SimulacaoEmprestimo simulacao) {
		simulacaoEmprestimos.remove(simulacao);
	}
	
	public static void addContrato(ContratoEmprestimo contrato) {
		contratoEmprestimos.add(contrato);
	}
	
	public static ContratoEmprestimo findContratoEmprestimo(String numeroDoContrato){
		for (ContratoEmprestimo contrato : contratoEmprestimos) {
			if (contrato.getNumeroDoContrato().equals(numeroDoContrato)) {
				return contrato;
			}
		}
		return null;
	}
}
