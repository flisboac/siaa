package com.flaviolisboa.siaa.util;

import java.util.List;

import javax.persistence.TypedQuery;

public class UtilJpa {

	public static <T> T buscarAlgum(TypedQuery<T> query) {
		
		query.setMaxResults(2);
		List<T> lista = query.getResultList();
		return UtilEntidades.buscarAlgum(lista);
	}
	
	public static <T> T buscar(TypedQuery<T> query) {
		
		query.setMaxResults(2);
		List<T> lista = query.getResultList();
		return UtilEntidades.buscar(lista);
	}
	
}
