
package com.flaviolisboa.siaa.util.excecoes;

public class ErroEstadoEntidadeInvalido extends ErroPersistencia {

    public ErroEstadoEntidadeInvalido() {
    }

    public ErroEstadoEntidadeInvalido(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ErroEstadoEntidadeInvalido(String message, Throwable cause) {
        super(message, cause);
    }

    public ErroEstadoEntidadeInvalido(String message) {
        super(message);
    }

    public ErroEstadoEntidadeInvalido(Throwable cause) {
        super(cause);
    }

}
