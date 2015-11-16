
package com.flaviolisboa.siaa.negocio.autenticacoes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.flaviolisboa.siaa.negocio.pessoas.Pessoa;
import com.flaviolisboa.siaa.util.entidades.Entidade;
import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "tipo")
@Table(name = "autenticacao", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "id_pessoa", "tipo" }, name = "uq_autenticacao")
})
@SequenceGenerator(name = "sq_autenticacao", sequenceName = "sq_autenticacao", initialValue = 1, allocationSize = 1)
@GroupSequence({ Identidade.class, Integridade.class})
public class Autenticacao implements Entidade, Serializable {
	private static final long serialVersionUID = 1L;

    @NotNull(message = "ID não pode ser nulo.", groups = Identificacao.class)
    @Id
    @GeneratedValue(generator = "sq_autenticacao", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @NotNull(message = "O método de autenticação deve estar associado a uma pessoa.", groups = Identidade.class)
    @Valid
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_autenticacao_id_pessoa"), nullable = false, insertable = false, updatable = false)
    private Pessoa pessoa;
    
    @NotNull(message = "Deve ser especificado um tipo para o método de autenticação.", groups = Identidade.class)
    @Convert(converter = ConversorTipoAutenticacaoJpa.class)
    @Column(name = "tipo", nullable = false, insertable = false, updatable = false)
    private TipoAutenticacao tipo;

    public Autenticacao() {
    }

    public Autenticacao(Long id) {
        this.id = id;
        if (this.id == null) {
            // TODO Melhorar a exceção
            throw new ErroValidacao();
        }
    }

    public Autenticacao(Pessoa pessoa, TipoAutenticacao tipo) {
        this.pessoa = pessoa;
        this.tipo = tipo;
        if (pessoa == null || tipo == null) {
            // TODO Melhorar a exceção
            throw new ErroValidacao();
        }
    }
    
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public TipoAutenticacao getTipo() {
        return tipo;
    }
    
}
