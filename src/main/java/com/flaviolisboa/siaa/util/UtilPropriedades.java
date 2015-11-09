
package com.flaviolisboa.siaa.util;

import com.flaviolisboa.siaa.util.excecoes.ErroNegocio;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UtilPropriedades {

    public static final String[] nomesMetodoPropriedade = { "getName", "name", "value", "getNome", "nome", "valor" };
    public static final String[] nomesCamposPropriedade = { "name", "property", "value", "nome", "propriedade", "valor" };
    
    public static String getNomePropriedadeBean(Object propriedade) throws ErroNegocio {
        String nomePropriedade = null;
        
        if (propriedade != null) {
            if (propriedade instanceof String) {
                nomePropriedade = (String) propriedade;
                
            } else {
                Class<?> classe = propriedade.getClass();
                
                for (String nomeMetodo : nomesMetodoPropriedade) {
                    Method[] metodos = classe.getDeclaredMethods();
                    
                    for (Method metodo : metodos) {
                        
                        if (metodo.getName().equals(nomeMetodo)
                                && metodo.getParameterCount() == 0
                                && metodo.getReturnType().isAssignableFrom(String.class)) {
                            try {
                                nomePropriedade = (String) metodo.invoke(propriedade);
                                
                            } catch (IllegalAccessException 
                                    | IllegalArgumentException 
                                    | InvocationTargetException ex) {
                                throw new ErroNegocio(ex);
                            }
                        }
                        
                        if (nomePropriedade != null) {
                            break;
                        }
                    }
                }
                
                if (nomePropriedade == null) {
                    Field[] campos = classe.getDeclaredFields();
                    
                    for (Field campo : campos) {
                        
                        if (campo.getName().equals(propriedade)
                                && campo.getType().isAssignableFrom(String.class)) {
                            try {
                                nomePropriedade = (String) campo.get(propriedade);
                                
                            } catch (IllegalArgumentException 
                                    | IllegalAccessException ex) {
                                throw new ErroNegocio(ex);
                            }
                        }
                        
                        if (nomePropriedade != null) {
                            break;
                        }
                    }
                }
            }
        }
        
        nomePropriedade = normalizarNomePropriedadeBean(nomePropriedade);        
        return nomePropriedade;
    }
    
    public static String normalizarNomePropriedadeBean(String nomePropriedade) {
        if (nomePropriedade != null) {
            nomePropriedade = nomePropriedade.replaceFirst("^(get|set|is)", "");
            
            if (nomePropriedade.length() > 0) {
                nomePropriedade = nomePropriedade.substring(0, 1).toUpperCase() + nomePropriedade.substring(1);
            }
        }
        
        return nomePropriedade;
    }
}
