package com.flaviolisboa.siaa.negocio.autenticacoes;

import com.flaviolisboa.siaa.util.entidades.impl.ConversorEnumeracaoJpa;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConversorTipoAutenticacaoJpa extends ConversorEnumeracaoJpa<TipoAutenticacao, String>{

    public ConversorTipoAutenticacaoJpa() {
        super(TipoAutenticacao.class, String.class);
    }
}
