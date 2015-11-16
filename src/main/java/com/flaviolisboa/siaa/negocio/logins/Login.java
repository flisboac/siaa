package com.flaviolisboa.siaa.negocio.logins;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.flaviolisboa.siaa.negocio.pessoas.Pessoa;
import com.flaviolisboa.siaa.util.entidades.impl.EntidadeAbstrata;
import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;

@Entity
@Table(name = "login", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "nome" }, name = "uq_login"),
		@UniqueConstraint(columnNames = { "id_pessoa" }, name = "uq_login_id_pessoa"),
})
@SequenceGenerator(name = "sq_login", sequenceName = "sq_login", allocationSize = 1, initialValue = 1)
@GroupSequence({ Identidade.class, Integridade.class })
public class Login extends EntidadeAbstrata<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int TAMANHO_NOME = 32;
	public static final String PADRAO_NOME = "[a-zA-Z][a-zA-Z0-9]*";

    @NotNull(message = "ID não pode ser nulo.", groups = Identificacao.class)
	@Id
	@GeneratedValue(generator = "sq_login", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotNull(message = "Entrada de Login para uma pessoa deve possuir um nome.", groups = Identidade.class)
	@Size(min = 1, max = TAMANHO_NOME, message = "Nome de login deve possuir entre {min} e {max} caracteres.", groups = Identidade.class)
	@Pattern(regexp = PADRAO_NOME, message = "Nome de login deve consistir apenas de caracteres alfanuméricos.", groups = Identidade.class)
	@Column(name = "nome", length = TAMANHO_NOME, nullable = false)
	private String nome;
	
    @NotNull(message = "Login deve pertencer a uma pessoa.", groups = Integridade.class)
    @Valid
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pessoa", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_login_id_pessoa"))
	private Pessoa pessoa; 
    
    @Past(message = "Data de desativação do login deve estar no passado.", groups = Integridade.class)
    @Column(name = "data_desativacao")
    private Date dataDesativacao;
    
    public Login() {
    }

    public Login(Long id) {
        this.id = id;
    }

    public Login(Pessoa pessoa, String nome) {
    	this.pessoa = pessoa;
    	doSetNome(nome);
    }

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public Date getDataDesativacao() {
		return dataDesativacao;
	}
	
	public void desativar() {
		if (isAtivo()) {
			this.dataDesativacao = new Date();
		}
	}
	
	public boolean isAtivo() {
		return dataDesativacao == null;
	}
	
    private void doSetNome(String nome) {
        nome = nome != null ? nome.replaceAll("[_.]", "") : nome;
        if (nome == null
        		|| nome.isEmpty()
        		|| nome.length() > TAMANHO_NOME
        		|| !nome.matches(PADRAO_NOME)) {
            // TODO Melhorar mensagem de validação
            throw new ErroValidacao();
        }
        this.nome = nome;
    }
}
