package br.com.cresol.desafio.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.cresol.desafio.entity.TbCliente;

@Stateless
public class TbClienteDAO extends DAOGeneric<Long, TbCliente> {
	
	public TbCliente findByCpf(TbCliente obj) {
		String srtSQL = "select c FROM TbCliente c where cpf = '" + obj.getCpf() + "' ";
		try {
			obj = (TbCliente) getEntityManager().createQuery(srtSQL).getSingleResult();
		} catch (NoResultException e) {
			this.saveFlush(obj);
		}
		return obj;
	}
}