package com.flaviolisboa.siaa.negocio.materias;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.flaviolisboa.siaa.util.entidades.impl.RepositorioJpa;

@Stateless(name = "RepositorioMateria")
@Local(RepositorioMateria.class)
public class RepositorioMateriaJpa extends RepositorioJpa<Materia> implements RepositorioMateria {

	@SuppressWarnings("unused")
	private ValidadorMateria validador;
	
	public RepositorioMateriaJpa() {
		super(Materia.class);
	}

	@EJB
	public void setValidador(ValidadorMateria validador) {
		this.validador = validador;
		super.setValidador(validador);
	}
}
