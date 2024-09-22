package ru.clevertec.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@NoArgsConstructor
@AllArgsConstructor
public class ConverterObject implements ConverterToJson{

    private ConverterFactory factory;
    @Override
    public StringBuilder convertToJson(Object object) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field: declaredFields) {
            field.setAccessible(true);
            String name = field.getName();
            Object cast = field.get(object);
            ConverterToJson converter = factory.getConverter(field.getType());
            StringBuilder value = converter.convertToJson(cast);
            sb.append(fieldNAmeToStringBuilder(name))
                    .append(':')
                    .append(value)
                    .append(',');
        }
        sb.replace(sb.lastIndexOf(","), sb.length(), "}");
        return sb;
    }
}
