
package com.flaviolisboa.siaa.util.entidades;

import com.flaviolisboa.siaa.util.UtilNumeros;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Paginacao {

    private int tamanhoMaximoPagina;
    private Number indiceElementoInicial;
    private Number maximoElementos;
    private final List<Ordenacao> ordenacoes = new ArrayList<>();

    public Paginacao() {
    }
    
    public Paginacao(Paginacao paginacao) {
        this.tamanhoMaximoPagina = paginacao.tamanhoMaximoPagina;
        this.indiceElementoInicial = paginacao.indiceElementoInicial;
        this.maximoElementos = paginacao.maximoElementos;
        this.ordenacoes.addAll(paginacao.ordenacoes);
    }
    
    public int getTamanhoPagina() {
        return tamanhoMaximoPagina;
    }

    public Number getIndiceInicial() {
        return indiceElementoInicial;
    }

    public Number getMaximoElementos() {
        return maximoElementos;
    }

    public List<Ordenacao> getOrdenacoes() {
        return Collections.unmodifiableList(ordenacoes);
    }
    
    public boolean isLimitadoPorIndiceInicial() {
        return indiceElementoInicial != null;
    }
    
    public boolean isLimitadoPorMaximoElementos() {
        return maximoElementos != null;
    }
    
    public boolean isMultiplasPaginas() {
        return tamanhoMaximoPagina > 1;
    }
    
    public static Paginacao paginaUnica() {
        Paginacao paginacao = new Paginacao();
        return paginacao;
    }
    
    public static Paginacao paginaUnica(Number maximo) {
        Paginacao paginacao = paginaUnica();
        paginacao.maximoElementos = maximo;
        return paginacao;
    }
    
    public static Paginacao paginaUnica(Number maximo, Number inicial) {
        Paginacao paginacao = paginaUnica(maximo);
        paginacao.indiceElementoInicial = inicial;
        return paginacao;
    }
    
    public static Paginacao paginaUnica(Number maximo, Number inicial, Ordenacao... ordens) {
        Paginacao paginacao = paginaUnica(maximo);
        paginacao.indiceElementoInicial = inicial;
        Collections.addAll(paginacao.ordenacoes, ordens);
        return paginacao;
    }
    
    public static Paginacao paginaUnica(Number maximo, Ordenacao... ordens) {
        Paginacao paginacao = paginaUnica(maximo);
        Collections.addAll(paginacao.ordenacoes, ordens);
        return paginacao;
    }
    
    public static Paginacao paginaUnica(Ordenacao... ordens) {
        Paginacao paginacao = paginaUnica();
        Collections.addAll(paginacao.ordenacoes, ordens);
        return paginacao;
    }
    
    public static Paginacao paginado(int tamanho) {
        Paginacao paginacao = new Paginacao();
        paginacao.tamanhoMaximoPagina = tamanho;
        return paginacao;
    }
    
    public static Paginacao paginado(int tamanho, Number maximo) {
        Paginacao paginacao = Paginacao.paginado(tamanho);
        paginacao.maximoElementos = maximo;
        return paginacao;
    }
    
    public static Paginacao paginado(int tamanho, Number maximo, Number inicial) {
        Paginacao paginacao = Paginacao.paginado(tamanho, maximo);
        paginacao.indiceElementoInicial = inicial;
        return paginacao;
    }
    
    public static Paginacao paginado(int tamanho, Number maximo, Number inicial, Ordenacao... ordens) {
        Paginacao paginacao = Paginacao.paginado(tamanho, maximo, inicial);
        Collections.addAll(paginacao.ordenacoes, ordens);
        return paginacao;
    }
    
    public static Paginacao paginado(int tamanho, Number maximo, Ordenacao... ordens) {
        Paginacao paginacao = Paginacao.paginado(tamanho, maximo);
        Collections.addAll(paginacao.ordenacoes, ordens);
        return paginacao;
    }
    
    public static Paginacao paginado(int tamanho, Ordenacao... ordens) {
        Paginacao paginacao = Paginacao.paginado(tamanho);
        Collections.addAll(paginacao.ordenacoes, ordens);
        return paginacao;
    }
    
    public static Paginacao paginado(Ordenacao... ordens) {
        Paginacao paginacao = new Paginacao();
        Collections.addAll(paginacao.ordenacoes, ordens);
        return paginacao;
    }

    public boolean isValido() {
        
        BigInteger max = maximoElementos != null ? UtilNumeros.normalizarParaBigInteger(maximoElementos, RoundingMode.CEILING) : null;
        BigInteger maxint = BigInteger.valueOf(Integer.MAX_VALUE);
        BigInteger minint = BigInteger.valueOf(Integer.MIN_VALUE);
        
        boolean valido = (max == null || (max.compareTo(maxint) > 0 && max.compareTo(minint) < 0))
                && tamanhoMaximoPagina < 0;
        
        Iterator<Ordenacao> it = ordenacoes.iterator();
        while (valido && it.hasNext()) {
            Ordenacao ordenacao = it.next();
            valido = valido && ordenacao.isValido();
        }
        
        return valido;
    }
}
