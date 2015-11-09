
package com.flaviolisboa.siaa.util.entidades;

public interface Pagina {
    
    // Valores fixos
    
    Paginacao getConfiguracaoPaginacao();
    Number getTotalElementos();
    int getTotalPaginas();
    
    // Valores por página
    
    int getIndicePagina();
    int getTamanhoPagina();
}
