package com.flaviolisboa.siaa.negocio.coordenacoes;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.flaviolisboa.siaa.util.entidades.impl.ValidadorBv;

@Stateless(name = "ValidadorCoordenacao")
@Local(ValidadorCoordenacao.class)
public class ValidadorCoordenacaoBv extends ValidadorBv<Coordenacao> implements ValidadorCoordenacao {

}
