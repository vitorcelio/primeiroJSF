package br.com.primeirojsf.repository;

import java.util.List;

import javax.faces.model.SelectItem;

import br.com.primeirojsf.model.Cidade;

public interface CidadeRepository {
	Cidade findById(Long id);
	List<SelectItem> getCidades();
	List<SelectItem> getCidadesByEstado(Long id);
}
