package br.com.primeirojsf.dao;

import javax.persistence.NoResultException;

import br.com.primeirojsf.model.Pessoa;
import br.com.primeirojsf.repository.PessoaRepository;

public class PessoaDao implements PessoaRepository{

	private Dao<Pessoa> dao = new Dao<>(Pessoa.class);
	
	@Override
	public Pessoa consultarUsuario(String login, String senha) {
		String jpql = "SELECT p FROM Pessoa p WHERE p.login = :login AND p.senha = :senha";
		try {
			return (Pessoa) dao.getEntityManger().createQuery(jpql, Pessoa.class).setParameter("login", login)
					.setParameter("senha", senha).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
