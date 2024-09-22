package ru.clevertec.core;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.clevertec.core.service.ConverterFactory;
import ru.clevertec.core.service.ConverterToJson;

@Data
@RequiredArgsConstructor
public class JsonConverter {

    private final ConverterFactory converterFactory;

    public String convertToString(Object object) throws IllegalAccessException {
        ConverterToJson converter = converterFactory.getConverter(object);
        StringBuilder builder = converter.convertToJson(object);
        return builder.toString();
    }


}
