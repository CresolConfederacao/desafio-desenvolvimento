package br.com.cresol.desafio.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class TbContrato implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	@Column
	private String numeroContrato;
	
	@Column
	private Float valor;
	
	@Column
	private Integer qtdParcelas;
	
	@Column
	private Float valorParcela;
	
	@Column
	private Float taxaJuros;
	
	@Column
	private Boolean ativo;
	
	@Column
	private Date data;
	
	@Column
	private Date dataValidade;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idCliente", referencedColumnName = "id")
	private TbCliente tbCliente;
	
	
	public TbContrato() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Integer getQtdParcelas() {
		return qtdParcelas;
	}

	public void setQtdParcelas(Integer qtdParcelas) {
		this.qtdParcelas = qtdParcelas;
	}

	public Float getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(Float valorParcela) {
		this.valorParcela = valorParcela;
	}

	public Float getTaxaJuros() {
		return taxaJuros;
	}

	public void setTaxaJuros(Float taxaJuros) {
		this.taxaJuros = taxaJuros;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public TbCliente getTbCliente() {
		return tbCliente;
	}

	public void setTbCliente(TbCliente tbCliente) {
		this.tbCliente = tbCliente;
	}
}