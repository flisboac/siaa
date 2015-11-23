
package com.flaviolisboa.siaa.util.entidades.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.flaviolisboa.siaa.util.entidades.Entidade;
import com.flaviolisboa.siaa.util.entidades.Repositorio;
import com.flaviolisboa.siaa.util.entidades.Validador;
import com.flaviolisboa.siaa.util.excecoes.ErroEntidadeInexistente;
import com.flaviolisboa.siaa.util.excecoes.ErroEstadoEntidadeInvalido;
import com.flaviolisboa.siaa.util.excecoes.ErroPersistencia;
import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import com.flaviolisboa.siaa.util.marcadores.NaoNulo;
import com.flaviolisboa.siaa.util.marcadores.orm.Alteracao;
import com.flaviolisboa.siaa.util.marcadores.orm.ConsultaPorIdentificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Criacao;
import com.flaviolisboa.siaa.util.marcadores.orm.ExclusaoPorIdentificacao;

public class RepositorioJpa<T extends Entidade> implements Repositorio<T> {

	@PersistenceContext
    private EntityManager entityManager;
    
    private Class<T> classeEntidade;
    private Validador<T> validador;
    private boolean gerenciadoPeloContainer = true;
    
    public RepositorioJpa(Class<T> classeEntidade) {
        this.classeEntidade = classeEntidade;
    }
    
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
    	this.entityManager = entityManager;
    	this.gerenciadoPeloContainer = false;
    }

    public void setEntityManager(EntityManager entityManager, boolean gerenciado) {
    	this.entityManager = entityManager;
    	this.gerenciadoPeloContainer = gerenciado;
    }
    
    public Class<T> getClasseEntidade() {
        return classeEntidade;
    }
    
    protected Validador<T> getValidador() {
        return validador;
    }

    public void setValidador(Validador<T> validador) {
        this.validador = validador;
    }

    public boolean isGerenciadoPeloContainer() {
        return gerenciadoPeloContainer;
    }

    @Override
    public <TT extends T> TT salvar(TT entidade) throws ErroPersistencia, ErroValidacao {
        EntityManager em = getEntityManager();
        Validador<T> v = getValidador();
        
        v.validarOuFalhar(entidade, NaoNulo.class);
        
        try {
            if (!entidade.isIdentificado()) {
                v.validarOuFalhar(entidade, Criacao.class);
                // TODO Validar pela identidade a partir de anotações na classe da entidade
                em.persist(entidade);

            } else {
                // TODO Validar pela identidade a partir de anotações na classe da entidade
                v.validarOuFalhar(entidade, Alteracao.class);
                entidade = em.merge(entidade);
            }

            em.flush();
            return entidade;
            
        } catch (PersistenceException ex) {
            throw new ErroPersistencia(ex);
        }
    }

    @Override
    public <TT extends T> void excluir(TT entidade) throws ErroValidacao, ErroEntidadeInexistente, ErroPersistencia, ErroEstadoEntidadeInvalido {
        EntityManager em = getEntityManager();
        Validador<T> v = getValidador();        
        
        v.validarOuFalhar(entidade, NaoNulo.class);
        
        try {
            if (!em.contains(entidade)) {
                if (entidade.isIdentificado()) {
                    v.validarOuFalhar(entidade, ExclusaoPorIdentificacao.class);
                    @SuppressWarnings("unchecked")
					Class<TT> classe = (Class<TT>) entidade.getClass();
                    entidade = em.find(classe, entidade.getId());

                } else {
                    // TODO Verificação da identidade por anotações
                    throw new ErroPersistencia(new UnsupportedOperationException());
                    //v.validarOuFalhar(entidade, ExclusaoPorIdentidade.class);
                }
            }

            if (entidade == null) {
                throw new ErroEntidadeInexistente();
            }
            
            em.remove(entidade);
            
        } catch (IllegalArgumentException ex) {
            throw new ErroEstadoEntidadeInvalido(ex);
            
        } catch (PersistenceException ex) {
            throw new ErroPersistencia(ex);
        }
    }
    
    @Override
    public List<T> buscarTodos() {
        EntityManager em = getEntityManager();
        
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> q = cb.createQuery(getClasseEntidade());
            Root<T> r = q.from(getClasseEntidade());
            q.select(r);
            
            TypedQuery<T> query = em.createQuery(q);
            List<T> retorno = query.getResultList();
            return retorno;
            
        } catch (PersistenceException ex) {
            throw new ErroPersistencia(ex);
        }
    }

    @Override
    public T buscarAlgum(Object id) {
        EntityManager em = getEntityManager();
        
        try {
            T entidade = em.find(getClasseEntidade(), id);
            return entidade;
            
        } catch (PersistenceException ex) {
            throw new ErroPersistencia(ex);
        }
    }

    @Override
    public <TT extends T> TT buscarAlgum(TT entidade) {        
        EntityManager em = getEntityManager();
        Validador<T> v = getValidador();
        
        try {
			@SuppressWarnings("unchecked")
			Class<TT> classe = (Class<TT>) entidade.getClass();
            v.validarOuFalhar(entidade, NaoNulo.class);
            
            if (entidade.isIdentificado()) {
                v.validarOuFalhar(entidade, ConsultaPorIdentificacao.class);
                entidade = em.find(classe, entidade.getId());
                
            } else {
                // TODO Verificação da identidade por anotações
                throw new ErroPersistencia(new UnsupportedOperationException());
            }
            
            return entidade;
            
        } catch (PersistenceException ex) {
            throw new ErroPersistencia(ex);
        }
    }

    @Override
    public T buscar(Object id) {
        T entidade = buscarAlgum(id);
        
        if (entidade == null) {
            // TODO Definir mensagem.
            throw new ErroEntidadeInexistente();
        }
        
        return entidade;
    }

    @Override
    public <TT extends T> TT buscar(TT entidade) {
        entidade = buscarAlgum(entidade);
        
        if (entidade == null) {
            // TODO Definir mensagem.
            throw new ErroEntidadeInexistente();
        }
        
        return entidade;
    }

    @Override
    public Number contar() {        
        EntityManager em = getEntityManager();
        
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> q = cb.createQuery(Long.class);
            Root<T> r = q.from(getClasseEntidade());
            q.select(cb.count(r));
            
            TypedQuery<Long> query = em.createQuery(q);
            Long retorno = query.getSingleResult();
            return retorno;
            
        } catch (PersistenceException ex) {
            throw new ErroPersistencia(ex);
        }
    }

    @Override
    public <TT extends T> boolean existe(TT entidade) {
        // TODO melhorar a implementação
        return buscarAlgum(entidade) != null;
    }
    
}
