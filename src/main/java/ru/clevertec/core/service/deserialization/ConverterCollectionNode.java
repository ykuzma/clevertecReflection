package ru.clevertec.core.service.deserialization;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.core.ContainerData;
import ru.clevertec.core.ContainerData.ContainerBuilder;
import ru.clevertec.core.node.ArrayNode;
import ru.clevertec.core.node.Node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@NoArgsConstructor
@AllArgsConstructor
public class ConverterCollectionNode implements NodeConverter {
    private NodeConverterFactory factory;

    @Override
    public <T> T convert(Node node, ContainerData<T> containerData) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<T> containerClass = containerData.getContainerClass();
        Class<?> implementation = containerData.getContainerClassImpl();
        T collection = containerClass.cast(implementation.getConstructor().newInstance());
        Method addInCollection = containerData.getAddInContainer();

        for (Node innerNode : ((ArrayNode) node).getNodes()) {
            Type innerNodeType = containerData.getTypeElementInContainer()[0];
            Object collectionValue = getValue(innerNodeType, innerNode);
            addInCollection.invoke(collection, collectionValue);
        }
        return collection;
    }

    private Object getValue(Type type, Node node) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = ContainerBuilder.extractGenericClass(type);
        ContainerData<?> containerData1 = new ContainerBuilder<>(clazz)
                .setTypeElementInContainer(type)
                .build();

        NodeConverter nodeConverter = factory.getNodeConverter(node, containerData1);
        return nodeConverter.convert(node, containerData1);
    }
}
