package com.flaviolisboa.siaa.web.jsf.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.flaviolisboa.siaa.util.entidades.Entidade;
import com.flaviolisboa.siaa.util.entidades.Repositorio;

public abstract class ConversorEntidadeAbstrato<T extends Entidade> implements Converter {
	
	protected abstract Class<T> getClasseEntidade();
	protected abstract Repositorio<T> getRepositorioEntidade();
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Object retorno = null;
		if (isIdValido(value)) {
			Object id = converterParaId(value);
			retorno = getRepositorioEntidade().buscar(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String retorno = null;
		if (value != null && getClasseEntidade().isInstance(value)) {
			@SuppressWarnings("unchecked")
			T entidade = (T) value;
			retorno = serializarId(entidade);
		}
		return retorno;
	}
	
	protected boolean isIdValido(String valor) {
		return valor != null && valor.matches("[0-9]+");
	}
	
	protected Object converterParaId(String valor) {
		return Long.valueOf(valor);
	}
	
	protected String serializarId(T entidade) {
		return entidade.getId().toString();
	}
}
