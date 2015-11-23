package com.flaviolisboa.siaa.web.jsf.converters.negocio;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import com.flaviolisboa.siaa.negocio.turmas.RepositorioTurma;
import com.flaviolisboa.siaa.negocio.turmas.Turma;
import com.flaviolisboa.siaa.util.entidades.Repositorio;
import com.flaviolisboa.siaa.web.jsf.converters.ConversorEntidadeAbstrato;

@Named
@ApplicationScoped
@FacesConverter(forClass = Turma.class)
public class ConversorTurma extends ConversorEntidadeAbstrato<Turma> {

	@Inject
	private RepositorioTurma repositorio;
	
	@Override
	protected Class<Turma> getClasseEntidade() {
		return Turma.class;
	}

	@Override
	protected Repositorio<Turma> getRepositorioEntidade() {
		return repositorio;
	}

}
