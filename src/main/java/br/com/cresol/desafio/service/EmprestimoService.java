package br.com.cresol.desafio.service;

import java.math.BigDecimal;
import java.rmi.ServerException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import br.com.cresol.desafio.dto.Cliente;
import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;
import br.com.cresol.desafio.util.Util;

/**
 * @author evandro
 *
 */
public class EmprestimoService {
	
	static List<Cliente> lsCliente;
	static List<SimulacaoEmprestimo> lsSimulacaoEmprestimo;
	static Long sequenceCliente = 1L;
	static Long sequenceEmprestimo = 1L;
	
	
	static {
		
		lsSimulacaoEmprestimo = new ArrayList<SimulacaoEmprestimo>();
		lsCliente = new ArrayList<Cliente>();
		
		Cliente cliente1 = new Cliente();
		cliente1.setId(sequenceCliente++);
		cliente1.setCpf(81712077074L);
		cliente1.setNome("Arnaldo Coelho");
		cliente1.setEmail("arnaldo.coelho@gmail.com");
		lsCliente.add(cliente1);
		
		Cliente cliente2 = new Cliente();
		cliente2.setId(sequenceCliente++);
		cliente2.setCpf(54344884078L);
		cliente2.setNome("Silvio Santos");
		cliente2.setEmail("silvio.santos@gmail.com");
		lsCliente.add(cliente2);
		
		Cliente cliente3 = new Cliente();
		cliente3.setId(sequenceCliente++);
		cliente3.setCpf(66010697018L);
		cliente3.setNome("kira Grace");
		cliente3.setEmail("kira.grace@gmail.com");
		lsCliente.add(cliente3);
		
    }
	
	
	
	

	public SimulacaoEmprestimo simular(SimularEmprestimoPayload payload) throws Exception {
		SimulacaoEmprestimo simulacaoEmprestimo = null;
		
		try {
			validacoes(payload);
			
			simulacaoEmprestimo = new SimulacaoEmprestimo();
			if(isClienteCarregado(payload)) {
				carregaCliente(simulacaoEmprestimo, payload);
			} else {
				inserirUsuario(payload);
			}
			
			
			simulacaoEmprestimo.setId(sequenceEmprestimo++);
			Long numeroContrato = getNumeroContrato(simulacaoEmprestimo);
			BigDecimal valorParcela = getValorParcela(payload);
			String dataAtual = getDataAtual();
			String dataSimulacao = getDataSimulacao();
			BigDecimal taxaJurosEmpretimo = getTaxaJuros(payload);
			
			simulacaoEmprestimo.setNumeroContrato(numeroContrato);
			simulacaoEmprestimo.setValorParcela(valorParcela);
			simulacaoEmprestimo.setDataSimulacao(dataAtual);
			simulacaoEmprestimo.setDataValidadeSimulacao(dataSimulacao);
			simulacaoEmprestimo.setValorContrato(payload.getValorContrato());
			simulacaoEmprestimo.setQuantidadeParcelas(payload.getQuantidadeParcelas());
			simulacaoEmprestimo.setValorParcela(valorParcela);
			simulacaoEmprestimo.setTaxaJurosEmprestimo(taxaJurosEmpretimo);
			
			
			lsSimulacaoEmprestimo.add(simulacaoEmprestimo);
			
		} catch (ServerException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("Falha ao realizar simulação!!! ", e);
		}
		
		return simulacaoEmprestimo;
	}



	public String getDataAtual() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		
		String dataAtual = dateFormat.format(cal.getTime());
		
