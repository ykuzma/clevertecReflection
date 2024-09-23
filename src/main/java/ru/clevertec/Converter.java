package ru.clevertec;

import lombok.AllArgsConstructor;
import ru.clevertec.core.AbstractContainer;
import ru.clevertec.core.ContainerCreator;
import ru.clevertec.core.ContainerData;
import ru.clevertec.core.ObjectConverterImpl;
import ru.clevertec.core.node.Node;
import ru.clevertec.core.service.ConverterFactory;
import ru.clevertec.core.service.ConverterToJson;
import ru.clevertec.parsing.JsonParser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;


@AllArgsConstructor
public class Converter {

    private final JsonParser parser;
    private final ObjectConverterImpl objectConverter;

     private final ConverterFactory converterFactory;
     private final ContainerCreator containerCreator;

    public <T> T mappingJsonToDomain(String json, Class<T> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        String substring = json.substring(1);
        Node parse = parser.parse(json.charAt(0), substring.toCharArray());
        return objectConverter.convert(parse, clazz);
    }

    public <T> T mappingJsonToDomain(String json, AbstractContainer<T> container) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String substring = json.substring(1);
        Node parse = parser.parse(json.charAt(0), substring.toCharArray());
        ContainerData<T> containerData = containerCreator.create(getContainerClass(container), container.getType());

        return objectConverter.convert(parse, containerData);
    }

    private <T> Class<T> getContainerClass(AbstractContainer<T> container) throws ClassNotFoundException {
        ParameterizedType type = (ParameterizedType) container.getType();
        String typeName = type.getRawType().getTypeName();


        return (Class<T>) Class.forName(typeName);
    }

    public String mappingDomainToJson(Object object) throws IllegalAccessException {
        ConverterToJson converter = converterFactory.getConverter(object);
        return converter.convertToJson(object).toString();
    }
}
