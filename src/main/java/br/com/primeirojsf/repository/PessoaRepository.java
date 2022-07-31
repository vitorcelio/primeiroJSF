package br.com.primeirojsf.repository;

import br.com.primeirojsf.model.Pessoa;

public interface PessoaRepository {
	Pessoa consultarUsuario(String login, String senha);
}
