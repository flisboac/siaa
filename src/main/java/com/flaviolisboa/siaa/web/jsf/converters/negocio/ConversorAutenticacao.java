package com.flaviolisboa.siaa.web.jsf.converters.negocio;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import com.flaviolisboa.siaa.negocio.autenticacoes.Autenticacao;
import com.flaviolisboa.siaa.negocio.autenticacoes.RepositorioAutenticacao;
import com.flaviolisboa.siaa.util.entidades.Repositorio;
import com.flaviolisboa.siaa.web.jsf.converters.ConversorEntidadeAbstrato;

@Named
@ApplicationScoped
@FacesConverter(forClass = Autenticacao.class)
public class ConversorAutenticacao extends ConversorEntidadeAbstrato<Autenticacao> {

	@Inject
	private RepositorioAutenticacao repositorio;
	
	@Override
	protected Class<Autenticacao> getClasseEntidade() {
		return Autenticacao.class;
	}

	@Override
	protected Repositorio<Autenticacao> getRepositorioEntidade() {
		return repositorio;
	}

}
