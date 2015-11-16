package com.flaviolisboa.siaa.negocio.perfis;

import com.flaviolisboa.siaa.util.entidades.impl.ConversorEnumeracaoJpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConversorTipoPerfilJpa
extends ConversorEnumeracaoJpa<TipoPerfil, String>
implements AttributeConverter<TipoPerfil, String> {

    public ConversorTipoPerfilJpa() {
        super(TipoPerfil.class, String.class);
    }
}
