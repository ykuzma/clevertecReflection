package ru.clevertec.core.service.serialization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConverterCollection implements ConverterToJson{

    private ConverterFactoryImpl factory;
    @Override
    public StringBuilder convertToJson(Object object) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append(ARRAY_START);
        Collection<?> collection = (Collection<?>) object;
        for (Object element: collection) {
            ConverterToJson converter = factory.getConverter(element);
            StringBuilder value = converter.convertToJson(element);
            sb.append(value)
                    .append(COMMA);
        }
        sb.replace(sb.lastIndexOf(COMMA), sb.length(), ARRAY_END);
        return sb;
    }
}
