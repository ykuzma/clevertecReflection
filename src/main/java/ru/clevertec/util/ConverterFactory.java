package ru.clevertec.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ConverterFactory {
    private ConverterObject converterObject;
    private ConverterObjectAsString converterObjectAsString;


    private static final List<Class<?>> CLASS_AS_STRING = Arrays.asList(new Class[]{String.class, UUID.class,
            OffsetDateTime.class, LocalDate.class, LocalDateTime.class,
            BigDecimal.class, BigInteger.class, Integer.class, Double.class, Long.class,
            Float.class, Boolean.class});

    public ConverterToJson getConverter(Class<?> clazz) {
        if (clazz.equals(Map.class)) {

        } else if (clazz.isArray()
                || clazz.equals(List.class)
                || clazz.equals(Set.class)) {

        } else if (clazz.isPrimitive()
            || CLASS_AS_STRING.stream()
                .anyMatch(aClass -> aClass.equals(clazz))) {

            return getConverterObjectAsString();
        }else{
            return getConverterObject();
        }
        return null;
    }

    private ConverterObject getConverterObject() {
        if (converterObject == null) converterObject = new ConverterObject(this);
        return converterObject;
    }
    private ConverterObjectAsString getConverterObjectAsString() {
        if (converterObjectAsString == null) converterObjectAsString = new ConverterObjectAsString();
        return converterObjectAsString;
    }


}
