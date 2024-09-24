package ru.clevertec.core.service.serialization;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@NoArgsConstructor
@AllArgsConstructor
public class ConverterObject implements ConverterToJson{

    private ConverterFactoryImpl factory;
    @Override
    public StringBuilder convertToJson(Object object) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append(OBJECT_START);
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field: declaredFields) {
            field.setAccessible(true);
            String name = field.getName();
            Object cast = field.get(object);
            ConverterToJson converter = factory.getConverter(cast);
            StringBuilder value = converter.convertToJson(cast);
            sb.append(fieldNAmeToStringBuilder(name))
                    .append(COLON)
                    .append(value)
                    .append(COMMA);
        }
        sb.replace(sb.lastIndexOf(","), sb.length(), "}");
        return sb;
    }
}
