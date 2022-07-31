package br.com.primeirojsf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Nome não pode ser nulo nem vazio!")
	@Length(min = 4, max = 10, message = "Nome deve ter no mínimo 4 e no máximo 10 caracteres")
	@Column(nullable = false)
	private String nome;

	@NotBlank(message = "Sobrenome não pode ser nulo nem vazio!")
	@Column(nullable = false)
	private String sobrenome;

	@NotNull(message = "A idade deve ser informada!")
	@Column(nullable = false)
	private Integer idade;

	@Temporal(TemporalType.DATE)
	private Date date;

	@NotBlank(message = "Sexo não pode ser nulo nem vazio!")
	private String sexo;

	@NotEmpty(message = "Selecione o que você mais usa!")
	private String[] frameworks;

	private Boolean ativo;

	@NotBlank(message = "Login não pode ser nulo nem vazio!")
	@Column(nullable = false, unique = true)
	private String login;

	private String loginAntigo;

	@Length(max = 8, message = "A senha deve ter pelo menos 8 caracteres!")
	@Column(nullable = false)
	private String senha;

	@NotBlank(message = "Perfil não pode ser nulo nem vazio!")
	@Column(nullable = false)
	private String perfil;

	@OneToMany(mappedBy = "pessoa")
	private List<Lancamento> lancamentos = new ArrayList<>();

	private String[] linguagens;

	private String cep;

	private String logradouro;

	private String complemento;

	private String bairro;

	private String localidade;

	private String uf;

	@NotNull(message = "É necessário informar a cidade!")
	@ManyToOne
	private Cidade cidade;

	@NotNull(message = "É necessário informar o estado!")
	@Transient
	private Estado estado;

	@Column(columnDefinition = "TEXT")
	private String fotoIcon;

	private String extensao;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] fotoOriginal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String[] getFrameworks() {
		return frameworks;
	}

	public void setFrameworks(String[] frameworks) {
		this.frameworks = frameworks;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public String[] getLinguagens() {
		return linguagens;
	}

	public void setLinguagens(String[] linguagens) {
		this.linguagens = linguagens;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getFotoIcon() {
		return fotoIcon;
	}

	public void setFotoIcon(String fotoIcon) {
		this.fotoIcon = fotoIcon;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public String getLoginAntigo() {
		return loginAntigo;
	}

	public void setLoginAntigo(String loginAntigo) {
		this.loginAntigo = loginAntigo;
	}

	public byte[] getFotoOriginal() {
		return fotoOriginal;
	}

	public void setFotoOriginal(byte[] fotoOriginal) {
		this.fotoOriginal = fotoOriginal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		return Objects.equals(id, other.id);
	}

}
