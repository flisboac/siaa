package com.flaviolisboa.siaa.negocio.perfis;

public enum TipoPerfil {

    ALUNO(Valores.ALUNO),
    PROFESSOR(Valores.PROFESSOR);
    
    private final String valor;

    private TipoPerfil(String valor) {
        this.valor = valor;
    }
    
    public static TipoPerfil buscarPorValor(String valor) {
        for (TipoPerfil elem : values()) {
            if (elem.valor.compareTo(valor) == 0) {
                return elem;
            }
        }
        return null;
    }
    
    public static final class Valores {
        
        public static final String ALUNO = "A";
        public static final String PROFESSOR = "F";
    }
}
