package com.flaviolisboa.siaa.util.entidades;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropriedadeIdentidade {

    @Documented
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Lista {
        
        public PropriedadeIdentidade[] valor();
    }
    
    public Class<?> marcador() default Void.class;
}
