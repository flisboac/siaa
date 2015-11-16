package com.flaviolisboa.siaa.negocio.autenticacoes;

import java.util.List;

import com.flaviolisboa.siaa.util.entidades.Repositorio;

public interface RepositorioAutenticacao extends Repositorio<Autenticacao> {

	public <E extends Autenticacao> List<E> buscarTodos(Class<E> classeAutenticacao);
}
