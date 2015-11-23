package com.flaviolisboa.siaa.web.views.coordenador;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.flaviolisboa.siaa.negocio.pessoas.Pessoa;

public class PessoaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa;
	
	@NotNull(message = "Informe um nome.")
    @Size(min = 1, max = Pessoa.TAMANHO_NOME_COMPLETO, message = "Informe um nome entre {min} e {max} caracteres.")
    @Pattern(regexp = Pessoa.PADRAO_NOME_COMPLETO, message = "Formato inválido para nome.")
	private String nomeCompleto;

    @NotNull(message = "Informe uma data de nascimento.")
    @Past(message = "Data de nascimento inválida.")
	private Date dataNascimento;
 
}
