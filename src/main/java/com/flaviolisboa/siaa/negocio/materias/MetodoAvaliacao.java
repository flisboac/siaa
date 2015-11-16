package com.flaviolisboa.siaa.negocio.materias;

public enum MetodoAvaliacao {

    /// A1 + max(A2, A3) / 2 >= 6 && A1 >= 5 && max(A2, A3) >= 5
    A3(Valores.A3, "A3 (Padrão)"),
    /// A4 >= 6
    A4(Valores.A4, "A4 (Nota Única)"),
    /// (A1 + 2*A2 + A3) / 4 == A4 >= 6
    T4(Valores.T4, "A4 (Avaliações Fixas)");
    
    private final String valor;
    private final String nome;

    private MetodoAvaliacao(String valor, String nome) {
        this.valor = valor;
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public String getNome() {
        return nome;
    }
    
    public static MetodoAvaliacao buscarPorValor(String valor) {
        for (MetodoAvaliacao elem : values()) {
            if (elem.valor.compareTo(valor) == 0) {
                return elem;
            }
        }
        return null;
    }
    
    public static final class Valores {
        
        public static final String A3 = "A3";
        public static final String A4 = "A4";
        public static final String T4 = "T4";
    }
}
