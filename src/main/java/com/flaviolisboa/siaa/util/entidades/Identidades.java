package com.flaviolisboa.siaa.util.entidades;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Identidades {

    public Class<?>[] marcadores();
    public Class<?>[] marcadoresParaInclusao() default {};
    public Class<?>[] marcadoresParaAlteracao() default {};
    public Class<?>[] marcadoresParaExclusao() default {};
}
