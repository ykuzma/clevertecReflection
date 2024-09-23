package ru.clevertec.core;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.core.node.Node;
import ru.clevertec.core.node.ObjectNode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class ConverterObjectNode implements NodeConverter {
    private NodeConverterFactory factory;
    private ContainerCreator containerCreator;

    @Override
    public <T> T convert(Node node, ContainerData<T> container) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<T> object = container.getContainerClass();
        ObjectNode objectNode = (ObjectNode) node;
        Map<String, Node> nodeFields = objectNode.getFields();
        Constructor<T> constructor = object.getConstructor();
        T t = constructor.newInstance();
        Field[] fields = object.getDeclaredFields();

        for (Field field: fields) {
            field.setAccessible(true);
            Node node1 = nodeFields.get(field.getName());
            Class<?> type = field.getType();
            ContainerData<?> containerData = containerCreator.create(type, field.getGenericType());
            NodeConverter nodeConverter = factory.getNodeConverter(node1, containerData);
            Object fieldValue = nodeConverter.convert(node1, containerData);
            field.set(t, fieldValue);
        }
        return t;
    }


}
