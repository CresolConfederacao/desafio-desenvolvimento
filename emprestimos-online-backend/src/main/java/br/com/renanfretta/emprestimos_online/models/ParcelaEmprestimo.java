package br.com.renanfretta.emprestimos_online.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "parcela_emprestimo")
@Data
public class ParcelaEmprestimo implements Serializable {

	private static final long serialVersionUID = 2914004795142382526L;

	@EmbeddedId
	private ParcelaEmprestimoId id;
	
	@OneToOne
	@JoinColumn(name = "numero_contrato", insertable=false, updatable=false)
	private ContratarEmprestimo contratarEmprestimo;
	
	@NotNull
	private Double valorParcela;
	
	@NotNull
	private Date dataVencimento;

}