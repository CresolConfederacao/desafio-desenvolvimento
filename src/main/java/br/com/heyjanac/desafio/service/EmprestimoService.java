package br.com.heyjanac.desafio.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import br.com.heyjanac.desafio.enums.StatusEmprestimoEnum;
import br.com.heyjanac.desafio.exception.RegrasExcpetion;
import br.com.heyjanac.desafio.exception.ResourceNotFoundException;
import br.com.heyjanac.desafio.model.Cliente;
import br.com.heyjanac.desafio.model.Emprestimo;
import br.com.heyjanac.desafio.model.Parcela;
import br.com.heyjanac.desafio.repository.IClienteRepository;
import br.com.heyjanac.desafio.repository.IEmprestimoRepository;

@Service
public class EmprestimoService {

	private static final Logger log = LoggerFactory.getLogger(EmprestimoService.class);

	@Autowired
	private IEmprestimoRepository emprestimoRepository;

	@Autowired
	private IClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;

	public List<Emprestimo> listarTodos() {
		log.info("Buscando todos os emprestimos.");
		return emprestimoRepository.findAll();
	}

	public Emprestimo buscarPorNumeroContrato(Long numeroContrato) {
		log.info("Buscando emprestimo por Numero Contrato {}", numeroContrato);
		return emprestimoRepository.findByNumeroContrato(numeroContrato);
	}

	@Transient
	public Emprestimo salvarSimulacao(Emprestimo emprestimo) throws ResourceNotFoundException {
		try {
			log.info("Simulando emprestimo: {}", emprestimo);

			Cliente cliente = clienteRepository.findFirstByCpf(emprestimo.getCliente().getCpf());
			if (cliente == null) {
				cliente = clienteService.salvar(emprestimo.getCliente());
			}
			emprestimo.setCliente(cliente);

			Long ultimoRegistroContrato = emprestimoRepository.findByUltimoNumeroContrato();

			Long numeroContratoGerado = this.gerarNumeroContrato(ultimoRegistroContrato);
			emprestimo.setNumeroContrato(numeroContratoGerado);

			BigDecimal taxaJuros = this.gerarTaxaJuros(emprestimo.getValorContrato(),
					emprestimo.getQuantidadeParcela());
			emprestimo.setValorTaxaJuros(taxaJuros);

			BigDecimal valorContratoAtualizado = gerarValorParcela(emprestimo.getValorContrato(),
					emprestimo.getQuantidadeParcela(), taxaJuros);
			emprestimo.setValorParcela(valorContratoAtualizado);

			List<Parcela> parcelas = this.gerarParcelas(valorContratoAtualizado, emprestimo.getQuantidadeParcela());
			emprestimo.setParcelas(parcelas);

			emprestimo.setValorIofContrato(BigDecimal.ZERO);

			emprestimo.setNumeroStatus(StatusEmprestimoEnum.SIMULADO);

			emprestimoRepository.saveAndFlush(emprestimo);

			log.info("Emprestimo Simulado: {}", emprestimo);

		} catch (Exception e) {
			throw new ResourceNotFoundException("Erro [salvarSimulacao] >> " + e.getMessage());
		}

		return emprestimo;
	}

	@Transient
	public Emprestimo contratar(Long numeroContrato) throws ResourceNotFoundException {
		log.info("Contratar emprestimo do n. contrato: {}", numeroContrato);
		Emprestimo emprestimo = null;

		try {

			emprestimo = buscarSimulacaoPorNumeroContrato(numeroContrato);

			if (LocalDateTime.now().isAfter(emprestimo.getDataValidadeSimulacao())) {
				throw new RegrasExcpetion("Simulação vencida.");
			}

			emprestimo.setDataContratacao(LocalDateTime.now());

			emprestimo.setNumeroStatus(StatusEmprestimoEnum.EFETIVADO);

			emprestimoRepository.save(emprestimo);

			log.info("Emprestimo Contratado: {}", emprestimo);

		} catch (Exception e) {
			throw new ResourceNotFoundException("Erro [contratar] >> " + e.getMessage());
		}

		return emprestimo;
	}
	
	public void deletar(Long id) {
		log.info("Deletando emprestimo {}", id);
		Optional<Emprestimo> optEmprestimo = emprestimoRepository.findById(id);

		if (optEmprestimo.isPresent()) {
			this.emprestimoRepository.deleteById(id);
		} else {
			log.info("Emprestimo {} inexistente", id);
		}
	}

