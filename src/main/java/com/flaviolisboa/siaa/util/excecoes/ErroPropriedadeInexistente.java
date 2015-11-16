
package com.flaviolisboa.siaa.util.excecoes;

public class ErroPropriedadeInexistente extends ErroPersistencia {
	private static final long serialVersionUID = 1L;

    public ErroPropriedadeInexistente() {
    }

    public ErroPropriedadeInexistente(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ErroPropriedadeInexistente(String message, Throwable cause) {
        super(message, cause);
    }

    public ErroPropriedadeInexistente(String message) {
        super(message);
    }

    public ErroPropriedadeInexistente(Throwable cause) {
        super(cause);
    }

}
