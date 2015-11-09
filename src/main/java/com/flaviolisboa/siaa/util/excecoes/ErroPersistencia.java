
package com.flaviolisboa.siaa.util.excecoes;

public class ErroPersistencia extends ErroNegocio {
    private static final long serialVersionUID = 1L;

	public ErroPersistencia() {
		super();
	}

	public ErroPersistencia(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ErroPersistencia(String message, Throwable cause) {
		super(message, cause);
	}

	public ErroPersistencia(String message) {
		super(message);
	}

	public ErroPersistencia(Throwable cause) {
		super(cause);
	}
}
