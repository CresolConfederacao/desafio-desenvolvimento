package br.com.heyjanac.desafio.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PARCELA")
@SequenceGenerator(name = "parcelaIdGenerator", sequenceName = "SEQ_TB_PARCELA", allocationSize = 1)
public class Parcela implements Serializable {

	private static final long serialVersionUID = 8154668663021553795L;

	@Id
	@GeneratedValue(generator = "parcelaIdGenerator", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_PARCELA", nullable = false)
	private Long idParcela;

	@Column(name = "NU_PARCELA", nullable = false)
	private Integer numeroParcela;

	@Column(name = "DT_VENCIMENTO", nullable = false)
	private LocalDateTime dataVencimento;

	@Column(name = "VL_PARCELA", nullable = false)
	private BigDecimal valorParcela;

	public Long getIdParcela() {
		return idParcela;
	}

	public void setIdParcela(Long idParcela) {
		this.idParcela = idParcela;
	}

	public Integer getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(Integer numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

	public LocalDateTime getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDateTime dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

}
