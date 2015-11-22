package com.flaviolisboa.siaa.negocio.logins;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.flaviolisboa.siaa.util.UtilJpa;
import com.flaviolisboa.siaa.util.entidades.impl.RepositorioJpa;
import com.flaviolisboa.siaa.util.excecoes.ErroPersistencia;
import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;

@Stateless(name = "RepositorioLogin")
@Local(RepositorioLogin.class)
public class RepositorioLoginJpa extends RepositorioJpa<Login> implements RepositorioLogin {

	@SuppressWarnings("unused")
	private ValidadorLogin validador;
	
	public RepositorioLoginJpa() {
		super(Login.class);
	}
	
	@EJB
	public void setValidador(ValidadorLogin validador) {
		this.validador = validador;
		super.setValidador(validador);
	}
	
	@Override
	public Login buscarAlgum(String nome) {
		if (nome == null || nome.isEmpty()) {
			// TODO melhorar classe de erro.
			throw new ErroValidacao();
		}
		
		EntityManager em = getEntityManager();
		nome = Login.normalizarNome(nome);
		
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Login> q = cb.createQuery(Login.class);
            Root<Login> r = q.from(Login.class);
            q.select(r).where(
				cb.equal(r.get(Login_.nome), nome)
            );
    		
            TypedQuery<Login> query = em.createQuery(q);
            return UtilJpa.buscarAlgum(query);
            
        } catch (PersistenceException ex) {
            throw new ErroPersistencia(ex);
        }
	}

}
