package br.com.heyjanac.desafio.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CLIENTE")
@SequenceGenerator(name = "clienteIdGenerator", sequenceName = "SEQ_TB_CLIENTE", allocationSize = 1)
public class Cliente implements Serializable {

	private static final long serialVersionUID = -8395876040206868895L;

	@Id
	@GeneratedValue(generator = "clienteIdGenerator", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_CLIENTE", nullable = false)
	private Long id;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CLIENTE")
	private List<Emprestimo> emprestimos;

	@Column(name = "NU_CPF", nullable = false)
	private String cpf;

	@Column(name = "DS_NOME", nullable = false)
	private String nome;

	@Column(name = "DS_EMAIL", nullable = false)
	private String email;
	
	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	public Cliente(String cpfCliente, String nomeCliente, String emailCliente) {
		this.cpf = cpfCliente;
		this.nome = nomeCliente;
		this.email = emailCliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long idCliente) {
		this.id = idCliente;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpfCliente) {
		this.cpf = cpfCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nomeCliente) {
		this.nome = nomeCliente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String emailCliente) {
		this.email = emailCliente;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", emprestimos=" + emprestimos + ", cpf=" + cpf + ", nome=" + nome + ", email="
				+ email + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((emprestimos == null) ? 0 : emprestimos.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (emprestimos == null) {
			if (other.emprestimos != null)
				return false;
		} else if (!emprestimos.equals(other.emprestimos))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	

}
