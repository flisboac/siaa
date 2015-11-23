package com.flaviolisboa.siaa.web.jsf.converters.negocio;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import com.flaviolisboa.siaa.negocio.turmas.participacoes.ParticipacaoAluno;
import com.flaviolisboa.siaa.negocio.turmas.participacoes.RepositorioParticipacaoAluno;
import com.flaviolisboa.siaa.util.entidades.Repositorio;
import com.flaviolisboa.siaa.web.jsf.converters.ConversorEntidadeAbstrato;

@Named
@ApplicationScoped
@FacesConverter(forClass = ParticipacaoAluno.class)
public class ConversorParticipacaoAluno extends ConversorEntidadeAbstrato<ParticipacaoAluno> {

	@Inject
	private RepositorioParticipacaoAluno repositorio;
	
	@Override
	protected Class<ParticipacaoAluno> getClasseEntidade() {
		return ParticipacaoAluno.class;
	}

	@Override
	protected Repositorio<ParticipacaoAluno> getRepositorioEntidade() {
		return repositorio;
	}

}
