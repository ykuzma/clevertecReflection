package ru.clevertec;

import lombok.AllArgsConstructor;
import ru.clevertec.core.AbstractContainer;
import ru.clevertec.core.ContainerBuilder;
import ru.clevertec.core.ContainerData;
import ru.clevertec.core.JsonConverter;
import ru.clevertec.core.Node;
import ru.clevertec.core.ObjectConverter;
import ru.clevertec.parsing.JsonParser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


@AllArgsConstructor
public class Converter {

    private final JsonParser parser;
    private final ObjectConverter objectConverter;

    private final JsonConverter jsonConverter;

    public <T> T mappingJsonToDomain(String json, Class<T> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        String substring = json.substring(1);
        Node parse = parser.parse(json.charAt(0), substring.toCharArray());
        return objectConverter.convert(parse, clazz);
    }

    public <T> T mappingJsonToDomain(String json, AbstractContainer<T> container) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String substring = json.substring(1);
        Node parse = parser.parse(json.charAt(0), substring.toCharArray());

        ContainerData<T> containerData = getContainerData(container);

        return objectConverter.convert(parse, containerData);
    }

    private <T> ContainerData<T> getContainerData(AbstractContainer<T> container) throws ClassNotFoundException, NoSuchMethodException {
        ParameterizedType type = (ParameterizedType) container.getType();
        String typeName = type.getRawType().getTypeName();
        Class<?> aClass = Class.forName(typeName);
        Type[] actualTypeArguments = type.getActualTypeArguments();

        return new ContainerBuilder<T>()
                .setContainerClass((Class<T>) aClass)
                .setTypeElementInContainer(actualTypeArguments)
                .build();
    }

    public String mappingDomainToJson(Object object) throws IllegalAccessException {
        return jsonConverter.convertToString(object);
    }
}
