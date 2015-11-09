package com.flaviolisboa.siaa.negocio.perfis;

import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue(TipoPerfil.Valores.ALUNO)
@Table(name = "perfil_aluno", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "id_pessoa" }, name = "uq_perfil_aluno"),
    @UniqueConstraint(columnNames = { "matricula" }, name = "uq_perfil_aluno_matricula")
}, indexes = {
    @Index(columnList = "id_pessoa", name = "ix_perfil_aluno_id_pessoa")
})
@GroupSequence({ Identificacao.class, Identidade.class, Integridade.class })
public class PerfilAluno extends Perfil {
    
    @NotNull(message = "Aluno deve possuir uma matrícula.", groups = Integridade.class)
    @Column(name = "matricula", length = 50, nullable = false)
    private String matricula;
    
}
