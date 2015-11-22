package com.flaviolisboa.siaa.util.excecoes;

public class ErroMultiplasEntidades extends ErroPersistencia {
	private static final long serialVersionUID = 1L;

	public ErroMultiplasEntidades() {
	}

	public ErroMultiplasEntidades(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ErroMultiplasEntidades(String message, Throwable cause) {
		super(message, cause);
	}

	public ErroMultiplasEntidades(String message) {
		super(message);
	}

	public ErroMultiplasEntidades(Throwable cause) {
		super(cause);
	}

}
