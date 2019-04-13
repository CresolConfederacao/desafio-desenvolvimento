package br.com.renanfretta.emprestimos_online.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "cliente")
@Data
public class Cliente implements Serializable {

	private static final long serialVersionUID = 6519931402625484981L;

	@Id
	private Integer cpf;

	@NotNull
	private String nome;
	
	@NotNull
	private String email;

}