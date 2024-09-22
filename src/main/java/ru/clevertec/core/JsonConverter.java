package ru.clevertec.core;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.clevertec.util.ConverterFactory;
import ru.clevertec.util.ConverterToJson;

@Data
@RequiredArgsConstructor
public class JsonConverter {

    private final ConverterFactory converterFactory;

    public String convertToString(Object object) throws IllegalAccessException {
        ConverterToJson converter = converterFactory.getConverter(object.getClass());
        StringBuilder builder = converter.convertToJson(object);
        return builder.toString();
    }


}
