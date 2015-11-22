package com.flaviolisboa.siaa.util;

import java.util.Collection;
import java.util.Iterator;

import com.flaviolisboa.siaa.util.excecoes.ErroEntidadeInexistente;
import com.flaviolisboa.siaa.util.excecoes.ErroMultiplasEntidades;

public class UtilEntidades {

	public static <T> T buscar(Collection<T> colecao) throws ErroEntidadeInexistente, ErroMultiplasEntidades {
		if (colecao.isEmpty()) {
			throw new ErroEntidadeInexistente();
		}
		return buscarAlgum(colecao);
	}
	
	public static <T> T buscarAlgum(Collection<T> colecao) throws ErroMultiplasEntidades {
		if (colecao.size() > 1) {
			throw new ErroMultiplasEntidades();
		}
		Iterator<T> iterador = colecao.iterator();
		if (iterador.hasNext()) {
			return iterador.next();
		}
		return null;
	}
}
