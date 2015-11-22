package com.flaviolisboa.siaa.web;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.flaviolisboa.siaa.negocio.perfis.Perfil;

@Named
@SessionScoped
public class SessaoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private AtomicReference<Perfil> perfil;
	private AtomicReference<String> navegacao;

	@Produces
	@Named("perfil")
	public Perfil getPerfil() {
		return perfil.get();
	}

	public void setPerfil(Perfil perfil) {
		this.perfil.set(perfil);
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
	}
	
	public String proximaNavegacao(String proxima) {
		return this.navegacao.getAndSet(proxima);
	}
	
	@Produces
	@Named("proximaNavegacao")
	public String proximaNavegacao() {
		return this.navegacao.getAndSet(null);
	}
	
	public boolean hasProximaNavegacao() {
		return this.navegacao.get() != null;
	}
}
