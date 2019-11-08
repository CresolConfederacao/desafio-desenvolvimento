package br.com.heyjanac.desafio.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.heyjanac.desafio.enums.StatusEmprestimoEnum;
import br.com.heyjanac.desafio.exception.RegrasExcpetion;

@Entity
@Table(name = "TB_EMPRESTIMO")
@SequenceGenerator(name = "emprestimoIdGenerator", sequenceName = "SEQ_TB_CLIENTE", allocationSize = 1)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Emprestimo implements Serializable {

	private static final long serialVersionUID = 6618569239781269554L;

	private static final Integer QTDE_DIAS_VALIDADE_SIMULACAO = 30; // Item 2

	private static final Integer QTDE_MAXIMA_PARCELA = 24; // R4


	private LocalDateTime dataCorrente;

	public Emprestimo() {
		dataCorrente = LocalDateTime.now();
		this.dataSimulacao = dataCorrente;
		this.dataValidadeSimulacao = this.dataSimulacao.plusDays(QTDE_DIAS_VALIDADE_SIMULACAO);
	}

	@Id
	@GeneratedValue(generator = "emprestimoIdGenerator", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_EMPRESTIMO", nullable = false)
	private Long idEmprestimo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CLIENTE")
	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn(name = "ID_EMPRESTIMO")
	private List<Parcela> parcelas;

	@Column(name = "DT_SIMULACAO", nullable = false)
	private LocalDateTime dataSimulacao;

	@Column(name = "DT_VALIDADE_SIMULACAO", nullable = false)
	private LocalDateTime dataValidadeSimulacao;

	@Column(name = "NU_CONTRATO", nullable = false)
	private Long numeroContrato;

	@Column(name = "VL_CONTRATO", nullable = false)
	private BigDecimal valorContrato;

	@Column(name = "NU_QTDE_PARCELA", nullable = false)
	private Integer quantidadeParcela;

	@Column(name = "VL_PARCELA", nullable = false)
	private BigDecimal valorParcela;

	@Column(name = "VL_TAXA_JUROS")
	private BigDecimal valorTaxaJuros;

	@Column(name = "DT_CONTRATACAO", nullable = true)
	private LocalDateTime dataContratacao;

	@Column(name = "VL_IOF_CONTRATO")
	private BigDecimal valorIofContrato;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "NU_STATUS", nullable = false, length = 2)
	private StatusEmprestimoEnum numeroStatus;

	public Long getIdEmprestimo() {
		return idEmprestimo;
	}

	public void setIdEmprestimo(Long idEmprestimo) {
		this.idEmprestimo = idEmprestimo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Parcela> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<Parcela> parcelas) {
		this.parcelas = parcelas;
	}

	public LocalDateTime getDataSimulacao() {
		return dataSimulacao;
	}

	public void setDataSimulacao(LocalDateTime dataSimulacao) {
		this.dataSimulacao = dataSimulacao;
	}

	public LocalDateTime getDataValidadeSimulacao() {
		return dataValidadeSimulacao;
	}

	public void setDataValidadeSimulacao(LocalDateTime dataValidadeSimulacao) {
		this.dataValidadeSimulacao = dataValidadeSimulacao;
	}

	public Long getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(Long numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public BigDecimal getValorContrato() {
		return valorContrato;
	}

	public void setValorContrato(BigDecimal valorContrato) {
		this.valorContrato = valorContrato;
	}

	public Integer getQuantidadeParcela() {
		return quantidadeParcela;
	}

	public void setQuantidadeParcela(Integer quantidadeParcela) throws RegrasExcpetion {
		this.quantidadeParcela = quantidadeParcela;
		this.validarQuantidadeMaximaDeParcelas();
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

	public BigDecimal getValorTaxaJuros() {
		return valorTaxaJuros;
	}

	public void setValorTaxaJuros(BigDecimal valorTaxaJuros) {
		this.valorTaxaJuros = valorTaxaJuros;
	}

	public LocalDateTime getDataContratacao() {
		return dataContratacao;
	}

	public void setDataContratacao(LocalDateTime dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

	public BigDecimal getValorIofContrato() {
		return valorIofContrato;
	}

	public void setValorIofContrato(BigDecimal valorIofContrato) {
		this.valorIofContrato = valorIofContrato;
	}

	public StatusEmprestimoEnum getNumeroStatus() {
		return numeroStatus;
	}

	public void setNumeroStatus(StatusEmprestimoEnum numeroStatus) {
		this.numeroStatus = numeroStatus;
	}

	/**
	 * Informacoes Adicionais: A simulacao do emprestimo possuem um periodo de
	 * validade de 30 dias. Apos este periodo nao sera possivel efetivar a
	 * contratacao.
	 * 
	 * @return
	 */
	public Boolean isSimulacaoValida() {
		Boolean retorno = true;
		LocalDateTime dataVerificada = LocalDateTime.now().minusDays(QTDE_DIAS_VALIDADE_SIMULACAO);
		if (dataVerificada.isAfter(this.dataValidadeSimulacao)) {
			retorno = false;
		}
		return retorno;
	}

	/**
	 * R4 : Quantidade Maxima de Parcelas = 24.
	 * 
	 * @throws RegrasExcpetion
	 */
	public void validarQuantidadeMaximaDeParcelas() throws RegrasExcpetion {
		if (this.quantidadeParcela != null && this.quantidadeParcela > QTDE_MAXIMA_PARCELA)
			throw new RegrasExcpetion("A quantidade maxima permitida de parcelas sao " + QTDE_MAXIMA_PARCELA.byteValue()
					+ " e o informado foi: " + this.quantidadeParcela + ".");
	}

}
