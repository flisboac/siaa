package com.flaviolisboa.siaa.negocio.autenticacoes;

import com.flaviolisboa.siaa.negocio.pessoas.Pessoa;
import com.flaviolisboa.siaa.util.entidades.Repositorio;

public interface RepositorioAutenticacao extends Repositorio<Autenticacao> {

	public <E extends Autenticacao> E buscarAlgum(Pessoa pessoa, Class<E> classeAutenticacao);
	public AutenticacaoSenha novaAutenticacaoSenha(Pessoa pessoa);
}
