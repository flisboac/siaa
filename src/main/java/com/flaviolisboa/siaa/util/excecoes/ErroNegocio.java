
package com.flaviolisboa.siaa.util.excecoes;

public class ErroNegocio extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public ErroNegocio() {
		super();
	}

	public ErroNegocio(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ErroNegocio(String message, Throwable cause) {
		super(message, cause);
	}

	public ErroNegocio(String message) {
		super(message);
	}

	public ErroNegocio(Throwable cause) {
		super(cause);
	}
}
