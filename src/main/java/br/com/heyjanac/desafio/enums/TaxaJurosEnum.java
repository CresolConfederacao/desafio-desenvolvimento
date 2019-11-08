package br.com.heyjanac.desafio.enums;

public enum TaxaJurosEnum {

	UM(1, "1%"), DOIS(2, "2%"), TRES(3, "3%"), QUATRO(4, "4%");

	private int valor;
	private String descricao;

	TaxaJurosEnum(int valorTaxaJuros, String descricaoTaxaJuros) {
		this.valor = valorTaxaJuros;
		this.descricao = descricaoTaxaJuros;
	}

	TaxaJurosEnum(int valorTaxaJuros) {
		this.valor = valorTaxaJuros;
	}
	
	TaxaJurosEnum(String descricaoTaxaJuros) {
		this.descricao = descricaoTaxaJuros;
	}

	public int getValor() {
		return valor;
	}

	public String getDescricao() {
		return descricao;
	}

}
