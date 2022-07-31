package br.com.primeirojsf.bean;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;

import br.com.primeirojsf.dao.CidadeDao;
import br.com.primeirojsf.dao.Dao;
import br.com.primeirojsf.dao.EstadoDao;
import br.com.primeirojsf.dao.PessoaDao;
import br.com.primeirojsf.model.Estado;
import br.com.primeirojsf.model.Pessoa;
import br.com.primeirojsf.repository.CidadeRepository;
import br.com.primeirojsf.repository.EstadoRepository;
import br.com.primeirojsf.repository.PessoaRepository;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Dao<Pessoa> dao = new Dao<>(Pessoa.class);

	private PessoaRepository daoPessoa = new PessoaDao();

	private EstadoRepository daoEstado = new EstadoDao();

	private CidadeRepository daoCidade = new CidadeDao();

	private Pessoa usuario = pessoaLogada();
	private Pessoa pessoa = new Pessoa();
	private List<Pessoa> pessoas = new ArrayList<>();
	private List<SelectItem> estados = new ArrayList<>();
	private List<SelectItem> cidades = new ArrayList<>();
	private Part arquivo;

	public String update() throws IOException {
		// Processando imagem no banco

		if (arquivo != null) {
			byte[] imagem = getByte(arquivo.getInputStream());
			String miniatura = converterMiniatura(imagem).split("pessoa")[0];
			String extensao = converterMiniatura(imagem).split("pessoa")[1];

			pessoa.setFotoOriginal(imagem);
			pessoa.setExtensao(extensao);
			pessoa.setFotoIcon(miniatura);
		}

		dao.save(pessoa);
		carregarPessoas();
		mostrarMsg(null, "Cadastrado com sucesso");
		return "";
	}

	public void registrarLog() {
		System.out.println("método registrarLog");
	}

	public void mudandoValor(ValueChangeEvent event) {
		pessoa.setLoginAntigo(event.getOldValue().toString());
	}

	private String converterMiniatura(byte[] imagem) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagem));

		// Pegando o tipo da imagem
		int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
		int largura = 200;
		int altura = 200;

		// Redimensionando a imagem
		BufferedImage redizeImage = new BufferedImage(largura, altura, type);
		Graphics2D graphic = redizeImage.createGraphics();
		graphic.drawImage(bufferedImage, 0, 0, largura, altura, null);
		graphic.dispose();

		// Escrevendo a imagem em tamanho menor
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String extensao = arquivo.getContentType().split("/")[1];
		ImageIO.write(redizeImage, extensao, baos);
		String miniatura = "data:" + arquivo.getContentType() + ";base64,"
				+ DatatypeConverter.printBase64Binary(baos.toByteArray());

		return miniatura + "pessoa" + extensao;
	}

	private void mostrarMsg(String id, String string) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(string);
		context.addMessage(id, message);
	}

	public void pesquisaCep(AjaxBehaviorEvent event) {
		try {
			URL url = new URL("https://viacep.com.br/ws/" + pessoa.getCep() + "/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String cep = "";
			StringBuilder jsonCep = new StringBuilder();

			while ((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}

			Pessoa gson = new Gson().fromJson(jsonCep.toString(), Pessoa.class);
			pessoa.setLogradouro(gson.getLogradouro());
			pessoa.setBairro(gson.getBairro());
			pessoa.setLocalidade(gson.getLocalidade());
			pessoa.setComplemento(gson.getComplemento());
			pessoa.setUf(gson.getUf());

		} catch (Exception e) {
			e.printStackTrace();
			mostrarMsg("cep", "Erro ao consultar o cep");
		}
	}

	public String novo() {
		pessoa = new Pessoa();
		return "";
	}

	public String deletar() {
		dao.remove(pessoa);
		novo();
		carregarPessoas();
		mostrarMsg(null, "Removido com sucesso");
		return "";
	}

	public String editar() {
		if (pessoa.getCidade() != null) {
			Estado estado = pessoa.getCidade().getEstado();
			pessoa.setEstado(estado);
			List<SelectItem> cidadesByEstado = daoCidade.getCidadesByEstado(estado.getId());
			setCidades(cidadesByEstado);
		}

		return "";
	}

	@PostConstruct
	public void carregarPessoas() {
		pessoas = dao.getAll();
	}

	public Boolean permitirAcesso(String acesso) {
		return pessoaLogada().getPerfil().equals(acesso);
	}

	public Boolean permitirAcesso(String acesso, String acesso2) {
		return pessoaLogada().getPerfil().equals(acesso) || pessoaLogada().getPerfil().equals(acesso2);
	}

	public void carregaCidades(AjaxBehaviorEvent event) {
		Estado estado = (Estado) ((HtmlSelectOneMenu) event.getSource()).getValue();

		if (estado != null) {
			pessoa.setEstado(estado);
			List<SelectItem> cidadesByEstado = daoCidade.getCidadesByEstado(estado.getId());
			setCidades(cidadesByEstado);
		}
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<SelectItem> getEstados() {
		estados = daoEstado.getEstados();
		return estados;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

	public Part getArquivo() {
		return arquivo;
	}

	public void setArquivo(Part arquivo) {
		this.arquivo = arquivo;
	}

	public Pessoa getUsuario() {
		usuario = pessoaLogada();
		return usuario;
	}

	public void setUsuario(Pessoa usuario) {
		this.usuario = usuario;
	}

	private byte[] getByte(InputStream is) throws IOException {
		@SuppressWarnings("unused")
		int len;
		int size = 1024;
		byte[] buf = null;

		if (is instanceof ByteArrayInputStream) {
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			while ((len = is.read(buf, 0, size)) != -1) {
				bos.write(buf, 0, size);
			}

			buf = bos.toByteArray();
		}

		return buf;
	}

	public void download() throws IOException {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Long idDownload = Long.parseLong(params.get("fileDownloadId"));
		Pessoa pessoa = dao.findById(idDownload);

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		response.addHeader("Content-Disposition",
				"attachment; filename=" + pessoa.getLogin() + "." + pessoa.getExtensao());
		response.setContentType("application/octet-stream");
		response.setContentLength(pessoa.getFotoOriginal().length);
		response.getOutputStream().write(pessoa.getFotoOriginal());
		response.getOutputStream().flush();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public String logar() {
		Pessoa pessoaLogada = daoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());

		if (pessoaLogada != null) {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.getSessionMap().put("usuarioLogado", pessoaLogada);
			return "paginaum.jsf?faces-redirect=true";
		}

		return "index.jsf?faces-redirect=true";
	}

	public String deslogar() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.getSessionMap().remove("usuarioLogado");

		HttpServletRequest httpServletRequest = (HttpServletRequest) externalContext.getRequest();
		httpServletRequest.getSession().invalidate();

		return "index.jsf?faces-redirect=true";
	}

	public Pessoa pessoaLogada() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		return (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
	}

	public Dao<Pessoa> getDao() {
		return dao;
	}

	public void setDao(Dao<Pessoa> dao) {
		this.dao = dao;
	}

	public PessoaRepository getDaoPessoa() {
		return daoPessoa;
	}

	public void setDaoPessoa(PessoaRepository daoPessoa) {
		this.daoPessoa = daoPessoa;
	}

	public EstadoRepository getDaoEstado() {
		return daoEstado;
	}

	public void setDaoEstado(EstadoRepository daoEstado) {
		this.daoEstado = daoEstado;
	}

	public CidadeRepository getDaoCidade() {
		return daoCidade;
	}

	public void setDaoCidade(CidadeRepository daoCidade) {
		this.daoCidade = daoCidade;
	}

}
