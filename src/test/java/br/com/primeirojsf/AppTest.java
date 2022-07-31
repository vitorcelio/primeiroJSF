package br.com.primeirojsf;

import java.sql.Date;

import org.junit.Test;

import br.com.primeirojsf.dao.Dao;
import br.com.primeirojsf.model.Pessoa;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@SuppressWarnings("deprecation")
	@Test
	public void testeInsercaoPessoa() {
		Dao<Pessoa> dao = new Dao<>(Pessoa.class);
		Pessoa pessoa = new Pessoa();
		Date date = new Date(2002, 7, 4);
		
		pessoa.setNome("Vítor");
		pessoa.setSobrenome("Célio");
		pessoa.setIdade(20);
		pessoa.setDate(date);
		
		dao.save(pessoa);
	}

}
