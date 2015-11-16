
package com.flaviolisboa.siaa.util.entidades.impl;

import javax.persistence.AttributeConverter;

public class ConversorIntBoolean implements AttributeConverter<Boolean, Integer>{

    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) {
            return null;
        }
        if (!attribute) {
            return 0;
        }
        return 1;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return 0 != dbData;
    }
}
