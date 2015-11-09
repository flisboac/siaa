package com.flaviolisboa.siaa.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class UtilNumeros {

    public static BigInteger normalizarParaBigInteger(Number numero, RoundingMode modo) throws ArithmeticException {
        BigInteger valor;
        
        if (numero instanceof BigDecimal) {
            if (modo != null) {
                valor = ((BigDecimal) numero).divide(BigDecimal.ONE, modo).toBigIntegerExact();
                
            } else {
                valor = ((BigDecimal) numero).toBigIntegerExact();
            }
            
        } else if (numero instanceof Double) {
            BigDecimal bd = BigDecimal.valueOf((double) numero);
            
            if (modo != null) {
                valor = bd.divide(BigDecimal.ONE, modo).toBigIntegerExact();
                
            } else {
                valor = bd.toBigIntegerExact();
            }
            
        } else if (numero instanceof Float) {
            BigDecimal bd = BigDecimal.valueOf((float) numero);
            
            if (modo != null) {
                valor = bd.divide(BigDecimal.ONE, modo).toBigIntegerExact();
                
            } else {
                valor = bd.toBigIntegerExact();
            }
            
        } else if (numero instanceof BigInteger) {
            valor = (BigInteger) numero;
            
        } else {
            valor = BigInteger.valueOf(numero.longValue());
        }
        
        return valor;
    }
    
    public static BigInteger normalizarParaBigInteger(Number numero) {
        return normalizarParaBigInteger(numero, null);
    }
    
    public static int intValueExact(BigInteger bigint) {
        if (bigint == null || bigint.bitLength() > 31) {
            throw new ArithmeticException();
        }
        return bigint.intValue();
    }
}
