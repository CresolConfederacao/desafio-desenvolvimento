package br.com.heyjanac.desafio.enums;

public enum StatusEmprestimoEnum {
	
	SIMULADO(1, "Simulado"), EFETIVADO(2, "Efetivado"), CANCELADO(3, "Cancelado");

	private int codigo;
	private String descricao;

	StatusEmprestimoEnum(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	StatusEmprestimoEnum(int codigo) {
		this.codigo = codigo;
	}
	
	StatusEmprestimoEnum(String descricao) {
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
}
