
package com.flaviolisboa.siaa.negocio.pessoas;

import com.flaviolisboa.siaa.util.entidades.Entidade;
import javax.persistence.Entity;
import javax.persistence.Id;

// TODO Mapeamentos do  JPA e validacoes
@Entity
public class Pessoa implements Entidade {

    @Id
    private Long id;
    
    private String nome;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isIdentificado() {
        return id != null;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
