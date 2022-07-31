package br.com.primeirojsf.dao;

import java.util.List;

import br.com.primeirojsf.model.Lancamento;
import br.com.primeirojsf.repository.LancamentoRepository;

public class LancamentoDao implements LancamentoRepository{

	private Dao<Lancamento> dao = new Dao<>(Lancamento.class);
	
	@Override
	public List<Lancamento> listaLancamentos(Long idPessoa) {
		String jpql = "SELECT l FROM Lancamento l WHERE l.pessoa.id = :id";
		return dao.getEntityManger().createQuery(jpql, Lancamento.class).setParameter("id", idPessoa).getResultList();
	}
	
}
