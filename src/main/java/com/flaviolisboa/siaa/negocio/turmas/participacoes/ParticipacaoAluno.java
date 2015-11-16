package com.flaviolisboa.siaa.negocio.turmas.participacoes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.flaviolisboa.siaa.negocio.materias.MetodoAvaliacao;
import com.flaviolisboa.siaa.negocio.perfis.PerfilAluno;
import com.flaviolisboa.siaa.negocio.turmas.ConversorStatusAprovacaoJpa;
import com.flaviolisboa.siaa.negocio.turmas.Turma;
import com.flaviolisboa.siaa.util.entidades.impl.EntidadeAbstrata;
import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;

@Entity
@Table(name = "turma_aluno", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "id_turma", "id_aluno" }, name = "uq_turma_aluno")
})
@SequenceGenerator(name = "sq_turma_aluno", sequenceName = "sq_turma_aluno", allocationSize = 1, initialValue = 1)
@GroupSequence({ Identidade.class, Integridade.class })
public class ParticipacaoAluno extends EntidadeAbstrata<Long> implements Serializable {
	private static final long serialVersionUID = 1L;	
    
	private static final BigDecimal DOIS = new BigDecimal(2);
	private static final BigDecimal QUATRO = new BigDecimal(4);
	private static final BigDecimal CINCO = new BigDecimal(5);
	private static final BigDecimal SEIS = new BigDecimal(6);
	
	public static final BigDecimal NOTA_MINIMA_A3 = CINCO;
	public static final BigDecimal MEDIA_APROVACAO = SEIS;
	
    public static final int PRECISAO_NOTA = 4;
    public static final int ESCALA_NOTA = 2;
    
