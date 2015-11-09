
package com.flaviolisboa.siaa.negocio.perfis;

import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "perfil_funcionario", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "id_pessoa" }, name = "uq_perfil_funcionario"),
    @UniqueConstraint(columnNames = { "matricula" }, name = "uq_perfil_funcionario_matricula")
}, indexes = {
    @Index(columnList = "id_pessoa", name = "ix_perfil_funcionario_id_pessoa")
})
@GroupSequence({ Identificacao.class, Identidade.class, Integridade.class })
public class PerfilFuncionario extends Perfil {
    
    @NotNull(message = "Usuario deve possuir uma matrícula.", groups = Integridade.class)
    @Column(name = "matricula", length = 50, nullable = false)
    private String matricula;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
        
}
