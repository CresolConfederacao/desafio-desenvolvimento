package br.com.cresol.desafio.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DAOGeneric<PK, T> {

	@PersistenceContext(unitName = "MySQLEjbComponentPU")
	private EntityManager emMySql;

	public DAOGeneric() {
	}
	
	public EntityManager getEntityManager() {
		return emMySql;
	}

	public T save(T entity) {
		getEntityManager().persist(entity);
		return entity;
	}

	public T saveFlush(T entity) {
		getEntityManager().persist(entity);
		getEntityManager().flush();
		return entity;
	}

	public Serializable saveSerializable(Serializable entity) {
		getEntityManager().persist(entity);
		return entity;
	}

	public void update(T entity) {
		getEntityManager().merge(entity);
	}

	public void updateFlush(T entity) {
		getEntityManager().merge(entity);
		flush();
	}

	public void updateSerializable(Serializable entity) {
		getEntityManager().merge(entity);
	}
	
	public void merge(T entity){
		getEntityManager().merge(entity);
	}
	
	public void mergeFlush(T entity){
		getEntityManager().merge(entity);
		flush();
	}

	public void delete(T entity) {
		getEntityManager().remove(entity);
	}

	public void deleteFlush(T entity) {
		getEntityManager().remove(entity);
		getEntityManager().flush();
	}
	
	public void deleteSerializable(Serializable entity) {
		getEntityManager().remove(entity);
	}

	public void refresh(T entity) {
		getEntityManager().refresh(entity);
	}

	public T find(PK id) {
		return (T) getEntityManager().find(getTypeClass(), id);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<T> findAll() {
		return getEntityManager().createQuery(("FROM " + getTypeClass().getName())).getResultList();
	}
	
	public void flush() {
		getEntityManager().flush();
	}

	public void clear() {
		getEntityManager().clear();
	}
	


	public Query createQuery(String query) {
		return getEntityManager().createQuery(query);
	}
	
	public Query createNativeQuery(String query) {
		return getEntityManager().createNativeQuery(query);
	}

	public Query createNamedQuery(String queryName) {
		return getEntityManager().createNamedQuery(queryName);
	}

	@SuppressWarnings("unchecked")
	private Class<T> getTypeClass() {
		Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		return clazz;
	}
	

	protected void addParameters(Query query, final List<Object> params) {
		if (params != null) {
			for (int i = 1; i <= params.size(); i++) {
				query.setParameter(i, params.get(i - 1));
			}
		}
	}
}
