package br.com.primeirojsf.repository;

import java.util.List;

import br.com.primeirojsf.model.Lancamento;

public interface LancamentoRepository {
	List<Lancamento> listaLancamentos(Long idPessoa);
}
