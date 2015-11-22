package com.flaviolisboa.siaa.negocio.pessoas;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.flaviolisboa.siaa.util.entidades.impl.ValidadorBv;

@Stateless(name = "ValidadorPessoa")
@Local(ValidadorPessoa.class)
public class ValidadorPessoaBv extends ValidadorBv<Pessoa> implements ValidadorPessoa {

}
