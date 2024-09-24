package ru.clevertec;

import lombok.AllArgsConstructor;
import ru.clevertec.core.AbstractContainer;
import ru.clevertec.core.ContainerCreator;
import ru.clevertec.core.ContainerData;
import ru.clevertec.core.JsonParser;
import ru.clevertec.core.node.Node;
import ru.clevertec.core.service.deserialization.NodeConverter;
import ru.clevertec.core.service.deserialization.NodeConverterFactory;
import ru.clevertec.core.service.serialization.ConverterFactory;
import ru.clevertec.core.service.serialization.ConverterToJson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;


@AllArgsConstructor
public class Converter {

    private final JsonParser parser;
    private final NodeConverterFactory nodeConverterFactory;

    private final ConverterFactory converterFactory;
    private final ContainerCreator containerCreator;

    public <T> T mappingJsonToDomain(String json, Class<T> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        ContainerData<T> containerData = containerCreator.create(clazz, null);
        return jsonToDomain(json, containerData);
    }

    public <T> T mappingJsonToDomain(String json, AbstractContainer<T> container) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ContainerData<T> containerData = containerCreator.create(getContainerClass(container), container.getType());
        return jsonToDomain(json, containerData);
    }

    public String mappingDomainToJson(Object object) throws IllegalAccessException {
        ConverterToJson converter = converterFactory.getConverter(object);
        return converter.convertToJson(object).toString();
    }

    private <T> T jsonToDomain(String json, ContainerData<T> containerData) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        String substring = json.substring(1);
        Node parse = parser.parse(json.charAt(0), substring.toCharArray());
        NodeConverter nodeConverter = nodeConverterFactory.getNodeConverter(parse, containerData);
        return nodeConverter.convert(parse, containerData);
    }

    private <T> Class<T> getContainerClass(AbstractContainer<T> container) throws ClassNotFoundException {
        ParameterizedType type = (ParameterizedType) container.getType();
        String typeName = type.getRawType().getTypeName();

        return (Class<T>) Class.forName(typeName);
    }


}
