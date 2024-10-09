package ru.clevertec.core.service.deserialization;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.core.ContainerData;
import ru.clevertec.core.node.Node;
import ru.clevertec.core.node.ObjectNode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class ConverterMapNode implements NodeConverter {
    private NodeConverterFactory factory;

    @Override
    public <T> T convert(Node node, ContainerData<T> containerData) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<T> containerClass = containerData.getContainerClass();
        Class<?> implementation = containerData.getContainerClassImpl();
        T map = containerClass.cast(implementation.getConstructor().newInstance());
        Method addInMap = containerData.getAddInContainer();

        for (Map.Entry<String, Node> innerNode : ((ObjectNode) node).fields()) {
            Class<?> keyClass = (Class<?>) containerData.getTypeElementInContainer()[0];
            Object key = parseValue(innerNode.getKey(), keyClass);
            Type typeValue = containerData.getTypeElementInContainer()[1];
            Object value = getValue(typeValue, innerNode.getValue());
            addInMap.invoke(map, key, value);
        }
        return map;
    }

    private Object getValue(Type typeValue, Node nodeValue) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ContainerData<?> containerData1 = new ContainerData.ContainerBuilder<>(
                ContainerData.ContainerBuilder.extractGenericClass(typeValue))
                .setTypeElementInContainer(typeValue)
                .build();

        NodeConverter nodeConverter = factory.getNodeConverter(nodeValue, containerData1);
        return nodeConverter.convert(nodeValue, containerData1);
    }
}
