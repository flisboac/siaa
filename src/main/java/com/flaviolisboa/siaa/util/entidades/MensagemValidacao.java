
package com.flaviolisboa.siaa.util.entidades;

import java.util.List;

public interface MensagemValidacao {

    Object getCodigo();
	Object getPropriedade();
    String getMensagem();
	List<String> getDetalhes();
}
