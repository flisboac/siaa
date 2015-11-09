package com.flaviolisboa.siaa.util.entidades.impl;

import com.flaviolisboa.siaa.util.entidades.NavegacaoPagina;
import com.flaviolisboa.siaa.util.entidades.Pagina;
import com.flaviolisboa.siaa.util.entidades.Paginacao;
import com.flaviolisboa.siaa.util.excecoes.ErroNegocio;
import com.flaviolisboa.siaa.util.excecoes.ErroValidacao;
import com.flaviolisboa.siaa.util.UtilNumeros;
import java.math.BigInteger;
import java.math.RoundingMode;

public class PaginaSimples implements Pagina {
    
    private Paginacao configuracaoPaginacao;
    private BigInteger totalElementos;
    private int totalPaginas;
    
    private int indicePagina;
    private int tamanhoPagina;

    private int divisaoTamanhoPagina;
    private int divisaoTamanhoPaginaFinal;
    
    public PaginaSimples() {}
    
    public PaginaSimples(Paginacao config, Number elementos) throws ErroNegocio {
        this.configuracaoPaginacao = config;
        
        try {
            BigInteger totalElementosConfig = config.getMaximoElementos() != null ? UtilNumeros.normalizarParaBigInteger(elementos, RoundingMode.CEILING) : null;
            this.totalElementos = UtilNumeros.normalizarParaBigInteger(elementos, RoundingMode.CEILING);
            
            if (totalElementosConfig != null && totalElementosConfig.compareTo(totalElementos) < 0) {
                this.totalElementos = totalElementosConfig;
            }
            
        } catch (ArithmeticException ex) {
            throw new ErroNegocio(ex);
        }
        
        BigInteger[] divisao = this.totalElementos.divideAndRemainder(BigInteger.valueOf(config.getTamanhoPagina()));
        BigInteger valor = divisao[0];
        
        if (divisao[1].compareTo(BigInteger.ZERO) > 0) {
            valor = valor.add(BigInteger.valueOf(1));
        }

        if (this.totalElementos.compareTo(BigInteger.ZERO) < 0
                || valor.compareTo(BigInteger.ZERO) < 0) {
            // TODO Adicionar mensagem.
            throw new ErroNegocio();
        }

        try {
            this.totalPaginas = UtilNumeros.intValueExact(valor);
            this.divisaoTamanhoPagina = UtilNumeros.intValueExact(divisao[0]);
            this.divisaoTamanhoPaginaFinal = UtilNumeros.intValueExact(divisao[1]);
            if (this.divisaoTamanhoPaginaFinal == 0) {
                this.divisaoTamanhoPaginaFinal = this.divisaoTamanhoPagina;
            }

        } catch (ArithmeticException ex) {
            throw new ErroNegocio(ex);
        }

        if (this.totalPaginas <= 0
                || this.divisaoTamanhoPagina <= 0
                || this.divisaoTamanhoPaginaFinal <= 0) {
            // TODO Adicionar mensagem.
            throw new ErroNegocio();
        }
    }
    
    @Override
    public Paginacao getConfiguracaoPaginacao() {
        return configuracaoPaginacao;
    }

    public void setConfiguracaoPaginacao(Paginacao configuracaoPaginacao) {
        this.configuracaoPaginacao = configuracaoPaginacao;
    }

    @Override
    public BigInteger getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(BigInteger totalElementos) {
        this.totalElementos = totalElementos;
    }

    @Override
    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    @Override
    public int getIndicePagina() {
        return indicePagina;
    }

    public void setIndicePagina(int indicePagina) {
        this.indicePagina = indicePagina;
    }

    @Override
    public int getTamanhoPagina() {
        return tamanhoPagina;
    }

    public BigInteger getIndiceInicialPesquisa() {
        Paginacao config = this.configuracaoPaginacao;
        BigInteger retorno = config.getMaximoElementos() != null 
                ? UtilNumeros.normalizarParaBigInteger(config.getIndiceInicial(), RoundingMode.CEILING)
                : BigInteger.ZERO;
        BigInteger adicional = BigInteger.valueOf(tamanhoPagina).multiply(BigInteger.valueOf(indicePagina));
        retorno = retorno.add(adicional);
        return retorno;
    }
    
    public BigInteger getIndiceFinalPesquisa() {
        BigInteger retorno = getIndiceInicialPesquisa();
        retorno = retorno.add(BigInteger.valueOf(tamanhoPagina));
        return retorno;
    }
    
    public void setNumeroElementos(int numeroElementos) {
        this.tamanhoPagina = numeroElementos;
    }
    
    public boolean isUltimaPagina() {
        return indicePagina == this.totalPaginas - 1;
    }
    
    public boolean isPrimeiraPagina() {
        return indicePagina == 0;
    }
    
    public PaginaSimples navegar(NavegacaoPagina navegacao, int indice) {
        switch(navegacao) {
        case PRIMEIRA:
            primeira();
            break;
        case ULTIMA:
            ultima();
            break;
        case ANTERIOR:
            anterior();
            break;
        case PROXIMA:
            proxima();
            break;
        case POR_INDICE:
            moverPara(indice);
            break;
        }
        return this;
    }
    
    public PaginaSimples anterior() {
        moverPara(this.indicePagina - 1);
        return this;
    }
    
    public PaginaSimples proxima() {
        moverPara(this.indicePagina + 1);
        return this;
    }
    
    public PaginaSimples primeira() {
        this.indicePagina = 0;
        this.tamanhoPagina = this.divisaoTamanhoPagina;
        return this;
    }
    
    public PaginaSimples ultima() {
        this.indicePagina = this.totalPaginas - 1;
        this.tamanhoPagina = this.divisaoTamanhoPaginaFinal;
        return this;
    }
    
    public PaginaSimples moverPara(int indice) {
        if (indice == this.totalPaginas - 1) {
            ultima();
            
        } else if (indice == 0) {
            primeira();
            
        } else if (indice > this.totalPaginas - 1 || indice < 0) {
            // TODO adicionar mensagem.
            throw new ErroValidacao(new ArrayIndexOutOfBoundsException());
            
        } else {
            this.indicePagina = indice;
            this.tamanhoPagina = this.divisaoTamanhoPagina;
        }
        return this;
    }
}
