package com.flaviolisboa.siaa.web.views.coordenador;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.flaviolisboa.siaa.negocio.autenticacoes.Autenticacao;
import com.flaviolisboa.siaa.negocio.autenticacoes.AutenticacaoSenha;
import com.flaviolisboa.siaa.negocio.autenticacoes.RepositorioAutenticacao;
import com.flaviolisboa.siaa.negocio.logins.Login;
import com.flaviolisboa.siaa.negocio.logins.RepositorioLogin;
import com.flaviolisboa.siaa.negocio.perfis.Perfil;
import com.flaviolisboa.siaa.negocio.perfis.RepositorioPerfil;
import com.flaviolisboa.siaa.negocio.pessoas.Pessoa;
import com.flaviolisboa.siaa.negocio.pessoas.RepositorioPessoa;

@Named
@ViewScoped
public class PessoaViewBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static enum Estado {
		LISTAGEM_PESSOAS,
		VISUALIZACAO_PESSOA,
		CRIACAO_PESSOA,
		EDICAO_PESSOA,
		VISUALIZACAO_PERFIS,
	}
	
	// EJB
	
	@EJB
	private RepositorioPessoa repositorioPessoa;
	@EJB
	private RepositorioAutenticacao repositorioAutenticacao;
	@EJB
	private RepositorioLogin repositorioLogin;
	@EJB
	private RepositorioPerfil repositorioPerfil;
	
	// ESTADO
	
	private Estado estado = Estado.LISTAGEM_PESSOAS;
	
	// CADASTRO PESSOA 
	
	private Long idPessoa;
	
	private Pessoa pessoa;
	
	private List<Pessoa> pessoas;
	
    // CADASTRO LOGIN
    
    private Login login;
    
    private List<Login> logins;
    
    private List<String> nomesLoginParaInclusao;
    
    // CADASTRO AUTENTICACOES
	
    private AutenticacaoSenha autenticacaoSenha;
    
    private List<Autenticacao> autenticacoes;
    
    // EVENTOS MANAGEDBEAN
    
    @PostConstruct
    private void init() {
    	atualizar();
    }
    
    private void atualizar() {
    	switch (estado) {
    	case LISTAGEM_PESSOAS:
    		this.pessoa = null;
    		this.pessoas = repositorioPessoa.buscarTodos();
    		break;
    	case CRIACAO_PESSOA:
    		this.pessoas = null;
    	case VISUALIZACAO_PESSOA:
    	case EDICAO_PESSOA:
    		this.autenticacoes = this.pessoa.getAutenticacoes();
    		this.logins = this.pessoa.getLogins();
    		break;
    	case VISUALIZACAO_PERFIS:
    		break;
    	}
    }
    
    // GETTERS

	public Pessoa getPessoa() {
		return pessoa;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public Login getLogin() {
		return login;
	}

	public List<Login> getLogins() {
		return logins;
	}

	public AutenticacaoSenha getAutenticacaoSenha() {
		return autenticacaoSenha;
	}

	public List<Autenticacao> getAutenticacoes() {
		return autenticacoes;
	}
    
	// SETTERS
	
	public void setIdPessoa(String idPessoa) {
		if (idPessoa != null && idPessoa.matches("[0-9]+")) {
			this.idPessoa = Long.valueOf(idPessoa);
			Pessoa pessoa = repositorioPessoa.buscarAlgum(this.idPessoa);
			
			if (pessoa != null) {
				this.estado = Estado.EDICAO_PESSOA;
				setPessoa(pessoa);
			}
		}
	}
	
	public void setPessoa(Pessoa pessoa) {
		if (pessoa != null && !pessoa.equals(this.pessoa)) {
			this.pessoa = pessoa;
			atualizar();
		}
	}
	
	// GETTERS ESTADO

	public boolean isListandoPessoas() {
		return estado == Estado.LISTAGEM_PESSOAS;
	}
	
	public boolean isCriandoPessoa() {
		return estado == Estado.CRIACAO_PESSOA;
	}
	
	public boolean isEditandoPessoa() {
		return estado == Estado.EDICAO_PESSOA;
	}

	public boolean isVisualizandoPessoa() {
		return estado == Estado.VISUALIZACAO_PESSOA;
	}
	
	// ACOES
	
	public void novaPessoa() {
		this.estado = Estado.CRIACAO_PESSOA;
		atualizar();
	}
	
	public void visualizarPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
		this.estado = Estado.VISUALIZACAO_PESSOA;
		atualizar();
	}
	
	public void editarPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
		this.estado = Estado.EDICAO_PESSOA;
		atualizar();
	}
	
	public void voltarParaListagemPessoas() {
		this.estado = Estado.LISTAGEM_PESSOAS;
		atualizar();
	}
	
	public void passarParaEdicaoPessoa() {
		this.estado = Estado.EDICAO_PESSOA;
		atualizar();
	}
	
	public void desativarLogin(Login login) {
		if (login.isAtivo()) {
			login.desativar();
			this.repositorioLogin.salvar(login);
		}
	}
	
	public void ativarLogin(Login login) {
		if (!login.isAtivo()) {
			login.desativar();
			this.repositorioLogin.salvar(login);
		}
	}
	
	public void desativarPerfil(Perfil perfil) {
		if (perfil.isAtivo()) {
			perfil.desativar();
			this.repositorioPerfil.salvar(perfil);
		}
	}
	
	public void ativarPerfil(Perfil perfil) {
		if (!perfil.isAtivo()) {
			perfil.ativar();
			this.repositorioPerfil.salvar(perfil);
		}
	}
	
}
