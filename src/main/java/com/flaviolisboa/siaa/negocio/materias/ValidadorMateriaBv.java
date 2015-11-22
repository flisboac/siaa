package com.flaviolisboa.siaa.negocio.materias;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.flaviolisboa.siaa.util.entidades.impl.ValidadorBv;

@Stateless(name = "ValidadorMateria")
@Local(ValidadorMateria.class)
public class ValidadorMateriaBv extends ValidadorBv<Materia> implements ValidadorMateria {

}
