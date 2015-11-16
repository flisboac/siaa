package com.flaviolisboa.siaa.negocio.autenticacoes;

public enum TipoAutenticacao {

    SENHA(Valores.SENHA);
    
    private final String valor;

    private TipoAutenticacao(String valor) {
        this.valor = valor;
    }
    
    public static TipoAutenticacao buscarPorValor(String valor) {
        for (TipoAutenticacao elem : values()) {
            if (elem.valor.compareTo(valor) == 0) {
                return elem;
            }
        }
        return null;
    }
    
    public static final class Valores {
        
        public static final String SENHA = "SNH";
    }
}
