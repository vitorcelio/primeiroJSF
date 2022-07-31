package br.com.primeirojsf.repository;

import java.util.List;

import javax.faces.model.SelectItem;

import br.com.primeirojsf.model.Estado;

public interface EstadoRepository {
	Estado findById(Long id);
	List<SelectItem> getEstados();
}
