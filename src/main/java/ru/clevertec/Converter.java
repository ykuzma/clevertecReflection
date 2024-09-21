package ru.clevertec;

import lombok.AllArgsConstructor;
import ru.clevertec.core.AbstractContainer;
import ru.clevertec.core.ContainerData;
import ru.clevertec.core.Node;
import ru.clevertec.core.ObjectConverter;
import ru.clevertec.parsing.JsonParser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


@AllArgsConstructor
public class Converter {

    private final JsonParser parser;
    private final ObjectConverter converter;

    public <T> T mappingJsonToDomain(String json, Class<T> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        String substring = json.substring(1);
        Node parse = parser.parse(json.charAt(0), substring.toCharArray());
        return converter.convert(parse, clazz);
    }
    public <T> T mappingJsonToDomain(String json, AbstractContainer<T> container) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String substring = json.substring(1);
        Node parse = parser.parse(json.charAt(0), substring.toCharArray());
        ParameterizedType type = (ParameterizedType)container.getType();
        String typeName = type.getRawType().getTypeName();
        Class<?> aClass = Class.forName(typeName);
        Type actualTypeArgument = type.getActualTypeArguments()[0];
        ContainerData containerData = new ContainerData<>(aClass, actualTypeArgument);


        return (T)converter.convert(parse, containerData);
    }
}
