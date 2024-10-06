package ru.clevertec.core.service.deserialization;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.core.ContainerData;
import ru.clevertec.core.node.Node;
import ru.clevertec.core.node.ObjectNode;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class ConverterObjectNode implements NodeConverter {
    private NodeConverterFactory factory;


    @Override
    public <T> T convert(Node node, ContainerData<T> container) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        Class<T> clazz = container.getContainerClass();
        T t = clazz.getConstructor().newInstance();
        Map<String, Node> nodeFields = ((ObjectNode) node).getFields();

        for (Field field: clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object fieldValue = getFieldValue(nodeFields.get(field.getName()), field);
            field.set(t, fieldValue);
        }
        return t;
    }

    private Object getFieldValue(Node node, Field field) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        ContainerData<?> containerData = new ContainerData.ContainerBuilder<>(field.getType())
                .setTypeElementInContainer(field.getGenericType())
                .build();
        NodeConverter nodeConverter = factory.getNodeConverter(node, containerData);

        return nodeConverter.convert(node, containerData);
    }

}
