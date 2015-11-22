package com.flaviolisboa.siaa.negocio;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.flaviolisboa.siaa.negocio.autenticacoes.AutenticacaoSenha;
import com.flaviolisboa.siaa.negocio.autenticacoes.RepositorioAutenticacao;
import com.flaviolisboa.siaa.negocio.logins.Login;
import com.flaviolisboa.siaa.negocio.logins.RepositorioLogin;
import com.flaviolisboa.siaa.negocio.perfis.PerfilCoordenador;
import com.flaviolisboa.siaa.negocio.perfis.RepositorioPerfil;
import com.flaviolisboa.siaa.negocio.pessoas.Pessoa;
import com.flaviolisboa.siaa.negocio.pessoas.RepositorioPessoa;

@Singleton
@Startup
public class ServicoInstalacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private RepositorioPessoa repositorioPessoa;
	
	@EJB
	private RepositorioPerfil repositorioPerfil;

	@EJB
	private RepositorioLogin repositorioLogin;

	@EJB
	private RepositorioAutenticacao repositorioAutenticacao;
	
	@PostConstruct
	public void init() {
		BigInteger numeroPessoas = new BigInteger(repositorioPerfil.contar().toString());
		
		if (numeroPessoas.compareTo(BigInteger.ZERO) == 0) {
			Calendar cal = Calendar.getInstance();
			cal.set(1970, Calendar.JANUARY, 1);
			Pessoa pessoa = new Pessoa("Administrador", cal.getTime());
			pessoa = repositorioPessoa.salvar(pessoa);
			
			AutenticacaoSenha autenticacao = repositorioAutenticacao.novaAutenticacaoSenha(pessoa);
			autenticacao.cadastrar(new char[]{ 'a', 'd', 'm', 'i', 'n' });
			autenticacao = repositorioAutenticacao.salvar(autenticacao);
			
			PerfilCoordenador perfil = new PerfilCoordenador(pessoa);
			perfil = repositorioPerfil.salvar(perfil);
			
			Login login = new Login(pessoa, "admin");
			login = repositorioLogin.salvar(login);
		}
	}
}
