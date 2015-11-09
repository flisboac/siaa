
package com.flaviolisboa.siaa.util.entidades.impl;

public class ConversorEnumeracaoJpa<E extends Enum<?>, T> extends ConversorEnumeracaoJpa2_1<E, T> {

    public ConversorEnumeracaoJpa() {
    }

    public ConversorEnumeracaoJpa(Class<E> classeEnumeracao, Class<T> classeValor) {
        super(classeEnumeracao, classeValor);
    }

}
