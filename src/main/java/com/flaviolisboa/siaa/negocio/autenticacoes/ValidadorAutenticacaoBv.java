package com.flaviolisboa.siaa.negocio.autenticacoes;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.flaviolisboa.siaa.util.entidades.impl.ValidadorBv;

@Stateless(name = "ValidadorAutenticacao")
@Local(ValidadorAutenticacao.class)
public class ValidadorAutenticacaoBv extends ValidadorBv<Autenticacao> implements ValidadorAutenticacao {

}
