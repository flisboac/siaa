package com.flaviolisboa.siaa.negocio.pessoas;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.flaviolisboa.siaa.util.entidades.impl.RepositorioJpa;

@Stateless(name = "RepositorioPessoa")
@Local(RepositorioPessoa.class)
public class RepositorioPessoaJpa extends RepositorioJpa<Pessoa> implements RepositorioPessoa {

	@SuppressWarnings("unused")
	private ValidadorPessoa validador;
	
	public RepositorioPessoaJpa() {
		super(Pessoa.class);
	}

	@EJB
	public void setValidador(ValidadorPessoa validador) {
		this.validador = validador;
		super.setValidador(validador);
	}
}
