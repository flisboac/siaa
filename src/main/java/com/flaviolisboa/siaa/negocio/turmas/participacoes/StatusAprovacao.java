package com.flaviolisboa.siaa.negocio.turmas.participacoes;

public enum StatusAprovacao {

    CURSANDO(Valores.CURSANDO),
    APROVADO(Valores.APROVADO),
    REPROVADO(Valores.REPROVADO),
    AUSENTE(Valores.AUSENTE);
    
    private final String valor;

    private StatusAprovacao(String valor) {
        this.valor = valor;
    }
    
    public static StatusAprovacao buscarPorValor(String valor) {
        for (StatusAprovacao elem : values()) {
            if (elem.valor.compareTo(valor) == 0) {
                return elem;
            }
        }
        return null;
    }

    public static final class Valores {
        public static final String CURSANDO = "CRS";
        public static final String APROVADO = "APR";
        public static final String REPROVADO = "REP";
        public static final String AUSENTE = "AUS";
    }
}
