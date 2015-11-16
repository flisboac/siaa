
package com.flaviolisboa.siaa.negocio.pessoas;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.flaviolisboa.siaa.negocio.logins.Login;
import com.flaviolisboa.siaa.util.entidades.impl.EntidadeAbstrata;
import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;

@Entity
@Table(name = "pessoa", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nome_completo", "data_nascimento"}, name = "uq_pessoa_nome")
})
@SequenceGenerator(name = "sq_pessoa", sequenceName = "sq_pessoa", allocationSize = 1, initialValue = 1)
@GroupSequence({ Identidade.class, Integridade.class })
public class Pessoa extends EntidadeAbstrata<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int TAMANHO_NOME_COMPLETO = 128;
	public static final String PADRAO_NOME_COMPLETO = ".*\\S.*";
	
    @NotNull(message = "ID não pode ser nulo.", groups = Identificacao.class)
    @Id
    @GeneratedValue(generator = "sq_pessoa", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @NotNull(message = "Pessoa deve possuir um nome completo.", groups = Identidade.class)
    @Size(min = 1, max = TAMANHO_NOME_COMPLETO, message = "Nome completo da pessoa deve estar entre {min} e {max} caracteres.", groups = Identidade.class)
    @Pattern(regexp = PADRAO_NOME_COMPLETO, message = "Nome completo da pessoa não pode ser vazio.", groups = Identidade.class)
    @Column(name = "nome_completo", nullable = false, length = TAMANHO_NOME_COMPLETO)
    private String nomeCompleto;
    
    @NotNull(message = "Pessoa deve possuir uma data de nascimento.", groups = Identidade.class)
    @Past(message = "Data de nascimento de uma pessoa deve estar no passado.", groups = Identidade.class)
    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento", nullable = false)
    private Date dataNascimento;

	@OneToMany(mappedBy = "pessoa")
	private List<Login> logins;

    public Pessoa() {
    }

    public Pessoa(Long id) {
        this.id = id;
    }

    public Pessoa(String nomeCompleto, Date dataNascimento) {
    	doSetDataNascimento(dataNascimento);
        doSetNomeCompleto(nomeCompleto);
    }

    private void doSetNomeCompleto(String nomeCompleto) {
        nomeCompleto = nomeCompleto != null ? nomeCompleto.trim() : nomeCompleto;
        if (nomeCompleto == null
        		|| nomeCompleto.isEmpty()
        		|| nomeCompleto.length() > TAMANHO_NOME_COMPLETO
        		|| !nomeCompleto.matches(PADRAO_NOME_COMPLETO)) {
        	// TODO Melhorar o erro lançado
        	throw new ErroValidacao();
        }
        this.nomeCompleto = nomeCompleto;
	}

    private void doSetDataNascimento(Date dataNascimento) {
    	if (dataNascimento == null
    			|| dataNascimento.compareTo(new Date()) > 0) {
        	// TODO Melhorar o erro lançado
        	throw new ErroValidacao();
    	}
    	this.dataNascimento = dataNascimento;
    }
    
	@Override
    public Long getId() {
        return id;
    }
    
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
    	doSetNomeCompleto(nomeCompleto);
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
    	doSetDataNascimento(dataNascimento);
    }
	
	public List<Login> getLogins() {
		return Collections.unmodifiableList(logins);
	}

}
