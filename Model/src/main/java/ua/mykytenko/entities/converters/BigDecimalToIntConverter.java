package ua.mykytenko.entities.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;

@Converter
public class BigDecimalToIntConverter implements AttributeConverter<BigDecimal, Integer>{
    @Override
    public Integer convertToDatabaseColumn(BigDecimal attribute) {
        return attribute.multiply(BigDecimal.valueOf(100)).intValue();
    }

    @Override
    public BigDecimal convertToEntityAttribute(Integer dbData) {
        return BigDecimal.valueOf(dbData).divide(BigDecimal.valueOf(100));
    }
}
