package br.com.primeirojsf.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.primeirojsf.model.Estado;
import br.com.primeirojsf.repository.EstadoRepository;

public class EstadoDao implements EstadoRepository{
	
	private Dao<Estado> dao = new Dao<>(Estado.class);
	
	@Override
	public Estado findById(Long id) {
		return dao.getEntityManger().find(Estado.class, id);
	}
	
	@Override
	public List<SelectItem> getEstados() {
		List<SelectItem> selectItens = new ArrayList<>();
		
		String jpql = "SELECT e FROM Estado e";
		List<Estado> estados = dao.getEntityManger().createQuery(jpql, Estado.class).getResultList();
		
		estados.forEach(estado -> {
			selectItens.add(new SelectItem(estado, estado.getNome()));
		});
		
		return selectItens;
	}
	
}
