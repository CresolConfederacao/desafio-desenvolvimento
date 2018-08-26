package br.com.cresol.desafio.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author evandro
 *
 */
public class SimularEmprestimoPayload extends Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal valorContrato;
	private Integer quantidadeParcelas;

	public SimularEmprestimoPayload() {
		super();
	}

	public BigDecimal getValorContrato() {
		return valorContrato;
	}

	public void setValorContrato(BigDecimal valorContrato) {
		this.valorContrato = valorContrato;
	}

	public Integer getQuantidadeParcelas() {
		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(Integer quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}

}
