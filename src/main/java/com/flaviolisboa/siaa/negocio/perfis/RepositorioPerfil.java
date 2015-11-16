
package com.flaviolisboa.siaa.negocio.perfis;

import com.flaviolisboa.siaa.util.entidades.Repositorio;

public interface RepositorioPerfil extends Repositorio<Perfil> {

	public <E extends Perfil> E buscarAlgum(Class<E> classePerfil, String identificacao);
}
