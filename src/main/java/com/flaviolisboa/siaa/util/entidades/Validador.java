
package com.flaviolisboa.siaa.util.entidades;

import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import java.util.List;

public interface Validador<T extends Entidade> {

	<TT extends T> ResultadoValidacao validar(TT entidade, Class<?>... aspectos);
	<TT extends T> ResultadoValidacao validar(TT entidade, Object propriedade, Class<?>... aspectos);
	<TT extends T> void validarOuFalhar(TT entidade, Class<?>... aspectos) throws ErroValidacao;
	<TT extends T> void validarOuFalhar(TT entidade, Object propriedade, Class<?>... aspectos) throws ErroValidacao;
    
	<TT extends T> ResultadoValidacao validar(TT entidade, List<Class<?>> aspectos);
	<TT extends T> ResultadoValidacao validar(TT entidade, Object propriedade, List<Class<?>> aspectos);
	<TT extends T> void validarOuFalhar(TT entidade, List<Class<?>> aspectos) throws ErroValidacao;
	<TT extends T> void validarOuFalhar(TT entidade, Object propriedade, List<Class<?>> aspectos) throws ErroValidacao;
}
