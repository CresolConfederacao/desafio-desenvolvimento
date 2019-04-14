package br.com.renanfretta.emprestimos_online.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renanfretta.emprestimos_online.bussiness.TaxaJurosBussiness;
import br.com.renanfretta.emprestimos_online.models.SimularEmprestimo;
import br.com.renanfretta.emprestimos_online.repositories.SimularEmprestimoRepository;
import br.com.renanfretta.emprestimos_online.utils.CalendarUtil;

@Service
public class SimularEmprestimoService {
	
	@Autowired
	private SimularEmprestimoRepository simularEmprestimoRepository;
	
	public SimularEmprestimo salvar(SimularEmprestimo simularEmprestimo) {
		// RN: Validar a quantidade de parcelas náo pode ser maior que 24
		// RN: Validar a quantidade de parcelas maior ou igua a 1
		// RN: Validar valor maior que zero
		
		Date dataSimulacao = new Date();
		Date dataValidadeSimulacao = CalendarUtil.adicionarDiasNaDataInformada(dataSimulacao, 30);
		double taxaJurosEmprestimo = TaxaJurosBussiness.getTaxaJurosCalculada(simularEmprestimo.getValorContrato(), simularEmprestimo.getQuantidadeParcelas());
		double valorParcela = TaxaJurosBussiness.getValorParcelaCalculada(simularEmprestimo.getValorContrato(), simularEmprestimo.getQuantidadeParcelas(), simularEmprestimo.getTaxaJurosEmprestimo());
		
		simularEmprestimo.setDataSimulacao(dataSimulacao);
		simularEmprestimo.setDataValidadeSimulacao(dataValidadeSimulacao);
		simularEmprestimo.setTaxaJurosEmprestimo(taxaJurosEmprestimo);
		simularEmprestimo.setValorParcela(valorParcela);
		
		return simularEmprestimoRepository.save(simularEmprestimo);
	}
	
	
//	R1 – Numero do Contrato
//	O numero do contrato deve ser composto pela sequencia:
//	AAAAMMDD+000000 onde 00000 sequencial de 6 dígitos


}