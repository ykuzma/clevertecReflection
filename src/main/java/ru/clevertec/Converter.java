package ru.clevertec;

import lombok.AllArgsConstructor;
import ru.clevertec.core.AbstractContainer;
import ru.clevertec.core.ContainerData;
import ru.clevertec.core.node.Node;
import ru.clevertec.core.parser.ParserFactory;
import ru.clevertec.core.service.deserialization.NodeConverter;
import ru.clevertec.core.service.deserialization.NodeConverterFactory;
import ru.clevertec.core.service.serialization.ConverterFactory;
import ru.clevertec.core.service.serialization.ConverterToJson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;


@AllArgsConstructor
public class Converter {

    private final ParserFactory parserFactory;
    private final NodeConverterFactory nodeConverterFactory;
    private final ConverterFactory converterFactory;


    public <T> T mappingJsonToDomain(String json, Class<T> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        ContainerData<T> containerData = new ContainerData.ContainerBuilder<>(clazz).build();
        return jsonToDomain(json, containerData);
    }

    public <T> T mappingJsonToDomain(String json, AbstractContainer<T> container) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ContainerData<T> containerData = new ContainerData.ContainerBuilder<>(getContainerClass(container))
                .setTypeElementInContainer(container.getType())
                .build();
        return jsonToDomain(json, containerData);
    }
    private <T> T jsonToDomain(String json, ContainerData<T> containerData) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        Node parentNode = parserFactory.getParser(json).parse(json);
        NodeConverter nodeConverter = nodeConverterFactory.getNodeConverter(parentNode, containerData);
        return  nodeConverter.convert(parentNode, containerData);
    }

    public String mappingDomainToJson(Object object) throws IllegalAccessException {
        ConverterToJson converter = converterFactory.getConverter(object);
        return converter.convertToJson(object).toString();
    }



    private <T> Class<T> getContainerClass(AbstractContainer<T> container) {
        ParameterizedType type = (ParameterizedType) container.getType();

        return (Class<T>) type.getRawType();
    }


}
