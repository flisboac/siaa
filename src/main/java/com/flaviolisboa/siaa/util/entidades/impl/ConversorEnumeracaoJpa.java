
package com.flaviolisboa.siaa.util.entidades.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.AttributeConverter;

public class ConversorEnumeracaoJpa<E extends Enum<?>, T>  implements AttributeConverter<E, T> 	{

    private Class<E> classeEnumeracao;
    private Class<T> classeValor;
    
    public ConversorEnumeracaoJpa() {
    }

    public ConversorEnumeracaoJpa(Class<E> classeEnumeracao, Class<T> classeValor) {
        this.classeEnumeracao = classeEnumeracao;
        this.classeValor = classeValor;
    }

    @SuppressWarnings("unchecked")
	@Override
    public T convertToDatabaseColumn(E attribute) {
        // TODO melhorar busca de valor (e.g. adicionar opções, usar anotações)
        Method metodo;
        try {
            metodo = classeEnumeracao.getDeclaredMethod("getValor");
            return (T) metodo.invoke(attribute);
            
        } catch (NoSuchMethodException
                | SecurityException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public E convertToEntityAttribute(T dbData) {
        // TODO melhorar busca de valor (e.g. adicionar opções, usar anotações)
        Method metodo;
        try {
            metodo = classeEnumeracao.getDeclaredMethod("buscarPorValor", classeValor);
            return (E) metodo.invoke(null, dbData);
            
        } catch (NoSuchMethodException
                | SecurityException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
}
