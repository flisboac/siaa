
package com.flaviolisboa.siaa.util.excecoes;

public class ErroEntidadeInexistente extends ErroPersistencia {

    public ErroEntidadeInexistente() {
    }

    public ErroEntidadeInexistente(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ErroEntidadeInexistente(String message, Throwable cause) {
        super(message, cause);
    }

    public ErroEntidadeInexistente(String message) {
        super(message);
    }

    public ErroEntidadeInexistente(Throwable cause) {
        super(cause);
    }

}
