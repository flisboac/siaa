package com.flaviolisboa.siaa.negocio.turmas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.flaviolisboa.siaa.negocio.materias.Materia;
import com.flaviolisboa.siaa.negocio.perfis.PerfilProfessor;
import com.flaviolisboa.siaa.negocio.turmas.participacoes.ParticipacaoAluno;
import com.flaviolisboa.siaa.util.entidades.impl.EntidadeAbstrata;
import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;

@Entity
@Table(name = "turma")
@SequenceGenerator(name = "sq_turma", sequenceName = "sq_turma", allocationSize = 1, initialValue = 1)
@GroupSequence({ Identidade.class, Integridade.class })
public class Turma extends EntidadeAbstrata<Long> implements Serializable {
	private static final long serialVersionUID = 1L;
    
    public static final int TAMANHO_CODIGO = 64;
    public static final String PADRAO_CODIGO = "[a-zA-Z][a-zA-Z0-9_-]*";
    public static final int MAXIMO_ALUNOS = 128;

    @NotNull(message = "ID não pode ser nulo.", groups = Identificacao.class)
    @Id
    @GeneratedValue(generator = "sq_turma", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    
    @NotNull(message = "Matéria deve pertencer a uma turma.", groups = Integridade.class)
    @Valid
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_materia", referencedColumnName = "id")
    private Materia materia;
    
    @NotNull(message = "Turma deve possuir um código.", groups = Identidade.class)
    @Size(min = 1, max = TAMANHO_CODIGO, message = "Código de turma deve estar entre {min} e {max} caracter(es).", groups = Integridade.class)
    @Pattern(regexp = PADRAO_CODIGO, message = "Código de turma deve ter padrão '{regexp}'.", groups = Identidade.class)
    @Column(name = "codigo")
    private String codigo;
    
    @NotNull(message = "Data de criação da turma deve ser especificada.", groups = Integridade.class)
    @Past(message = "Data de criação da turma deve estar no passado.", groups = Integridade.class)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao")
    private Date dataCriacao;
    
    @NotNull(message = "Número máximo de alunos deve ser especificado para a turma.", groups = Integridade.class)
    @Min(value = 1, message = "Turma deve comportar no mínimo {value} aluno(s).", groups = Integridade.class)
    @Max(value = MAXIMO_ALUNOS, message = "Capacidade da turma não deve ultrapassar {value} aluno(s).", groups = Integridade.class)
    @Column(name = "numero_maximo_alunos")
    private Integer numeroMaximoAlunos;
    
    @ManyToOne
    @JoinColumn(name = "id_professor_responsavel", referencedColumnName = "id")
    private PerfilProfessor professorResponsavel;
    
    @OneToMany(mappedBy = "turma")
    private List<ParticipacaoAluno> alunosParticipantes;

    public Turma() {
    }

    public Turma(Long id) {
        this.id = id;
    }

    public Turma(Materia materia) {
        this.materia = materia;
        this.dataCriacao = new Date();
        this.alunosParticipantes = new ArrayList<>();
    }
    
    public Turma(Turma turma, String codigo) {
        this.materia = turma.materia;
        this.dataCriacao = turma.dataCriacao;
        this.numeroMaximoAlunos = turma.numeroMaximoAlunos;
        this.professorResponsavel = turma.professorResponsavel;
        this.alunosParticipantes.addAll(turma.alunosParticipantes);
        this.codigo = codigo;
    }
    
    @Override
    public Long getId() {
        return id;
    }

    public Materia getMateria() {
        return materia;
    }

    public String getCodigo() {
        return codigo;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public List<ParticipacaoAluno> getAlunosParticipantes() {
        return alunosParticipantes;
    }

    public Integer getNumeroMaximoAlunos() {
        return numeroMaximoAlunos;
    }

    public void setNumeroMaximoAlunos(int numeroMaximoAlunos) {
        if (numeroMaximoAlunos <= 0) {
            // TODO Melhorar mensagem de validação
            throw new ErroValidacao();
        }
        this.numeroMaximoAlunos = numeroMaximoAlunos;
    }

    public PerfilProfessor getProfessorResponsavel() {
        return professorResponsavel;
    }

    public void setProfessorResponsavel(PerfilProfessor professorResponsavel) {
        if (professorResponsavel == null
                || !professorResponsavel.isIdentificado()) {
            // TODO Melhorar mensagem de validação
            throw new ErroValidacao();
        }
        this.professorResponsavel = professorResponsavel;
    }
    
}
