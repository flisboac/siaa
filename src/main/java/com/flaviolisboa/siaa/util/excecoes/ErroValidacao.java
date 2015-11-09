
package com.flaviolisboa.siaa.util.excecoes;

import com.flaviolisboa.siaa.util.entidades.ResultadoValidacao;

public class ErroValidacao extends ErroNegocio {
	private static final long serialVersionUID = 1L;

	private final ResultadoValidacao resultadoValidacao;
	
	public ErroValidacao() {
		super();
		this.resultadoValidacao = null;
	}

	public ErroValidacao(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.resultadoValidacao = null;
	}

	public ErroValidacao(String message, Throwable cause) {
		super(message, cause);
		this.resultadoValidacao = null;
	}

	public ErroValidacao(String message) {
		super(message);
		this.resultadoValidacao = null;
	}

	public ErroValidacao(Throwable cause) {
		super(cause);
		this.resultadoValidacao = null;
	}

	public ErroValidacao(ResultadoValidacao validacao) {
		super(!validacao.isValido() ? validacao.getResumo() : "Ocorreu um erro de violação.");
		this.resultadoValidacao = null;
	}
	
	public ErroValidacao(ResultadoValidacao validacao, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(!validacao.isValido() ? validacao.getResumo() : "Ocorreu um erro de violação.",
				cause, enableSuppression, writableStackTrace);
		this.resultadoValidacao = validacao;
	}
	
	public ErroValidacao(ResultadoValidacao validacao, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.resultadoValidacao = validacao;
	}

	public ErroValidacao(ResultadoValidacao validacao, String message, Throwable cause) {
		super(message, cause);
		this.resultadoValidacao = validacao;
	}

	public ErroValidacao(ResultadoValidacao validacao, String message) {
		super(message);
		this.resultadoValidacao = validacao;
	}

	public ErroValidacao(ResultadoValidacao validacao, Throwable cause) {
		super(!validacao.isValido() ? validacao.getResumo() : "Ocorreu um erro de violação.");
		this.resultadoValidacao = validacao;
	}

	public ResultadoValidacao getResultadoValidacao() {
		return resultadoValidacao;
	}
	
}
