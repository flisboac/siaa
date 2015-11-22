package com.flaviolisboa.siaa.negocio.perfis;

public enum TipoPerfil {

    ALUNO(Valores.ALUNO, PerfilAluno.class),
    PROFESSOR(Valores.PROFESSOR, PerfilProfessor.class),
    COORDENADOR(Valores.COORDENADOR, PerfilCoordenador.class);
    
    private final String valor;
    private final Class<? extends Perfil> classeEntidade;

    private TipoPerfil(String valor, Class<? extends Perfil> classeEntidade) {
        this.valor = valor;
        this.classeEntidade = classeEntidade;
    }
    
    public String getValor() {
		return valor;
	}

	public Class<? extends Perfil> getClasseEntidade() {
		return classeEntidade;
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
        public static final String PROFESSOR = "P";
        public static final String COORDENADOR = "C";
    }
}
