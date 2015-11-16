package com.flaviolisboa.siaa.negocio.perfis;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.UniqueConstraint;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.flaviolisboa.siaa.negocio.pessoas.Pessoa;
import com.flaviolisboa.siaa.negocio.turmas.participacoes.ParticipacaoAluno;
import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;

@Entity
@SecondaryTable(name = "perfil_aluno", uniqueConstraints = {
	    @UniqueConstraint(columnNames = { "matricula" }, name = "uq_perfil_aluno_matricula")
}, pkJoinColumns = {
		@PrimaryKeyJoinColumn(name = "id_perfil", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_perfil_aluno_id_perfil"))
})
@DiscriminatorValue(TipoPerfil.Valores.ALUNO)
@GroupSequence({ Identidade.class, Integridade.class })
public class PerfilAluno extends Perfil {
	private static final long serialVersionUID = 1L;	
    
	public static final int TAMANHO_MATRICULA = 10;
	public static final String PADRAO_MATRICULA = "[0-9]{10}";
		
	@NotNull(message = "Perfil de aluno deve possuir uma matrícula.", groups = Integridade.class)
	@Size(min = TAMANHO_MATRICULA, max = TAMANHO_MATRICULA, message = "Matrícula para perfil de aluno deve possuir exatamente {max} caracter(es).", groups = Integridade.class)
	@Pattern(regexp = PADRAO_MATRICULA, message = "Matrícula para perfil de aluno deve consistir apenas por números.", groups = Integridade.class)
    @Column(name = "matricula", length = 10, nullable = false, table = "perfil_aluno")
    private String matricula;

	@OneToMany(mappedBy = "aluno")
	private List<ParticipacaoAluno> participacoesEmTurmas;
	
    public PerfilAluno() {
    }

    public PerfilAluno(Long id) {
        super(id);
    }
    
    public PerfilAluno(Pessoa pessoa, String matricula) {
        super(TipoPerfil.ALUNO, pessoa);
        if (matricula == null || matricula.length() != 10) {
            // TODO Melhorar mensagem de validação
            throw new ErroValidacao();
        }
        this.matricula = matricula;
    }
    
    public String getMatricula() {
        return matricula;
    }
}
