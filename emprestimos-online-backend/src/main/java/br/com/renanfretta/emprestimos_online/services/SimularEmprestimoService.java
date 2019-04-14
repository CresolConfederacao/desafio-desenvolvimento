package br.com.renanfretta.emprestimos_online.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renanfretta.emprestimos_online.bussiness.CalculoEmprestimoBussiness;
import br.com.renanfretta.emprestimos_online.bussiness.ClienteBussiness;
import br.com.renanfretta.emprestimos_online.bussiness.SimularEmprestimoBussiness;
import br.com.renanfretta.emprestimos_online.models.SimularEmprestimo;
import br.com.renanfretta.emprestimos_online.repositories.SimularEmprestimoRepository;
import br.com.renanfretta.emprestimos_online.utils.CalendarUtil;

@Service
public class SimularEmprestimoService {
	
	@Autowired
	private SimularEmprestimoRepository simularEmprestimoRepository;
	
	@Autowired
	private ClienteBussiness clienteBussiness;
	
	@Autowired
	private SimularEmprestimoBussiness simularEmprestimoBussiness;
	
	@Autowired
	private CalculoEmprestimoBussiness calculoEmprestimoBussiness;
	
	public SimularEmprestimo salvar(SimularEmprestimo simularEmprestimo) {
		clienteBussiness.validaCliente(simularEmprestimo.getCliente());
		simularEmprestimoBussiness.validaValorContrato(simularEmprestimo.getValorContrato());
		simularEmprestimoBussiness.validaQuantidadeParcelas(simularEmprestimo.getQuantidadeParcelas());
		
		Date dataSimulacao = new Date();
		Date dataValidadeSimulacao = CalendarUtil.adicionarDiasNaDataInformada(dataSimulacao, 30);
		double taxaJurosEmprestimo = calculoEmprestimoBussiness.getTaxaJurosCalculada(simularEmprestimo.getValorContrato(), simularEmprestimo.getQuantidadeParcelas());
		double valorParcela = calculoEmprestimoBussiness.getValorParcelaCalculada(simularEmprestimo.getValorContrato(), simularEmprestimo.getQuantidadeParcelas(), simularEmprestimo.getTaxaJurosEmprestimo());
		
		simularEmprestimo.setDataSimulacao(dataSimulacao);
		simularEmprestimo.setDataValidadeSimulacao(dataValidadeSimulacao);
		simularEmprestimo.setTaxaJurosEmprestimo(taxaJurosEmprestimo);
		simularEmprestimo.setValorParcela(valorParcela);
		
		return simularEmprestimoRepository.save(simularEmprestimo);
	}

}