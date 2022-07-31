package br.com.primeirojsf.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.primeirojsf.model.Cidade;
import br.com.primeirojsf.repository.CidadeRepository;

public class CidadeDao implements CidadeRepository{
	
	private Dao<Cidade> dao = new Dao<>(Cidade.class);
	
	@Override
	public Cidade findById(Long id) {
		return dao.getEntityManger().find(Cidade.class, id);
	}
	
	@Override
	public List<SelectItem> getCidades() {
		List<SelectItem> selectItens = new ArrayList<>();
		
		String jpql = "SELECT c FROM Cidade c";
		List<Cidade> cidades = dao.getEntityManger().createQuery(jpql, Cidade.class).getResultList();
		
		cidades.forEach(cidade -> {
			selectItens.add(new SelectItem(cidade, cidade.getNome()));
		});
		
		return selectItens;
	}
	
	@Override
	public List<SelectItem> getCidadesByEstado(Long id) {
		List<SelectItem> selectItens = new ArrayList<>();
		
		String jpql = "SELECT c FROM Cidade c WHERE c.estado.id = :id";
		List<Cidade> cidades = 
				dao.getEntityManger()
					.createQuery(jpql, Cidade.class)
					.setParameter("id", id)
					.getResultList();
		
		cidades.forEach(cidade -> {
			selectItens.add(new SelectItem(cidade, cidade.getNome()));
		});
		
		return selectItens;
	}

}
