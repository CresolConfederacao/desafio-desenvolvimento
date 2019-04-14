package br.com.renanfretta.emprestimos_online.bussiness;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

@Service
public class CalculoEmprestimoBussiness {
	
	private final static double TAXA_JUROS_VALOR_CONTRATO_MENOR_OU_IGUAL_A_1000 = 1.8;
	private final static double TAXA_JUROS_VALOR_CONTRATO_MAIOR_QUE_1000 = 3;
	private final static double TAXA_JUROS_ADICIONAL_QUANTIDADE_PARCELAS_MAIOR_QUE_12 = 0.5;
	
	public double getTaxaJurosCalculada(double valorContrato, int quantidadeParcelas) {
		double percentualTaxaJurosAdicional = 0;

		if (quantidadeParcelas > 12)
			percentualTaxaJurosAdicional = TAXA_JUROS_ADICIONAL_QUANTIDADE_PARCELAS_MAIOR_QUE_12;
		
		if (valorContrato <= 1000)
			return TAXA_JUROS_VALOR_CONTRATO_MENOR_OU_IGUAL_A_1000 + percentualTaxaJurosAdicional;

		return TAXA_JUROS_VALOR_CONTRATO_MAIOR_QUE_1000 + percentualTaxaJurosAdicional;
	}
	
	public double getValorParcelaCalculada(double valorContrato, int quantidadeParcelas, double taxaJuros) {
		BigDecimal bigDecimal = new BigDecimal(valorContrato * (1 + (quantidadeParcelas * (taxaJuros / 100))) / quantidadeParcelas);
		return bigDecimal.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
	}
	
}