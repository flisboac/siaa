package com.flaviolisboa.siaa.negocio.materias;

import java.io.Serializable;
import java.util.Collections;
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
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.flaviolisboa.siaa.negocio.coordenacoes.Coordenacao;
import com.flaviolisboa.siaa.negocio.turmas.Turma;
import com.flaviolisboa.siaa.util.entidades.impl.EntidadeAbstrata;
import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;

@Entity
@Table(name = "materia")
@SequenceGenerator(name = "sq_materia", sequenceName = "sq_materia", allocationSize = 1, initialValue = 1)
@GroupSequence({ Identidade.class, Integridade.class })
public class Materia extends EntidadeAbstrata<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int TAMANHO_CODIGO = 32;
	public static final int TAMANHO_NOME = 64;
	public static final String PADRAO_CODIGO = "[a-zA-Z][a-zA-Z0-9]{1,}";
	public static final String PADRAO_NOME = ".*\\S.*";
	
    @NotNull(message = "ID não pode ser nulo.", groups = Identificacao.class)
    @Id
    @GeneratedValue(generator = "sq_materia", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    
    @NotNull(message = "Matéria deve possuir uma coordenação de origem.", groups = Identidade.class)
    @Valid
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_coordenacao_origem", referencedColumnName = "id")
    private Coordenacao coordenacaoOrigem;
    
    @NotNull(message = "Código da matéria deve ser fornecido.", groups = Identidade.class)
    @Size(min = 3, max = TAMANHO_CODIGO, message = "Código da matéria deve estar entre {min} e {max} caracteres.", groups = Integridade.class)
    @Pattern(regexp = PADRAO_CODIGO, message = "Código da matéria deve consistir apenas de caracteres alfanuméricos.", groups = Integridade.class)
    @Column(name = "codigo")
    private String codigo;
    
    @NotNull(message = "Nome da matéria deve ser fornecido.", groups = Integridade.class)
    @Size(min = 1, max = TAMANHO_NOME, message = "Nome da matéria deve estar entre {min} e {max} caracteres.", groups = Integridade.class)
    @Pattern(regexp = PADRAO_NOME, message = "Nome da matéria não pode ser vazio.", groups = Integridade.class)
    @Column(name = "nome")
    private String nome;
    
    @NotNull(message = "Matéria deve possuir um método de avaliação.", groups = Integridade.class)
    @Column(name = "metodo_avaliacao")
    private MetodoAvaliacao metodoAvaliacao;
    
    @OneToMany(mappedBy = "materia")
    private List<Turma> turmas;

    public Materia() {
    }

    public Materia(Long id) {
        this.id = id;
    }

    public Materia(Coordenacao coordenacaoOrigem, String codigo) {
        this.coordenacaoOrigem = coordenacaoOrigem;
        this.codigo = codigo;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Coordenacao getCoordenacaoOrigem() {
        return coordenacaoOrigem;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public MetodoAvaliacao getMetodoAvaliacao() {
        return metodoAvaliacao;
    }

    public void setMetodoAvaliacao(MetodoAvaliacao metodoAvaliacao) {
        if (metodoAvaliacao == null) {
            // TODO Melhorar mensagem de validação
            throw new ErroValidacao();
        }
        this.metodoAvaliacao = metodoAvaliacao;
    }

    public List<Turma> getTurmas() {
        return Collections.unmodifiableList(turmas);
    }
    
}
