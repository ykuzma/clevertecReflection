package ru.clevertec.core;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.core.node.Node;
import ru.clevertec.core.node.ObjectNode;
import ru.clevertec.core.node.ValueNode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class ConverterMapNode implements NodeConverter{

    private NodeConverterFactory factory;
    private ContainerCreator containerCreator;

    @Override
    public <T> T convert(Node node, ContainerData<T> containerData) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<T> containerClass = containerData.getContainerClass();
        Class<?> implementation = containerData.getContainerClassImpl();
        T map = containerClass.cast(implementation.getConstructor().newInstance());
        Method addInContainer = containerData.getAddInContainer();

        ObjectNode objectNode = (ObjectNode) node;
        for (Map.Entry<String, Node> n : objectNode.getFields().entrySet()) {
            Node nodeValue = n.getValue();
            Object key = parseValue(n.getKey(), (Class<?>) containerData.getTypeElementInContainer()[0]);
            ContainerData<?> containerData1 = containerCreator.create((Class<?>) containerData.getTypeElementInContainer()[1],
                    containerData.getTypeElementInContainer()[1]);
            NodeConverter nodeConverter = factory.getNodeConverter(nodeValue, containerData1);
            Object value = nodeConverter.convert(nodeValue, containerData1);

            addInContainer.invoke(map, key, value);
        }
        return map;
    }
}
