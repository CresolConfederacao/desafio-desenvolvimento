package br.com.renanfretta.emprestimos_online.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class ParcelaEmprestimoId implements Serializable {
	
	private static final long serialVersionUID = -4514849817977935361L;

	@Column(name = "numero_contrato")
	private Long numeroContrato;
	
	@Column(name = "numero_da_parcela")
	private Integer numeroDaParcela;
	
}