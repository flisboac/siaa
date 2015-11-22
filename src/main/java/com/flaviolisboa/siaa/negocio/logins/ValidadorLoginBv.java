package com.flaviolisboa.siaa.negocio.logins;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.flaviolisboa.siaa.util.entidades.impl.ValidadorBv;

@Stateless(name = "ValidadorLogin")
@Local(ValidadorLogin.class)
public class ValidadorLoginBv extends ValidadorBv<Login> implements ValidadorLogin {

}
