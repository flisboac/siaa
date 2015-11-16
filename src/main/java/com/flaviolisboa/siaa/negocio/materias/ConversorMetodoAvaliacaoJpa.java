package com.flaviolisboa.siaa.negocio.materias;

import com.flaviolisboa.siaa.util.entidades.impl.ConversorEnumeracaoJpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConversorMetodoAvaliacaoJpa
extends ConversorEnumeracaoJpa<MetodoAvaliacao, String>
implements AttributeConverter<MetodoAvaliacao, String> {

    public ConversorMetodoAvaliacaoJpa() {
        super(MetodoAvaliacao.class, String.class);
    }
}
