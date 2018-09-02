package br.com.cresol.desafio.dto;

/**
 * @author evandro
 *
 */
public class SimularEmprestimoPayload {

	private String nome;
	private String cpf;
	private String email;
	private float valorDoContrato;
	private int quantidadeDeParcelas;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public float getValorDoContrato() {
		return valorDoContrato;
	}
	public void setValorDoContrato(float valorDoContrato) {
		this.valorDoContrato = valorDoContrato;
	}
	public int getQuantidadeDeParcelas() {
		return quantidadeDeParcelas;
	}
	public void setQuantidadeDeParcelas(int quantidadeDeParcelas) {
		this.quantidadeDeParcelas = quantidadeDeParcelas;
	}
	
}
