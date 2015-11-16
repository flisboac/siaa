package com.flaviolisboa.siaa.negocio.logins;

import com.flaviolisboa.siaa.util.entidades.Repositorio;

public interface RepositorioLogin extends Repositorio<Login> {

	public Login buscarAlgum(String nome);
}
