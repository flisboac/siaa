
package com.flaviolisboa.siaa.negocio.perfis;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.flaviolisboa.siaa.negocio.logins.Login;
import com.flaviolisboa.siaa.negocio.logins.RepositorioLogin;
import com.flaviolisboa.siaa.util.UtilJpa;
import com.flaviolisboa.siaa.util.entidades.impl.RepositorioJpa;
import com.flaviolisboa.siaa.util.excecoes.ErroPersistencia;
import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import com.flaviolisboa.siaa.util.marcadores.orm.ConsultaPorIdentidade;

@Stateless(name = "RepositorioPerfil")
@Local(RepositorioPerfil.class)
public class RepositorioPerfilJpa extends RepositorioJpa<Perfil> implements RepositorioPerfil {

	@EJB
	private RepositorioLogin repositorioLogin;
	
	@EJB
	private ValidadorPerfil validador;
	
	public RepositorioPerfilJpa() {
		super(Perfil.class);
	}

	@EJB
	public void setValidador(ValidadorPerfil validador) {
		this.validador = validador;
		super.setValidador(validador);
	}
	
	@Override
	public Perfil buscarAlgum(TipoPerfil tipoPerfil, String identificacao) {
		if (tipoPerfil == null) {
			// TODO melhorar erro de validação
			throw new ErroValidacao();
		}
		return buscarAlgum(tipoPerfil.getClasseEntidade(), identificacao);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E extends Perfil> E buscarAlgum(Class<E> classePerfil, String identificacao) {
		if (classePerfil == null
				|| identificacao == null) {
			// TODO Melhorar o erro lançado
			throw new ErroValidacao();
		}
		
		EntityManager em = getEntityManager();
		E retorno = null;
		
		if (PerfilAluno.class == classePerfil) {
			PerfilAluno perfilAluno = new PerfilAluno(null, identificacao);
			validador.validar(perfilAluno, PerfilAluno_.matricula, ConsultaPorIdentidade.class);
			
	        try {
	            CriteriaBuilder cb = em.getCriteriaBuilder();
	            CriteriaQuery<PerfilAluno> q = cb.createQuery(PerfilAluno.class);
	            Root<PerfilAluno> r = q.from(PerfilAluno.class);
	            q.select(r).where(
    				cb.equal(r.get(PerfilAluno_.matricula), identificacao)
	            );
	    		
	            TypedQuery<PerfilAluno> query = em.createQuery(q);
				retorno = (E) UtilJpa.buscarAlgum(query);
	            
	        } catch (PersistenceException ex) {
	            throw new ErroPersistencia(ex);
	        }

	        
		} else if (PerfilCoordenador.class == classePerfil
				|| PerfilProfessor.class == classePerfil) {
			
	        Login login = repositorioLogin.buscarAlgum(identificacao);

	        if (login != null) {
		        try {
		            CriteriaBuilder cb = em.getCriteriaBuilder();
		            CriteriaQuery<E> q = cb.createQuery(classePerfil);
		            Root<E> r = q.from(classePerfil);
		            q.select(r).where(
	    				cb.equal(r.get(Perfil_.pessoa), login.getPessoa())
		            );
		    		
		            TypedQuery<E> query = em.createQuery(q);
					retorno = UtilJpa.buscarAlgum(query);
		            
		        } catch (PersistenceException ex) {
		            throw new ErroPersistencia(ex);
		        }
	        }
	        
		} else {
			// TODO melhorar o erro lançado
			throw new ErroValidacao();
		}
		
		return retorno;
	}

}
