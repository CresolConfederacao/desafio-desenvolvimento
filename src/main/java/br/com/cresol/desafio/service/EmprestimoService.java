package br.com.cresol.desafio.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.ejb.EJB;

import br.com.cresol.desafio.dao.TbClienteDAO;
import br.com.cresol.desafio.dao.TbContratoDAO;
import br.com.cresol.desafio.dto.Payload;
import br.com.cresol.desafio.entity.TbCliente;
import br.com.cresol.desafio.entity.TbContrato;

/**
 * @author evandro
 *
 */
public class EmprestimoService {
	
	@EJB
	private TbClienteDAO clienteDAO;
	
	@EJB
	private TbContratoDAO contratoDAO;

	public TbContrato simular(Payload payload) {
		TbCliente cliente = new TbCliente();
		cliente.setCpf(payload.getCpf());
		cliente.setEmail(payload.getEmail());
		cliente.setNome(payload.getNome());
		//cliente = clienteDAO.findByCpf(cliente);
		
		TbContrato contrato = new TbContrato();
		contrato.setTbCliente(cliente);
		contrato.setData(new Date());
		contrato.setQtdParcelas(payload.getQtdParcelas());
		contrato.setValor(payload.getValor());
		
		LocalDate localDate = contrato.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		//contrato.setNumeroContrato(String.valueOf(localDate.getYear()) + String.valueOf(localDate.getMonthValue()) + String.valueOf(localDate.getDayOfMonth()) + String.format("%06d", contratoDAO.findAll().size()+1));
		contrato.setNumeroContrato(String.valueOf(localDate.getYear()) + String.valueOf(localDate.getMonthValue()) + String.valueOf(localDate.getDayOfMonth()) + String.format("%06d", 1));
		contrato.setTaxaJuros(definirTaxaPeloValor(contrato.getValor()) + definirTaxaPelaQtdParcelas(contrato.getQtdParcelas()));
		contrato.setValorParcela((contrato.getValor()*(1+(contrato.getQtdParcelas()*(contrato.getTaxaJuros()/100))))/contrato.getQtdParcelas());
		localDate = localDate.plusDays(30);
		contrato.setDataValidade(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		//contratoDAO.saveFlush(contrato);
		
		return contrato;
	}
	
	private Float definirTaxaPeloValor(Float valor) {
		if (valor <= 1000) {
			return Float.valueOf("1.8");
		} else {
			return Float.valueOf("3.0");
		}
	}
	
	private Float definirTaxaPelaQtdParcelas(Integer qtdParcelas) {
		if (qtdParcelas > 12) {
			return Float.valueOf("0.5");
		} else {
			return Float.valueOf("0");
		}
	}

}
