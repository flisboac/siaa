package com.flaviolisboa.siaa.web.views.login;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.flaviolisboa.siaa.negocio.autenticacoes.AutenticacaoSenha;
import com.flaviolisboa.siaa.negocio.autenticacoes.RepositorioAutenticacao;
import com.flaviolisboa.siaa.negocio.logins.Login;
import com.flaviolisboa.siaa.negocio.perfis.Perfil;
import com.flaviolisboa.siaa.negocio.perfis.RepositorioPerfil;
import com.flaviolisboa.siaa.negocio.perfis.TipoPerfil;
import com.flaviolisboa.siaa.negocio.pessoas.Pessoa;
import com.flaviolisboa.siaa.util.UtilJsf;
import com.flaviolisboa.siaa.web.SessaoBean;

@Named
@ViewScoped
public class LoginViewBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Informe um nome de login ou matricula.")
	@Size(min = 1, max = Login.TAMANHO_NOME, message = "Nome/matrícula deve estar entre {min} e {max} caracter(es).")
	private String nome;
	
	@NotNull(message = "Informe uma senha.")
	@Size(min = 1, max = AutenticacaoSenha.TAMANHO_SENHA, message = "Senha deve estar entre {min} e {max} caracter(es).")
	private char[] senha;// = new char[AutenticacaoSenha.TAMANHO_SENHA];
	
	@NotNull(message = "Informe um tipo de perfil.")
	private TipoPerfil tipoPerfil;
	
	@EJB
	private RepositorioPerfil repositorioPerfil;
	
	@EJB
	private RepositorioAutenticacao repositorioAutenticacao;
	
	@Inject
	private SessaoBean sessaoBean;
	
	public String doLogin() {
		boolean autenticado = false;
		Perfil perfil = repositorioPerfil.buscarAlgum(tipoPerfil, nome);
		
		if (perfil != null) {
			Pessoa pessoa = perfil.getPessoa();
			AutenticacaoSenha autenticacao = repositorioAutenticacao.buscarAlgum(pessoa, AutenticacaoSenha.class);
			
			if (autenticacao != null) {
				autenticado = autenticacao.isCredenciaisValidas(senha);
			}
		}
		
		if (autenticado) {
			sessaoBean.setPerfil(perfil);
			String navegacao = redirecionar();
			return navegacao;
		} else {
			UtilJsf.erro(null, "Usuário ou senha inválidos.", null);
		}
		
		return "";
	}
	
	private String redirecionar() {
		switch (tipoPerfil) {
		case ALUNO:
			return "/aluno?faces-redirect=true";
		case COORDENADOR:
			return "/coordenador/index.xhtml?faces-redirect=true";
		case PROFESSOR:
			return "/professor?faces-redirect=true";
		}
		return "";
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public char[] getSenha() {
		return senha;
	}
	
	public void setSenha(char[] senha) {
		this.senha = senha;
	} 
	
	public TipoPerfil getTipoPerfil() {
		return tipoPerfil;
	}
	
	public void setTipoPerfil(TipoPerfil tipoPerfil) {
		this.tipoPerfil = tipoPerfil;
	}
	
	public TipoPerfil[] getTiposPerfil() {
		return TipoPerfil.values();
	}
	
	public String getDescricaoTipoPerfil(TipoPerfil tipoPerfil) {
		if (tipoPerfil == null) {
			return null;
		}
		switch (tipoPerfil) {
		case ALUNO:
			return "Aluno";
		case COORDENADOR:
			return "Coordenador";
		case PROFESSOR:
			return "Professor";
		default:
			return "";
		}
	}
}
