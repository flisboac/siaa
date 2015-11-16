package com.flaviolisboa.siaa.negocio.coordenacoes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.flaviolisboa.siaa.negocio.perfis.PerfilCoordenador;
import com.flaviolisboa.siaa.util.entidades.impl.EntidadeAbstrata;
import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Identidade;
import com.flaviolisboa.siaa.util.marcadores.orm.Identificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Integridade;

@Entity
@Table(name = "coordenacao")
@SequenceGenerator(name = "sq_coordenacao", sequenceName = "sq_coordenacao", initialValue = 1, allocationSize = 1)
@GroupSequence({ Identidade.class, Integridade.class })
public class Coordenacao extends EntidadeAbstrata<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

    public static final int TAMANHO_CODIGO = 3;
    public static final int TAMANHO_NOME = 3;
    public static final String PADRAO_CODIGO = "[a-zA-Z][a-zA-Z0-9]{1,}";
    public static final String PADRAO_NOME = ".*\\S.*";

    @NotNull(message = "ID não pode ser nulo.", groups = Identificacao.class)
    @Id
    @GeneratedValue(generator = "sq_coordenacao", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @NotNull(message = "Deve ser informado um código para a coordenação.", groups = Identidade.class)
    @Size(min = 2, max = TAMANHO_CODIGO, message = "Mensagem deve ter entre {min} e {max} caracter(es).", groups = Identidade.class)
    @Pattern(regexp = PADRAO_CODIGO, message = "Código deve conter apenascaracteres alfanuméricos, começando poruma letra.", groups = Identidade.class)
    @Column(name = "codigo")
    private String codigo;
    
    @NotNull(message = "Nome da coordenação deve ser fornecido.", groups = Integridade.class)
    @Size(min = 1, max = TAMANHO_NOME, message = "Nome da coordenação deve conter entre {min} e {max} caracter(es).", groups = Integridade.class)
    @Pattern(regexp = PADRAO_NOME, message = "Nome da coordenação não pode ser vazio.", groups = Integridade.class)
    @Column(name = "nome")
    private String nome;
    
    @NotNull(message = "Coordenação deve possuir um coordenador responsável por ela.", groups = Integridade.class)
    @Valid
    @ManyToOne
    @JoinColumn(name = "id_perfil_coordenador", referencedColumnName = "id")
    private PerfilCoordenador coordenador;

    public Coordenacao() {
    }

    public Coordenacao(Long id) {
        if (id == null) {
            // TODO Melhorar mensagem de validação
            throw new ErroValidacao();
        }
        this.id = id;
    }

    public Coordenacao(String codigo) {
        if (codigo == null 
                || codigo.isEmpty() 
                || codigo.length() > TAMANHO_CODIGO
                || codigo.matches(PADRAO_CODIGO)) {
            // TODO Melhorar mensagem de validação
            throw new ErroValidacao();
        }
        this.codigo = codigo;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null 
                || nome.isEmpty() 
                || nome.length() > TAMANHO_NOME) {
            // TODO melhorar mensagem de erro
            throw new ErroValidacao();
        }
        this.nome = nome;
    }

    public PerfilCoordenador getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(PerfilCoordenador coordenador) {
        if (coordenador == null
                || !coordenador.isIdentificado()) {
            // TODO melhorar mensagem de erro
            throw new ErroValidacao();
        }
        this.coordenador = coordenador;
    }
    
}
