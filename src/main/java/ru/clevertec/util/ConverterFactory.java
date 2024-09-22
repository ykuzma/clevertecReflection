package ru.clevertec.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ConverterFactory {
    private ConverterObject converterObject;
    private ConverterObjectAsString converterObjectAsString;
    private ConverterCollection converterCollection;
    private ConverterObjectWithoutQuotes withoutQuotes;


    private static final List<Class<?>> CLASS_WITH_QUOTES = List.of(String.class, UUID.class,
            OffsetDateTime.class, LocalDate.class, LocalDateTime.class,
            BigDecimal.class, BigInteger.class);
    private static final List<Class<?>> CLASS_WITHOUT_QUOTES = List.of(Integer.class, Double.class, Long.class,
            Float.class, Boolean.class);
    public ConverterToJson getConverter(Object object) {
        if (object instanceof Map) {

        } else if (object instanceof Collection<?>) {
            return getConverterCollection();
        } else if (object.getClass().isPrimitive()
            || CLASS_WITHOUT_QUOTES.stream()
                .anyMatch(aClass -> aClass.equals(object.getClass()))) {

            return getWithoutQuotes();
        } else if (CLASS_WITH_QUOTES.stream()
                .anyMatch(aClass -> aClass.equals(object.getClass()))) {
            return getConverterObjectAsString();
        }
     return getConverterObject();
    }

    private ConverterObjectWithoutQuotes getWithoutQuotes(){
        if(withoutQuotes == null) withoutQuotes = new ConverterObjectWithoutQuotes();
        return withoutQuotes;
    }
    private ConverterCollection getConverterCollection(){
        if(converterCollection == null) converterCollection = new ConverterCollection(this);
        return converterCollection;
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
