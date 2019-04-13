package br.com.renanfretta.emprestimos_online.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "cliente")
@Data
public class Cliente {

	@Id
	private Integer cpf;

	@NotNull
	private String nome;
	
	@NotNull
	private String email;

}