package com.flaviolisboa.siaa.negocio.turmas.participacoes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;

@Embeddable
@Access(AccessType.FIELD)
public class NotaAvaliacao implements Serializable {
	private static final long serialVersionUID = 1L;
    
	@DecimalMin(value = "0.0", message = "Valor da nota deve ser no mínimo {value}.", groups = Integridade.class)
	@DecimalMax(value = "10.0", message = "Valor da nota deve ser no máximo {value}.", groups = Integridade.class)
    @Column(name = "nota")
    private BigDecimal nota;
    
	@NotNull(message = "Deve ser especificado se o aluno compareceu à avaliação.", groups = Integridade.class)
    @Column(name = "ausente")
    private Boolean ausente;
    
    @Past(message = "Data de lançamento da nota deve estar no futuro.", groups = Integridade.class)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_lancamento")
    private Date dataLancamento;

    public NotaAvaliacao() {
    }

    public NotaAvaliacao(BigDecimal nota) {
        doSetNota(nota);
    }

    public NotaAvaliacao(boolean ausente) {
    	this.ausente = ausente;
    	doSetNota(BigDecimal.ZERO);
    }
    
    public BigDecimal getNota() {
        return nota;
    }

    public boolean isAusente() {
        return ausente != null ? ausente : false;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }
    
    private void doSetNota(BigDecimal nota) {
        if (nota == null
                || nota.compareTo(BigDecimal.ZERO) < 0
                || nota.compareTo(new BigDecimal(10)) > 0) {
            throw new ErroValidacao();
        }
        this.nota = nota;
        this.dataLancamento = new Date();
    }
}
