package br.com.primeirojsf.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.primeirojsf.dao.Dao;
import br.com.primeirojsf.dao.LancamentoDao;
import br.com.primeirojsf.model.Lancamento;
import br.com.primeirojsf.model.Pessoa;
import br.com.primeirojsf.repository.LancamentoRepository;

@ViewScoped
@ManagedBean(name = "lancamentoBean")
public class LancamentoBean {

	private LancamentoRepository daoLancamento = new LancamentoDao();

	private Dao<Lancamento> dao = new Dao<>(Lancamento.class);

	private Lancamento lancamento = new Lancamento();
	private List<Lancamento> lancamentos = new ArrayList<>();

	public String salvar() {
		lancamento.setPessoa(pessoaLogada());
		dao.save(lancamento);
		carregarLancamentos();
		return "";
	}

	public String novo() {
		lancamento = new Lancamento();
		return "";
	}

	public String remover() {
		dao.remove(lancamento);
		carregarLancamentos();
		return "";
	}

	@PostConstruct
	public void carregarLancamentos() {
		this.lancamentos = daoLancamento.listaLancamentos(pessoaLogada().getId());
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public Pessoa pessoaLogada() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		return (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
	}

	public LancamentoRepository getDaoLancamento() {
		return daoLancamento;
	}

	public void setDaoLancamento(LancamentoRepository daoLancamento) {
		this.daoLancamento = daoLancamento;
	}

	public Dao<Lancamento> getDao() {
		return dao;
	}

	public void setDao(Dao<Lancamento> dao) {
		this.dao = dao;
	}

}
