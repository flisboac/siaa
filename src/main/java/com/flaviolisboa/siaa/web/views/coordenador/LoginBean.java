package com.flaviolisboa.siaa.web.views.coordenador;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.flaviolisboa.siaa.negocio.logins.Login;

public class LoginBean {

	@NotNull(message = "Informe um nome de login.")
	@Size(min = 1, max = Login.TAMANHO_NOME, message = "Login deve conter entre {min} e {max} caracteres.")
	@Pattern(regexp = Login.PADRAO_NOME, message = "Formato de nome de login inválido.")
	@Column(name = "nome")
    private String nomeLogin;
	
}
