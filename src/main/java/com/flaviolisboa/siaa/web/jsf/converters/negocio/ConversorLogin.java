package com.flaviolisboa.siaa.web.jsf.converters.negocio;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import com.flaviolisboa.siaa.negocio.logins.Login;
import com.flaviolisboa.siaa.negocio.logins.RepositorioLogin;
import com.flaviolisboa.siaa.util.entidades.Repositorio;
import com.flaviolisboa.siaa.web.jsf.converters.ConversorEntidadeAbstrato;

@Named
@ApplicationScoped
@FacesConverter(forClass = Login.class)
public class ConversorLogin extends ConversorEntidadeAbstrato<Login> {

	@Inject
	private RepositorioLogin repositorio;
	
	@Override
	protected Class<Login> getClasseEntidade() {
		return Login.class;
	}

	@Override
	protected Repositorio<Login> getRepositorioEntidade() {
		return repositorio;
	}

}