		return dataAtual;
	}
	
	public String getDataSimulacao() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, 30);
		String dataSimulacao = dateFormat.format(cal.getTime());
		
		return dataSimulacao;
	}



	public BigDecimal getValorParcela(SimularEmprestimoPayload payload) throws Exception {
		BigDecimal qtParcelas = BigDecimal.valueOf(payload.getQuantidadeParcelas());
		
		BigDecimal valorParcelaPasso1 = qtParcelas.multiply(getTaxaJuros(payload));
		BigDecimal valorParcelaPasso2 = BigDecimal.ONE.add( valorParcelaPasso1);
		BigDecimal valorParcelaPasso3 = payload.getValorContrato().multiply(valorParcelaPasso2);
		BigDecimal valorParcela = valorParcelaPasso3.divide(qtParcelas);
		
		return valorParcela;
	}



	public BigDecimal getTaxaJuros(SimularEmprestimoPayload payload) throws Exception {
		BigDecimal valorContratado = payload.getValorContrato();
		
		BigDecimal taxaDeJuros = BigDecimal.ZERO;
		
		if(BigDecimal.valueOf(1000).compareTo(valorContratado) <= 0) {
			taxaDeJuros = BigDecimal.valueOf(0.018d);
		} else {
			taxaDeJuros = BigDecimal.valueOf(0.03d);			
		}
		
		if(payload.getQuantidadeParcelas() > 12) {
			taxaDeJuros.add(BigDecimal.valueOf(0.005d));
		}
		return taxaDeJuros;
	}



	public Long getNumeroContrato(SimulacaoEmprestimo simulacaoEmprestimo) throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		
		String sNumeroDocumento = dateFormat.format(cal.getTime()) + String.format("%06d", simulacaoEmprestimo.getId());
		return Long.valueOf(sNumeroDocumento);
	}



	private void carregaCliente(SimulacaoEmprestimo simulacaoEmprestimo, SimularEmprestimoPayload payload) throws Exception {
		
		Cliente cliente = null;
		
		for (Iterator<Cliente> iterator = lsCliente.iterator(); iterator.hasNext();) {
			Cliente clienteAux = iterator.next();
			if(clienteAux.getCpf().equals(payload.getCpf())) {
				cliente = clienteAux;
				break;
			}
		}

		if(cliente == null) {
			throw new ServerException("Cliente não encontrado !!!");
		}
		
		simulacaoEmprestimo.setCliente(cliente);
		
	}



	private void validacoes(SimularEmprestimoPayload payload) throws Exception {
		if(Util.isCPF(payload.getCpf().toString()) == false) {
			throw new ServerException("CPF inválido");
		}
		
		if(Util.isEmail(payload.getEmail()) == false) {
			throw new ServerException("E-mail inválido");			
		}
		
		if(isMaiorMaximoParcelas(payload)) {
			throw new ServerException("Quantidade máxima de parcelas: 24 !!!");			
		}
		
	}



	public boolean isMaiorMaximoParcelas(SimularEmprestimoPayload payload) {
		return (payload.getQuantidadeParcelas() != null
				&& payload.getQuantidadeParcelas() > 24);
	}



	private void inserirUsuario(SimularEmprestimoPayload payload) throws Exception{
		Cliente cliente = new Cliente();
		Long id = Long.valueOf(lsCliente.size()+1);
		cliente.setId(id);
		cliente.setCpf(payload.getCpf());
		cliente.setNome(payload.getNome());
		cliente.setEmail(payload.getEmail());
		
		lsCliente.add(cliente);
		
	}



	private boolean isClienteCarregado(SimularEmprestimoPayload payload) throws Exception {
		for (Iterator<Cliente> iterator = lsCliente.iterator(); iterator.hasNext();) {
			Cliente cliente = iterator.next();
			if(cliente.getCpf().equals(payload.getCpf())) {
				return true;
			}
		}
		return false;
	}



	public List<SimulacaoEmprestimo> consultar(SimularEmprestimoPayload payload) throws Exception {
		
		try {
			List<SimulacaoEmprestimo> lsSimulacaoEmprestimo = getSimulacoes(payload);
			
			return lsSimulacaoEmprestimo;
		} catch (ServerException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("Falha ao realizar simulação!!! ", e);
		}
	}



	private List<SimulacaoEmprestimo> getSimulacoes(SimularEmprestimoPayload payload) throws Exception {
		
		List<SimulacaoEmprestimo> lsSimulacaoEmprestimoAux = new ArrayList<SimulacaoEmprestimo>();
		
 		for (Iterator<SimulacaoEmprestimo> iterator = lsSimulacaoEmprestimo.iterator(); iterator.hasNext();) {
			SimulacaoEmprestimo simulacaoEmprestimo = iterator.next();
			
			if(simulacaoEmprestimo.getCliente().getCpf().equals(payload.getCpf())) {
				lsSimulacaoEmprestimoAux.add(simulacaoEmprestimo);				
			}
			
		}
		return lsSimulacaoEmprestimoAux;
	}



	public Long remover(Long id) throws Exception {
		
		Long idAux = null;
		
 		for (Iterator<SimulacaoEmprestimo> iterator = lsSimulacaoEmprestimo.iterator(); iterator.hasNext();) {
			SimulacaoEmprestimo simulacaoEmprestimo = iterator.next();
			
			if(simulacaoEmprestimo.getId().equals(id)) {
				idAux = simulacaoEmprestimo.getId();
				lsSimulacaoEmprestimo.remove(simulacaoEmprestimo);
				break;
			}
			
		}
 		
		return idAux;
	}

}
