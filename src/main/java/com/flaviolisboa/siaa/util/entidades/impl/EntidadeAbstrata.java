package com.flaviolisboa.siaa.util.entidades.impl;

import com.flaviolisboa.siaa.util.entidades.Entidade;

public abstract class EntidadeAbstrata<T> implements Entidade {

    @Override
    public abstract T getId();
    
    @Override
    public boolean isIdentificado() {
        return getId() != null;
    }
    
    @Override
    public int hashCode() {
        int ret;
        if (isIdentificado()) {
            ret = getId().hashCode();
        } else {
            ret = super.hashCode();
        }
        return ret;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (!obj.getClass().isAssignableFrom(Entidade.class)) {
            return false;
        }
        final Entidade other = (Entidade) obj;
        if (isIdentificado() && other.isIdentificado()) {
            if (!this.getId().equals(other.getId())) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
