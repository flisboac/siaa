
package com.flaviolisboa.siaa.negocio.autenticacoes;

import com.flaviolisboa.siaa.negocio.pessoas.Pessoa;
import com.flaviolisboa.siaa.util.entidades.Entidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "tipo")
@Table(name = "autenticacao", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "id_pessoa", "tipo" }, name = "uq_autenticacao")
})
@SequenceGenerator(name = "sq_autenticacao", sequenceName = "sq_autenticacao", initialValue = 1, allocationSize = 1)
@GroupSequence({ Identificacao.class, Identidade.class, Integridade.class})
public class Autenticacao implements Entidade, Serializable {

    @NotNull(message = "ID não pode ser nulo.", groups = Identificacao.class)
    @Id
    @GeneratedValue(generator = "sq_autenticacao", strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @NotNull(message = "O método de autenticação deve estar associado a uma pessoa.", groups = Identidade.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id", nullable = false)
    private Pessoa pessoa;
    
    @NotNull(message = "Deve ser especificado um tipo para o método de autenticação.", groups = Identidade.class)
    @Convert(converter = ConversorTipoAutenticacaoJpa.class)
    @Column(name = "tipo", nullable = false)
    private TipoAutenticacao tipo;

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

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public TipoAutenticacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoAutenticacao tipo) {
        this.tipo = tipo;
    }
    
}
