package com.flaviolisboa.siaa.negocio.turmas.participacoes;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.flaviolisboa.siaa.util.entidades.impl.RepositorioJpa;

@Stateless(name = "RepositorioParticipacaoAluno")
@Local(RepositorioParticipacaoAluno.class)
public class RepositorioParticipacaoAlunoJpa extends RepositorioJpa<ParticipacaoAluno>
		implements RepositorioParticipacaoAluno {

	@SuppressWarnings("unused")
	private ValidadorParticipacaoAluno validador;
	
	public RepositorioParticipacaoAlunoJpa() {
		super(ParticipacaoAluno.class);
	}

	@EJB
	public void setValidador(ValidadorParticipacaoAluno validador) {
		this.validador = validador;
		super.setValidador(validador);
	}
	
}
