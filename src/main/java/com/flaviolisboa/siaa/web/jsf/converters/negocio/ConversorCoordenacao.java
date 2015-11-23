package com.flaviolisboa.siaa.web.jsf.converters.negocio;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import com.flaviolisboa.siaa.negocio.coordenacoes.Coordenacao;
import com.flaviolisboa.siaa.negocio.coordenacoes.RepositorioCoordenacao;
import com.flaviolisboa.siaa.util.entidades.Repositorio;
import com.flaviolisboa.siaa.web.jsf.converters.ConversorEntidadeAbstrato;

@Named
@ApplicationScoped
@FacesConverter(forClass = Coordenacao.class)
public class ConversorCoordenacao extends ConversorEntidadeAbstrato<Coordenacao> {

	@Inject
	private RepositorioCoordenacao repositorio;
	
	@Override
	protected Class<Coordenacao> getClasseEntidade() {
		return Coordenacao.class;
	}

	@Override
	protected Repositorio<Coordenacao> getRepositorioEntidade() {
		return repositorio;
	}

}
