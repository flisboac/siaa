package com.flaviolisboa.siaa.util.entidades;

import com.flaviolisboa.siaa.util.UtilPropriedades;

public class Ordenacao {

    private Object propriedade;
    private Ordem ordem = Ordem.NATURAL;

    public Object getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(Object propriedade) {
        this.propriedade = propriedade;
    }

    public Ordem getOrdem() {
        return ordem;
    }

    public void setOrdem(Ordem ordem) {
        this.ordem = ordem;
    }
    
    public String getNomePropriedade() {
        return propriedade != null ? UtilPropriedades.getNomePropriedadeBean(propriedade) : null;
    }
    
    public boolean isValido() {
        boolean valido = propriedade != null && ordem != null;
        return valido;
    }
    
    public static Ordenacao por(Object propriedade) {
        Ordenacao ordenacao = new Ordenacao();
        ordenacao.setPropriedade(propriedade);
        return ordenacao;
    }
    
    public static Ordenacao por(Object propriedade, Ordem ordem) {
        Ordenacao ordenacao = new Ordenacao();
        ordenacao.setPropriedade(propriedade);
        ordenacao.setOrdem(ordem);
        return ordenacao;
    }
}
