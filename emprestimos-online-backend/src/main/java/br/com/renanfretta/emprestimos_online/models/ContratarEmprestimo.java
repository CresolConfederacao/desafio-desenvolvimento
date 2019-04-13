package br.com.renanfretta.emprestimos_online.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "contratar_emprestimo")
@Data
public class ContratarEmprestimo implements Serializable {

	private static final long serialVersionUID = 1018214626925811892L;

	@Id
	@ManyToOne
	@JoinColumn(name = "numeroContrato")
	private SimularEmprestimo simularEmprestimo;
	
	@NotNull
	private Date dataContratacao;
	
	@NotNull
	private Double valorContrato;
	
	@NotNull
	private Integer quantidadeParcelas;
	
	@NotNull
	private Double taxaJurosEmprestimo;
	
	@NotNull
	private Double iofContrato;

}