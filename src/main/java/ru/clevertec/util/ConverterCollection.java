package ru.clevertec.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConverterCollection implements ConverterToJson{

    private ConverterFactory factory;
    @Override
    public StringBuilder convertToJson(Object object) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Collection<?> collection = (Collection<?>) object;
        for (Object element: collection) {
            ConverterToJson converter = factory.getConverter(element);
            StringBuilder value = converter.convertToJson(element);
            sb.append(value)
                    .append(',');
        }
        sb.replace(sb.lastIndexOf(","), sb.length(), "]");
        return sb;
    }
}
