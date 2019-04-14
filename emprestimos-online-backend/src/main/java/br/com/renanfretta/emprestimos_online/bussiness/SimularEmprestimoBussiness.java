package br.com.renanfretta.emprestimos_online.bussiness;

import org.springframework.stereotype.Service;

@Service
public class SimularEmprestimoBussiness {
	
	public void validaQuantidadeParcelas(Integer quantidadeParcelas) {
		if (quantidadeParcelas != null && quantidadeParcelas > 0 && quantidadeParcelas <= 24)
			return;
		throw new IllegalArgumentException("A quantidade de parcelas deve ser um número maior que zero e menor que 24.");
	}
			
	public void validaValorContrato(Double valorContrato) {
		if (valorContrato != null && valorContrato > 0)
			return;
		throw new IllegalArgumentException("O valor do contrato deve ser um número maior que zero.");
	}

}