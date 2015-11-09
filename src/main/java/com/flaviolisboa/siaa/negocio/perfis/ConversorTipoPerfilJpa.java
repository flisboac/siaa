package com.flaviolisboa.siaa.negocio.perfis;

import com.flaviolisboa.siaa.util.entidades.impl.ConversorEnumeracaoJpa;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConversorTipoPerfilJpa extends ConversorEnumeracaoJpa<TipoPerfil, String>{

    public ConversorTipoPerfilJpa() {
        super(TipoPerfil.class, String.class);
    }
}
