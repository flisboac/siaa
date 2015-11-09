package com.flaviolisboa.siaa.util.entidades;

import java.util.Collection;
import java.util.List;

public interface Repositorio<T extends Entidade> {

	<TT extends T> TT salvar(TT entidade);
	<TT extends T> void excluir(TT entidade);
	
    Pagina paginar(Paginacao paginacao, NavegacaoPagina navegacao, Integer indice);
    Pagina navegar(NavegacaoPagina navegacao, Pagina pagina, Integer indice);
    List<T> buscarTodos(Pagina pagina);
    
	Collection<T> buscarTodos();    
	T buscarAlgum(Object id);
	<TT extends T> TT buscarAlgum(TT entidade);
	T buscar(Object id);
	<TT extends T> TT buscar(TT entidade);
	Number contar();
	<TT extends T> boolean existe(TT entidade);
}
