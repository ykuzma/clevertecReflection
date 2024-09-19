package ru.clevertec;

import lombok.AllArgsConstructor;
import ru.clevertec.core.Node;
import ru.clevertec.core.ObjectConverter;
import ru.clevertec.parsing.JsonParser;

import java.lang.reflect.InvocationTargetException;


@AllArgsConstructor
public class Converter {

    private final JsonParser parser;
    private final ObjectConverter converter;

    public <T> T mappingJsonToDomain(String json, Class<T> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String substring = json.substring(1);
        Node parse = parser.parse(json.charAt(0), substring.toCharArray());
        return converter.convert(parse, clazz);
    }

}
