package ru.clevertec.core.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class ConverterMap implements ConverterToJson{

    private ConverterFactoryImpl factory;
    @Override
    public StringBuilder convertToJson(Object object) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        Map<?,?> collection = (Map<?,?>) object;
        for (Map.Entry<?, ?> entry: collection.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            ConverterToJson converter = factory.getConverter(value);

            sb.append(fieldNAmeToStringBuilder(key.toString()))
                    .append(':')
                    .append(converter.convertToJson(value))
                    .append(',');
        }
        sb.replace(sb.lastIndexOf(","), sb.length(), "}");
        return sb;
    }
}
