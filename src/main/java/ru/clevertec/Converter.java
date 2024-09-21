package ru.clevertec;

import lombok.AllArgsConstructor;
import ru.clevertec.core.AbstractContainer;
import ru.clevertec.core.ContainerData;
import ru.clevertec.core.Node;
import ru.clevertec.core.ObjectConverter;
import ru.clevertec.parsing.JsonParser;

import java.lang.reflect.InvocationTargetException;


@AllArgsConstructor
public class Converter {

    private final JsonParser parser;
    private final ObjectConverter objectConverter;

    public <T> T mappingJsonToDomain(String json, Class<T> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        String substring = json.substring(1);
        Node parse = parser.parse(json.charAt(0), substring.toCharArray());
        return objectConverter.convert(parse, clazz);
    }
    public <T> T mappingJsonToDomain(String json, AbstractContainer<T> container) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String substring = json.substring(1);
        Node parse = parser.parse(json.charAt(0), substring.toCharArray());
        ContainerData<T> containerData = new ContainerData<>();
        containerData.setData(container);

        return objectConverter.convert(parse, containerData);
    }
}
