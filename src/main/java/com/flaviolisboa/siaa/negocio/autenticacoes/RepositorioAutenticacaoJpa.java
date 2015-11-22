package com.flaviolisboa.siaa.negocio.autenticacoes;

import java.security.SecureRandom;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.flaviolisboa.siaa.negocio.pessoas.Pessoa;
import com.flaviolisboa.siaa.negocio.pessoas.ValidadorPessoa;
import com.flaviolisboa.siaa.util.UtilJpa;
import com.flaviolisboa.siaa.util.entidades.impl.RepositorioJpa;
import com.flaviolisboa.siaa.util.excecoes.ErroPersistencia;
import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import com.flaviolisboa.siaa.util.marcadores.orm.ConsultaPorIdentificacao;

@Stateless(name = "RepositorioAutenticacao")
@Local(RepositorioAutenticacao.class)
public class RepositorioAutenticacaoJpa extends RepositorioJpa<Autenticacao> implements RepositorioAutenticacao {

	private static final int TAMANHO_SAL = 64;
	
	private SecureRandom random = new SecureRandom();
	
	@SuppressWarnings("unused")
	private ValidadorAutenticacao validador;
	
	@EJB
	private ValidadorPessoa validadorPessoa;
	
	public RepositorioAutenticacaoJpa() {
		super(Autenticacao.class);
	}

	@EJB	
	public void setValidador(ValidadorAutenticacao validador) {
		this.validador = validador;
		super.setValidador(validador);
	}
	
	@Override
	public <E extends Autenticacao> E buscarAlgum(Pessoa pessoa, Class<E> classeAutenticacao) {
		if (classeAutenticacao == null
				|| AutenticacaoSenha.class != classeAutenticacao) {
			// TODO Melhorar erro lançado.
			throw new ErroValidacao();
		}
		
		EntityManager em = getEntityManager();
		TipoAutenticacao tipoAutenticacao = TipoAutenticacao.SENHA;
		validadorPessoa.validarOuFalhar(pessoa, ConsultaPorIdentificacao.class);
		
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<E> q = cb.createQuery(classeAutenticacao);
            Root<E> r = q.from(classeAutenticacao);
            q.select(r).where(
        		cb.and(
    				cb.equal(r.get(Autenticacao_.tipo), tipoAutenticacao),
    				cb.equal(r.get(Autenticacao_.pessoa), pessoa)
				)
            );
    		
            TypedQuery<E> query = em.createQuery(q);
            return UtilJpa.buscarAlgum(query);
            
        } catch (PersistenceException ex) {
            throw new ErroPersistencia(ex);
        }
	}

	@Override
	public AutenticacaoSenha novaAutenticacaoSenha(Pessoa pessoa) {
		byte[] bytes = new byte[TAMANHO_SAL];
		random.nextBytes(bytes);
		return new AutenticacaoSenha(pessoa, bytes);
	}

}
