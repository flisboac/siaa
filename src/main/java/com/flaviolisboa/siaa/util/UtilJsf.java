package com.flaviolisboa.siaa.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class UtilJsf {

	public static void mensagem(FacesMessage.Severity severidade, String id, String titulo, String detalhes) {
		FacesMessage mensagem = new FacesMessage(severidade, titulo, detalhes);
		FacesContext.getCurrentInstance().addMessage(id, mensagem);
	}

	public static void info(String id, String titulo, String detalhes) {
		mensagem(FacesMessage.SEVERITY_INFO, id, titulo, detalhes);
	}

	public static void aviso(String id, String titulo, String detalhes) {
		mensagem(FacesMessage.SEVERITY_WARN, id, titulo, detalhes);
	}
	
	public static void erro(String id, String titulo, String detalhes) {
		mensagem(FacesMessage.SEVERITY_ERROR, id, titulo, detalhes);
	}

	public static void fatal(String id, String titulo, String detalhes) {
		mensagem(FacesMessage.SEVERITY_FATAL, id, titulo, detalhes);
	}
}
