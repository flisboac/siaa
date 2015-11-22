package com.flaviolisboa.siaa.negocio.coordenacoes;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.flaviolisboa.siaa.util.entidades.impl.RepositorioJpa;

@Stateless(name = "RepositorioCoordenacao")
@Local(RepositorioCoordenacao.class)
public class RepositorioCoordenacaoJpa extends RepositorioJpa<Coordenacao> implements RepositorioCoordenacao {

	@SuppressWarnings("unused")
	private ValidadorCoordenacao validador;
	
	public RepositorioCoordenacaoJpa() {
		super(Coordenacao.class);
	}
	
	@EJB
	public void setValidador(ValidadorCoordenacao validador) {
		this.validador = validador;
		super.setValidador(validador);
	}
}
