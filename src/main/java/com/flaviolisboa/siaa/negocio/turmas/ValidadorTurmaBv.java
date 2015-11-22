package com.flaviolisboa.siaa.negocio.turmas;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.flaviolisboa.siaa.util.entidades.impl.ValidadorBv;

@Stateless(name = "ValidadorTurma")
@Local(ValidadorTurma.class)
public class ValidadorTurmaBv extends ValidadorBv<Turma> implements ValidadorTurma {

}
