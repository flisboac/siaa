package com.flaviolisboa.siaa.web.jsf.converters.negocio;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import com.flaviolisboa.siaa.negocio.perfis.Perfil;
import com.flaviolisboa.siaa.negocio.perfis.RepositorioPerfil;
import com.flaviolisboa.siaa.util.entidades.Repositorio;
import com.flaviolisboa.siaa.web.jsf.converters.ConversorEntidadeAbstrato;

@Named
@ApplicationScoped
@FacesConverter(forClass = Perfil.class)
public class ConversorPerfil extends ConversorEntidadeAbstrato<Perfil> {

	@Inject
	private RepositorioPerfil repositorio;
	
	@Override
	protected Class<Perfil> getClasseEntidade() {
		return Perfil.class;
	}

	@Override
	protected Repositorio<Perfil> getRepositorioEntidade() {
		return repositorio;
	}

}
