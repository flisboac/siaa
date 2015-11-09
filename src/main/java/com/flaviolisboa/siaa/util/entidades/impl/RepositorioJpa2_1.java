
package com.flaviolisboa.siaa.util.entidades.impl;

import com.flaviolisboa.siaa.util.UtilNumeros;
import com.flaviolisboa.siaa.util.entidades.Entidade;
import com.flaviolisboa.siaa.util.entidades.NavegacaoPagina;
import com.flaviolisboa.siaa.util.entidades.Ordenacao;
import com.flaviolisboa.siaa.util.entidades.Pagina;
import com.flaviolisboa.siaa.util.entidades.Paginacao;
import com.flaviolisboa.siaa.util.entidades.Repositorio;
import com.flaviolisboa.siaa.util.entidades.Validador;
import com.flaviolisboa.siaa.util.excecoes.ErroEntidadeInexistente;
import com.flaviolisboa.siaa.util.excecoes.ErroEstadoEntidadeInvalido;
import com.flaviolisboa.siaa.util.excecoes.ErroNegocio;
import com.flaviolisboa.siaa.util.excecoes.ErroPersistencia;
import com.flaviolisboa.siaa.util.excecoes.ErroPropriedadeInexistente;
import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import com.flaviolisboa.siaa.util.marcadores.NaoNulo;
import com.flaviolisboa.siaa.util.marcadores.orm.Alteracao;
import com.flaviolisboa.siaa.util.marcadores.orm.ConsultaPorIdentificacao;
import com.flaviolisboa.siaa.util.marcadores.orm.Criacao;
import com.flaviolisboa.siaa.util.marcadores.orm.ExclusaoPorIdentificacao;
import com.flaviolisboa.siaa.util.UtilPropriedades;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

public class RepositorioJpa2_1<T extends Entidade> implements Repositorio<T> {

    public static class PaginaJpa2_1 extends PaginaSimples {

        public PaginaJpa2_1() {
        }

        public PaginaJpa2_1(Paginacao config, Number elementos) throws ErroNegocio {
            super(config, elementos);
        }
    }
    
    private Class<T> classeEntidade;
    private Validador<T> validador;
    private EntityManager entityManager;
    private boolean gerenciadoExternamente;

    public RepositorioJpa2_1() {
    }
    
    public RepositorioJpa2_1(Class<T> classeEntidade) {
        this.classeEntidade = classeEntidade;
    }
    
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    public void setEntityManager(EntityManager entityManager, boolean gerenciadoExternamente) {
        this.entityManager = entityManager;
        this.gerenciadoExternamente = gerenciadoExternamente;
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

    public boolean isGerenciadoExternamente() {
        return gerenciadoExternamente;
    }

    @Override
    public <TT extends T> TT salvar(TT entidade) throws ErroPersistencia, ErroValidacao {
        EntityManager em = getEntityManager();
        Validador<T> v = getValidador();
        
        v.validarOuFalhar(entidade, NaoNulo.class);
        
        try {
            if (entidade.isIdentificado()) {
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
    public Pagina paginar(Paginacao paginacao, NavegacaoPagina navegacao, Integer indice) {
        if (paginacao == null 
                || !paginacao.isValido()
                || navegacao == null
                || (navegacao == NavegacaoPagina.POR_INDICE && indice == null)) {
            // TODO melhorar validação dos parâmetros de entrada
            throw new ErroValidacao();
        }
        Number contagem = contar();
        Pagina retorno = new PaginaJpa2_1(paginacao, contagem);
        return retorno;
    }

    @Override
    public Pagina navegar(NavegacaoPagina navegacao, Pagina pagina, Integer indice) {
        if (pagina == null
                || !(pagina instanceof PaginaJpa2_1)
                || navegacao == null
                || (navegacao == NavegacaoPagina.POR_INDICE && indice == null)) {
            // TODO Melhorar validação dos parâmetros de entrada
            throw new ErroValidacao();
        }
        
        PaginaJpa2_1 paginaJpa = (PaginaJpa2_1) pagina;
        return paginaJpa.navegar(navegacao, indice);
    }

    @Override
    public List<T> buscarTodos(Pagina pagina) {
        if (pagina == null
                || !(pagina instanceof PaginaJpa2_1)) {
            // TODO Melhorar validação dos parâmetros de entrada
            throw new ErroValidacao();
        }
        
        EntityManager em = getEntityManager();
        PaginaJpa2_1 paginaJpa = (PaginaJpa2_1) pagina;
        
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> q = cb.createQuery(getClasseEntidade());
            Root<T> r = q.from(getClasseEntidade());
            q.select(r);
            List<Order> orders = new ArrayList<>();
            
            for (Ordenacao ordenacao : pagina.getConfiguracaoPaginacao().getOrdenacoes()) {
                orders.add(getOrder(cb, ordenacao, get(r, ordenacao)));
            }
            
            q.orderBy(orders);
            
            TypedQuery<T> query = em.createQuery(q);
            query.setFirstResult(UtilNumeros.intValueExact(paginaJpa.getIndiceInicialPesquisa()));
            query.setMaxResults(paginaJpa.getTamanhoPagina());
            List<T> retorno = query.getResultList();
            return retorno;
            
        } catch (ArithmeticException | PersistenceException ex) {
            throw new ErroPersistencia(ex);
        }
    }

    @Override
    public Collection<T> buscarTodos() {
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
            Class<TT> classe = (Class<TT>) entidade.getClass();
            v.validarOuFalhar(entidade, NaoNulo.class);
            
            if (entidade.isIdentificado()) {
                v.validarOuFalhar(entidade, ConsultaPorIdentificacao.class);
                
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
    
    private <TT extends T> Expression<?> get(Root<TT> root, Ordenacao ordenacao) throws ErroPersistencia, ErroPropriedadeInexistente {
        return get(root, ordenacao.getPropriedade());
    }
    
    private <TT extends T> Expression<?> get(Root<TT> root, Object propriedade) {
        try {
            if (propriedade instanceof SingularAttribute) {
                SingularAttribute<TT, ?> attr = (SingularAttribute<TT, ?>) propriedade;
                return root.get(attr);
                
            } else if (propriedade instanceof PluralAttribute) {
                PluralAttribute<TT, ? extends Collection, ?> attr = (PluralAttribute<TT, ? extends Collection, ?>) propriedade;
                return root.get(attr);
                
            } else if (propriedade instanceof MapAttribute) {
                MapAttribute<TT, ?, ?> attr = (MapAttribute<TT, ?, ?>) propriedade;
                return root.get(attr);
                
            } else {
                return root.get(UtilPropriedades.getNomePropriedadeBean(propriedade));
            }
            
        } catch (IllegalArgumentException ex) {
            throw new ErroPropriedadeInexistente(ex);
            
        } catch (IllegalStateException ex) {
            throw new ErroPersistencia(ex);
        }
    }
    
    private Order getOrder(CriteriaBuilder builder, Ordenacao ordenacao, Expression<?> expr) {
        Order retorno = null;
        
        switch (ordenacao.getOrdem()) {
        case ASC:
        case NATURAL:
            retorno = builder.asc(expr);
            break;
            
        case DESC:
            retorno = builder.desc(expr);
            break;
        }
        
        return retorno;
    }
}
