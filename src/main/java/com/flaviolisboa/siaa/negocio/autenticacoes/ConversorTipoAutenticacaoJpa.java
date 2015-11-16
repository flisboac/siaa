package com.flaviolisboa.siaa.negocio.autenticacoes;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.flaviolisboa.siaa.util.entidades.impl.ConversorEnumeracaoJpa;

@Converter(autoApply = true)
public class ConversorTipoAutenticacaoJpa
extends ConversorEnumeracaoJpa<TipoAutenticacao, String>
implements AttributeConverter<TipoAutenticacao, String> {

    public ConversorTipoAutenticacaoJpa() {
        super(TipoAutenticacao.class, String.class);
    }
}
