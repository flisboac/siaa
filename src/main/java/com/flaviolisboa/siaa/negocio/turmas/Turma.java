package com.flaviolisboa.siaa.negocio.turmas;

import com.flaviolisboa.siaa.negocio.perfis.PerfilProfessor;
import com.flaviolisboa.siaa.util.entidades.Entidade;
import java.util.List;

public class Turma implements Entidade {
    
    private Long id;
    
    private String codigo;
    
    private PerfilProfessor professor;
    
    private List<Avaliacao> avaliacoes;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public boolean isIdentificado() {
        return id != null;
    }
}
