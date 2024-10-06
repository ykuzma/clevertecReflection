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
public class ConverterMapNode implements NodeConverter{

    private NodeConverterFactory factory;


    @Override
    public <T> T convert(Node node, ContainerData<T> containerData) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<T> containerClass = containerData.getContainerClass();
        Class<?> implementation = containerData.getContainerClassImpl();
        T map = containerClass.cast(implementation.getConstructor().newInstance());
        Method addInContainer = containerData.getAddInContainer();

        ObjectNode objectNode = (ObjectNode) node;
        for (Map.Entry<String, Node> n : objectNode.getFields().entrySet()) {
            Node nodeValue = n.getValue();
            Type type = containerData.getTypeElementInContainer()[1];
            Object key = parseValue(n.getKey(), (Class<?>) containerData.getTypeElementInContainer()[0]);
            ContainerData<?> containerData1 = new ContainerData.ContainerBuilder<>(
                    ContainerData.ContainerBuilder.extractGenericClass(type))
                    .setTypeElementInContainer(type)
                    .build();
            NodeConverter nodeConverter = factory.getNodeConverter(nodeValue, containerData1);
            Object value = nodeConverter.convert(nodeValue, containerData1);

            addInContainer.invoke(map, key, value);
        }
        return map;
    }
}
