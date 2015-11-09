
package com.flaviolisboa.siaa.util.entidades;

import java.util.List;

public interface ResultadoValidacao {

	boolean isValido();
	String getResumo();
	List<MensagemValidacao> getMensagens();
}
