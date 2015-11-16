package com.flaviolisboa.siaa.negocio.turmas;

import com.flaviolisboa.siaa.util.entidades.Repositorio;

public interface RepositorioTurma extends Repositorio<Turma> {

	@Override 
	public <TT extends Turma> TT salvar(TT turma);
}
