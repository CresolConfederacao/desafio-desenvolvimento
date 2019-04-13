package br.com.renanfretta.emprestimos_online.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "simular_emprestimo")
@Data
public class SimularEmprestimo implements Serializable {

	private static final long serialVersionUID = -1338556234061013006L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer numeroContrato;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cpf_cliente")
	private Cliente cliente;

	@NotNull
	private Date dataSimulacao;
	
	@NotNull
	private Date dataValidadeSimulacao;
	
	@NotNull
	private Double valorContrato;
	
	@NotNull
	private Integer quantidadeParcelas;
	
	@NotNull
	private Double valorParcela;
	
	@NotNull
	private Double taxaJurosEmprestimo;

}