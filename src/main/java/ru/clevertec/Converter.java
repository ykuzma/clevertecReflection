package ru.clevertec;

import lombok.AllArgsConstructor;
import ru.clevertec.core.AbstractContainer;
import ru.clevertec.core.ContainerData;
import ru.clevertec.core.service.deserialization.NodeConverter;
import ru.clevertec.core.service.deserialization.NodeConverterFactory;
import ru.clevertec.core.node.Node;
import ru.clevertec.core.service.serialization.ConverterFactory;
import ru.clevertec.core.service.serialization.ConverterToJson;
import ru.clevertec.core.JsonParser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;


@AllArgsConstructor
public class Converter {

    private final JsonParser parser;
    private final NodeConverterFactory nodeConverterFactory;

     private final ConverterFactory converterFactory;


    public <T> T mappingJsonToDomain(String json, Class<T> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        String substring = json.substring(1);
        Node parse = parser.parse(json.charAt(0), substring.toCharArray());
        ContainerData<T> containerData = new ContainerData.ContainerBuilder<>(clazz).build();
        NodeConverter nodeConverter = nodeConverterFactory.getNodeConverter(parse, containerData);
        return nodeConverter.convert(parse, containerData);
    }

    public <T> T mappingJsonToDomain(String json, AbstractContainer<T> container) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String substring = json.substring(1);
        Node parse = parser.parse(json.charAt(0), substring.toCharArray());
        ContainerData<T> containerData =  new ContainerData.ContainerBuilder<>(getContainerClass(container))
                .setTypeElementInContainer(container.getType())
                .build();
        NodeConverter nodeConverter = nodeConverterFactory.getNodeConverter(parse, containerData);
        return  nodeConverter.convert(parse, containerData);
    }

    private <T> Class<T> getContainerClass(AbstractContainer<T> container) {
        ParameterizedType type = (ParameterizedType) container.getType();

        return (Class<T>) type.getRawType();
    }

    public String mappingDomainToJson(Object object) throws IllegalAccessException {
        ConverterToJson converter = converterFactory.getConverter(object);
        return converter.convertToJson(object).toString();
    }
}
