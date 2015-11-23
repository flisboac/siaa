package com.flaviolisboa.siaa.web.jsf.converters.negocio;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import com.flaviolisboa.siaa.negocio.materias.Materia;
import com.flaviolisboa.siaa.negocio.materias.RepositorioMateria;
import com.flaviolisboa.siaa.util.entidades.Repositorio;
import com.flaviolisboa.siaa.web.jsf.converters.ConversorEntidadeAbstrato;

@Named
@ApplicationScoped
@FacesConverter(forClass = Materia.class)
public class ConversorMateria extends ConversorEntidadeAbstrato<Materia> {

	@Inject
	private RepositorioMateria repositorio;
	
	@Override
	protected Class<Materia> getClasseEntidade() {
		return Materia.class;
	}

	@Override
	protected Repositorio<Materia> getRepositorioEntidade() {
		return repositorio;
	}

}
