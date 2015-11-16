
package com.flaviolisboa.siaa.negocio.perfis;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.flaviolisboa.siaa.negocio.pessoas.Pessoa;
import com.flaviolisboa.siaa.util.entidades.impl.EntidadeAbstrata;
import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
@SequenceGenerator(name = "sq_perfil", sequenceName = "sq_perfil", initialValue = 1, allocationSize = 1)
@GroupSequence({ Identidade.class, Integridade.class })
public class Perfil extends EntidadeAbstrata<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

    @NotNull(message = "ID não pode ser nulo.", groups = Identificacao.class)
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_perfil")
    @Column(name = "id")
    @Access(AccessType.PROPERTY)
    private Long id;
    
    @NotNull(message = "Perfil de pessoa deve possuir um tipo.", groups = Identidade.class)
    @Convert(converter = ConversorTipoPerfilJpa.class)
    @Column(name = "tipo", insertable = false, updatable = false)
    private TipoPerfil tipo;

    @NotNull(message = "Perfil de pessoa deve pertencer à uma pessoa.", groups = Identidade.class)
    @Valid
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    private Pessoa pessoa;
    
    @Past(message = "A data de desativação de um perfil deve estar no passado.", groups = Integridade.class)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_desativacao")
    private Date dataDesativacao;
	
    public Perfil() {
    }

    public Perfil(Long id) {
        this.id = id;
    }

    public Perfil(TipoPerfil tipo, Pessoa pessoa) {
        this.tipo = tipo;
        this.pessoa = pessoa;
    }
        
    @Override
    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
    	this.id = id;
    }
    
    public TipoPerfil getTipo() {
        return tipo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public boolean isAtivo() {
        return dataDesativacao == null;
    }

    public Date getDataDesativacao() {
        return dataDesativacao;
    }
    
    public void desativar() {
    	if (isAtivo()) {
    		this.dataDesativacao = new Date();
    	}
    }
}
