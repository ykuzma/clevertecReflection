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
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class ConverterCollectionNode implements NodeConverter {

    private NodeConverterFactory factory;


    @Override
    public <T> T convert(Node node, ContainerData<T> containerData) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<T> containerClass = containerData.getContainerClass();
        Class<?> implementation = containerData.getContainerClassImpl();
        T list = containerClass.cast(implementation.getConstructor().newInstance());
        ArrayNode arrayNode = (ArrayNode) node;
        List<Node> nodes = arrayNode.getNodes();
        Method addInContainer = containerData.getAddInContainer();
        for (Node n : nodes) {
            Type type = containerData.getTypeElementInContainer()[0];
            Class<?> aClass = ContainerBuilder.extractGenericClass(type);
            ContainerData<?> containerData1 = new ContainerBuilder<>(aClass)
                    .setTypeElementInContainer(type)
                    .build();



            NodeConverter nodeConverter = factory.getNodeConverter(n, containerData1);
            Object value = nodeConverter.convert(n, containerData1);
            addInContainer.invoke(list, value);
        }

        return list;
    }
}
