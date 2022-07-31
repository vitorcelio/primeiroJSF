package br.com.primeirojsf.dao;

import java.util.List;

import javax.persistence.EntityManager;

public class Dao<E> {

	private EntityManager em = HibernateUtil.getEntityManager();

	private Class<E> classe;

	public Dao(Class<E> classe) {
		this.classe = classe;
	}

	private Dao<E> transacaoBegin() {
		this.em.getTransaction().begin();
		return this;
	}

	private Dao<E> transacaoCommit() {
		this.em.getTransaction().commit();
		return this;
	}

	public Dao<E> save(E entidade) {
		this.transacaoBegin();
		this.em.persist(entidade);
		this.transacaoCommit();
		return this;
	}

	public Dao<E> update(E entidade) {
		this.transacaoBegin();
		entidade = this.em.merge(entidade);
		this.transacaoCommit();
		return this;
	}

	public Dao<E> remove(E entidade) {
		this.transacaoBegin();
		this.em.remove(entidade);
		this.transacaoCommit();
		return this;
	}

	public E findById(Long id) {
		return this.em.find(classe, id);
	}

	public List<E> getAll() {
		String hql = "SELECT e FROM " + classe.getName() + " e";
		return this.em.createQuery(hql, classe).getResultList();
	}
	
	public EntityManager getEntityManger() {
		return em;
	}

}
