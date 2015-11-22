package com.flaviolisboa.siaa.negocio.turmas;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.flaviolisboa.siaa.negocio.materias.Materia;
import com.flaviolisboa.siaa.util.entidades.impl.RepositorioJpa;

@Stateless(name = "RepositorioTurma")
@Local(RepositorioTurma.class)
public class RepositorioTurmaJpa extends RepositorioJpa<Turma> implements RepositorioTurma {

	@SuppressWarnings("unused")
	private ValidadorTurma validador;
	
	public RepositorioTurmaJpa() {
		super(Turma.class);
	}

	@EJB
	public void setValidador(ValidadorTurma validador) {
		this.validador = validador;
		super.setValidador(validador);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E extends Turma> E salvar(E entidade) {
		Materia materia = entidade.getMateria();
		String codigo = materia.getCodigo() + "-" + (materia.getTurmas().size() + 1);
		Turma turma = new Turma(entidade, codigo);
		return (E) super.salvar(turma);
	}
}