	public Emprestimo buscarSimulacaoPorNumeroContrato(Long numeroContrato) throws ResourceNotFoundException {
		Emprestimo simulado = null;

		simulado = emprestimoRepository.findByNumeroContratoSimulado(numeroContrato);
		if (simulado == null) {
			throw new ResourceNotFoundException("Nao ha simulacao para o contrato " + numeroContrato + ".");
		}
		return simulado;
	}

	public List<Parcela> gerarParcelas(BigDecimal valorContratoAtualizado, int quantidadeParcela) {
		Parcela parcela = new Parcela();
		List<Parcela> parcelas = new ArrayList<>();
		BigDecimal valorParcela = valorContratoAtualizado.divide(
				new BigDecimal(quantidadeParcela = quantidadeParcela < 1 ? 1 : quantidadeParcela),
				BigDecimal.ROUND_HALF_UP);

		LocalDateTime dataVencimento = LocalDateTime.now().minusDays(5);

		for (int i = 0; i < quantidadeParcela; i++) {
			parcela = new Parcela();
			parcela.setNumeroParcela(i + 1);
			parcela.setValorParcela(valorParcela);
			parcela.setDataVencimento(dataVencimento);
			parcelas.add(parcela);
			dataVencimento = parcela.getDataVencimento().minusDays(30);
		}

		return parcelas;
	}

	/**
	 * O numero do contrato deve ser composto pela sequencia: AAAAMMDD+000000 onde
	 * 00000 sequencial de 6 dígitos
	 * 
	 * @param ultimoNumeroContrato
	 * @return
	 */
	public Long gerarNumeroContrato(Long ultimoNumeroContrato) {

		String iniciais = "";
		Integer numero = 0;

		for (int i = 0; i < 6; i++) {
			numero = new Integer((int) (Math.random() * 10));
			iniciais += numero.toString();
		}

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDateTime dateTime = LocalDateTime.now();
		String foramttedString = dateTime.format(format);

		Long contratoGerado = Long.parseLong(foramttedString.concat(iniciais));
		return contratoGerado;

	}

	/**
	 * Sendo valor contrato menor ou igual a R$1.000,00 atribuir 1,8% como
	 * percentual de taxa de juros, se não: atribuir 3%. Sendo a quantidade de
	 * parcelas maior que 12 parcelas, adicionar 0,5% a taxa de juros.
	 * 
	 * @throws RegrasExcpetion
	 */
	public BigDecimal gerarTaxaJuros(BigDecimal valorContrato, Integer quantidadeParcela) throws RegrasExcpetion {
		BigDecimal taxaJuros = BigDecimal.ZERO;
		try {
			taxaJuros = new BigDecimal(valorContrato.compareTo(new BigDecimal(1000)) == 1 ? "3.0" : "1.8");
			taxaJuros = quantidadeParcela > 12 ? taxaJuros.add(new BigDecimal("0.5")) : taxaJuros;
		} catch (Exception e) {
			throw new RegrasExcpetion("Erro [geraTaxaJuros] >> " + e.getMessage());
		}
		return taxaJuros;
	}

	/**
	 * 
	 * @param valorContrato
	 * @param quantidadeParcela
	 * @return
	 * @throws RegrasExcpetion
	 */
	public BigDecimal gerarValorParcela(BigDecimal valorContrato, Integer quantidadeParcela, BigDecimal taxaJuros)
			throws RegrasExcpetion {
		BigDecimal calculado = BigDecimal.ZERO;
		BigDecimal calculo1 = BigDecimal.ZERO;
		BigDecimal calculo2 = BigDecimal.ZERO;

		try {

			calculo1 = (new BigDecimal(quantidadeParcela).multiply(taxaJuros));
			calculo2 = valorContrato.multiply(BigDecimal.ONE.add(calculo1));

			calculado = calculo2.divide(
					new BigDecimal(quantidadeParcela = quantidadeParcela < 1 ? 1 : quantidadeParcela),
					BigDecimal.ROUND_HALF_UP);

		} catch (Exception e) {
			throw new RegrasExcpetion("Erro [gerarValorParcela] >> " + e.getMessage());
		}

		return calculado;

	}

}
