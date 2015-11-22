
package com.flaviolisboa.siaa.util.entidades;

import java.util.List;

import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;

public interface Validador<T extends Entidade> extends Servico<T> {

	<TT extends T> ResultadoValidacao validar(TT entidade, Class<?>... aspectos);
	<TT extends T> ResultadoValidacao validar(TT entidade, Object propriedade, Class<?>... aspectos);
	<TT extends T> void validarOuFalhar(TT entidade, Class<?>... aspectos) throws ErroValidacao;
	<TT extends T> void validarOuFalhar(TT entidade, Object propriedade, Class<?>... aspectos) throws ErroValidacao;
    
	<TT extends T> ResultadoValidacao validar(TT entidade, List<Class<?>> aspectos);
	<TT extends T> ResultadoValidacao validar(TT entidade, Object propriedade, List<Class<?>> aspectos);
	<TT extends T> void validarOuFalhar(TT entidade, List<Class<?>> aspectos) throws ErroValidacao;
	<TT extends T> void validarOuFalhar(TT entidade, Object propriedade, List<Class<?>> aspectos) throws ErroValidacao;
}