    @NotNull(message = "ID não pode ser nulo.", groups = Identificacao.class)
    @Id
    @GeneratedValue(generator = "sq_turma_aluno", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @NotNull(message = "Participação do aluno deve dizer respeito a uma turma.", groups = Identidade.class)
    @Valid
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_turma", referencedColumnName = "id", nullable = false)
    private Turma turma;

    @NotNull(message = "Participação do aluno em uma turma deve estar relacionado a um aluno.", groups = Identidade.class)
    @Valid
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_aluno", referencedColumnName = "id", nullable = false)
    private PerfilAluno aluno;

    @Valid
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "nota", column = @Column(name = "nota_01", precision = PRECISAO_NOTA, scale = ESCALA_NOTA)),
        @AttributeOverride(name = "ausente", column = @Column(name = "nota_01_ausente")),
        @AttributeOverride(name = "dataLancamento", column = @Column(name = "nota_01_data"))
    })
    private NotaAvaliacao avaliacao01;
    
    @Valid
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "nota", column = @Column(name = "nota_02", precision = PRECISAO_NOTA, scale = ESCALA_NOTA)),
        @AttributeOverride(name = "ausente", column = @Column(name = "nota_02_ausente")),
        @AttributeOverride(name = "dataLancamento", column = @Column(name = "nota_02_data"))
    })
    private NotaAvaliacao avaliacao02;

    @Valid
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "nota", column = @Column(name = "nota_03", precision = PRECISAO_NOTA, scale = ESCALA_NOTA)),
        @AttributeOverride(name = "ausente", column = @Column(name = "nota_03_ausente")),
        @AttributeOverride(name = "dataLancamento", column = @Column(name = "nota_03_data"))
    })
    private NotaAvaliacao avaliacao03;

    @Valid
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "nota", column = @Column(name = "nota_04", precision = PRECISAO_NOTA, scale = ESCALA_NOTA)),
        @AttributeOverride(name = "ausente", column = @Column(name = "nota_04_ausente")),
        @AttributeOverride(name = "dataLancamento", column = @Column(name = "nota_04_data"))
    })
    private NotaAvaliacao avaliacao04;

	@DecimalMin(value = "0.0", message = "Média preliminar deve ser no mínimo {value}.", groups = Integridade.class)
	@DecimalMax(value = "10.0", message = "Média preliminar deve ser no máximo {value}.", groups = Integridade.class)
    @Column(name = "media_preliminar", precision = PRECISAO_NOTA, scale = ESCALA_NOTA)
    private BigDecimal mediaPreliminar;

	@DecimalMin(value = "0.0", message = "Média final deve ser no mínimo {value}.", groups = Integridade.class)
	@DecimalMax(value = "10.0", message = "Média final deve ser no máximo {value}.", groups = Integridade.class)
    @Column(name = "media_final", precision = PRECISAO_NOTA, scale = ESCALA_NOTA)
    private BigDecimal mediaFinal;

	@NotNull(message = "Aluno deve possuir um status de participação em turma.", groups = Integridade.class)
    @Convert(converter = ConversorStatusAprovacaoJpa.class)
    @Column(name = "status")
    private StatusAprovacao status;
    
    public ParticipacaoAluno() {
    }

    public ParticipacaoAluno(Long id) {
        this.id = id;
    }

    public ParticipacaoAluno(Turma turma, PerfilAluno aluno) {
        this.turma = turma;
        this.aluno = aluno;
        this.status = StatusAprovacao.CURSANDO;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Turma getTurma() {
        return turma;
    }

    public PerfilAluno getAluno() {
        return aluno;
    }

    public BigDecimal getMediaPreliminar() {
        return mediaPreliminar;
    }

    public BigDecimal getMediaFinal() {
        return mediaFinal;
    }

    public StatusAprovacao getStatus() {
        return status;
    }

    public NotaAvaliacao getAvaliacao01() {
        return avaliacao01;
    }

    public void setAvaliacao01(NotaAvaliacao avaliacao01) {
        if (avaliacao01 == null) {
            // TODO Melhorar a mensagem de erro
            throw new ErroValidacao();
        }
        this.avaliacao01 = avaliacao01;
        recalcularNotas();
    }

    public NotaAvaliacao getAvaliacao02() {
        return avaliacao02;
    }

    public void setAvaliacao02(NotaAvaliacao avaliacao02) {
        if (avaliacao02 == null || avaliacao01 == null) {
            // TODO Melhorar a mensagem de erro
            throw new ErroValidacao();
        }
        this.avaliacao02 = avaliacao02;
        recalcularNotas();
    }

    public NotaAvaliacao getAvaliacao03() {
        return avaliacao03;
    }

    public void setAvaliacao03(NotaAvaliacao avaliacao03) {
        if (avaliacao03 == null || avaliacao01 == null || avaliacao02 == null) {
            // TODO Melhorar a mensagem de erro
            throw new ErroValidacao();
        }
        this.avaliacao03 = avaliacao03;
        recalcularNotas();
    }

    public NotaAvaliacao getAvaliacao04() {
        return avaliacao04;
    }

    public void setAvaliacao04(NotaAvaliacao avaliacao04) {
        this.avaliacao04 = avaliacao04;
        recalcularNotas();
    }
    
    private void recalcularNotas() {
        MetodoAvaliacao metodoAvaliacao = turma.getMateria().getMetodoAvaliacao();
        switch (metodoAvaliacao) {
        case A3:
            recalcularNotasMetodoA3();
            break;
        case A4:
            recalcularNotasMetodoA4();
            break;
        case T4:
            recalcularNotasMetodoT4();
            break;
        }
    }
    
    private void recalcularNotasMetodoA3() {
        BigDecimal nota1 = null;
        BigDecimal nota2 = null;
        boolean calculado = false;
        boolean usandoA3 = false;
    	
        if (avaliacao01 != null && avaliacao01.isAusente()) {
        	this.mediaFinal = BigDecimal.ZERO;
            this.status = StatusAprovacao.AUSENTE;
            calculado = true;
            
        } else if (avaliacao01 != null) {
    		nota1 = avaliacao01.getNota();
        	
    		if (nota1.compareTo(NOTA_MINIMA_A3) < 0) {
    			nota1 = BigDecimal.ZERO;
    		}
    		
        	if (avaliacao02 != null) {
        		if (avaliacao02.isAusente()) {
        			usandoA3 = true;
        			
        		} else {
        			nota2 = avaliacao02.getNota();
        		}
        	}
        	
        	if (usandoA3 && avaliacao03 != null) {
        		if (avaliacao03.isAusente()) {
        			nota2 = BigDecimal.ZERO;
        			
        		} else {
        			nota2 = avaliacao03.getNota();
        		}
        	}
        	
    		if (nota2 != null && nota2.compareTo(CINCO) < 0) {
    			nota2 = BigDecimal.ZERO;
    		}
        }
        
        if (!calculado) {
            boolean prontoParaNotaFinal = nota1 != null && nota2 != null;
        	nota1 = nota1 != null ? nota1 : BigDecimal.ZERO;
        	nota2 = nota2 != null ? nota2 : BigDecimal.ZERO;
        	mediaPreliminar = nota1.add(nota2).divide(DOIS, ESCALA_NOTA, RoundingMode.HALF_EVEN);
            
	        if (prontoParaNotaFinal) {
	        	mediaFinal = mediaPreliminar;
	        	mediaPreliminar = null;
	        	recalcularStatus();
	        }
        }
    }
    
    private void recalcularNotasMetodoA4() {
        
    	if (avaliacao04 != null) {
    		mediaPreliminar = null;
    		
    		if (avaliacao04.isAusente()) {
    			mediaFinal = BigDecimal.ZERO;
    			status = StatusAprovacao.AUSENTE;
    			
    		} else {
    			mediaFinal = avaliacao04.getNota();
            	recalcularStatus();
    		}
    	}
    }
    
    private void recalcularNotasMetodoT4() {
        BigDecimal nota1 = avaliacao01 != null ? avaliacao01.getNota() : BigDecimal.ZERO;
        BigDecimal nota2 = avaliacao02 != null ? avaliacao02.getNota() : BigDecimal.ZERO;
        BigDecimal nota3 = avaliacao03 != null ? avaliacao03.getNota() : BigDecimal.ZERO;
        boolean prontoParaNotaFinal = avaliacao01 != null && avaliacao02 != null && avaliacao03 != null;
    	mediaPreliminar = nota1.add(nota2.multiply(DOIS)).add(nota3).divide(QUATRO, ESCALA_NOTA, RoundingMode.HALF_EVEN);
        
        if (prontoParaNotaFinal) {
        	mediaFinal = mediaPreliminar;
        	mediaPreliminar = null;
        	recalcularStatus();
        }
    }
    
    private void recalcularStatus() {

		if (mediaFinal.compareTo(MEDIA_APROVACAO) < 0) {
        	status = StatusAprovacao.REPROVADO;
        	
        } else {
        	status = StatusAprovacao.APROVADO;
        }
    }
    
}
