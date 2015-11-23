package com.flaviolisboa.siaa.web.jsf.converters.negocio;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import com.flaviolisboa.siaa.negocio.pessoas.Pessoa;
import com.flaviolisboa.siaa.negocio.pessoas.RepositorioPessoa;
import com.flaviolisboa.siaa.util.entidades.Repositorio;
import com.flaviolisboa.siaa.web.jsf.converters.ConversorEntidadeAbstrato;

@Named
@ApplicationScoped
@FacesConverter(forClass = Pessoa.class)
public class ConversorPessoa extends ConversorEntidadeAbstrato<Pessoa> {

	@Inject
	private RepositorioPessoa repositorio;
	
	@Override
	protected Class<Pessoa> getClasseEntidade() {
		return Pessoa.class;
	}

	@Override
	protected Repositorio<Pessoa> getRepositorioEntidade() {
		return repositorio;
	}

}
