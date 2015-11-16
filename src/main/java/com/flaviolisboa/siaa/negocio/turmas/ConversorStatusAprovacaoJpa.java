package com.flaviolisboa.siaa.negocio.turmas;

import com.flaviolisboa.siaa.negocio.turmas.participacoes.StatusAprovacao;
import com.flaviolisboa.siaa.util.entidades.impl.ConversorEnumeracaoJpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConversorStatusAprovacaoJpa
extends ConversorEnumeracaoJpa<StatusAprovacao, String>
implements AttributeConverter<StatusAprovacao, String> {

    public ConversorStatusAprovacaoJpa() {
        super(StatusAprovacao.class, String.class);
    }
}
