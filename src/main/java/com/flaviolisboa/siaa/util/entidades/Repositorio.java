package com.flaviolisboa.siaa.util.entidades;

import java.util.List;

public interface Repositorio<T extends Entidade> extends Servico<T> {

	<TT extends T> TT salvar(TT entidade);
	<TT extends T> void excluir(TT entidade);
	
	List<T> buscarTodos();    
	T buscarAlgum(Object id);
	<TT extends T> TT buscarAlgum(TT entidade);
	T buscar(Object id);
	<TT extends T> TT buscar(TT entidade);
	Number contar();
	<TT extends T> boolean existe(TT entidade);
}
