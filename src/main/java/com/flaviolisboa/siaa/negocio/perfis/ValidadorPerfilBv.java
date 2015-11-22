package com.flaviolisboa.siaa.negocio.perfis;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.flaviolisboa.siaa.util.entidades.impl.ValidadorBv;

@Stateless(name = "ValidadorPerfil")
@Local(ValidadorPerfil.class)
public class ValidadorPerfilBv extends ValidadorBv<Perfil> implements ValidadorPerfil {

}
