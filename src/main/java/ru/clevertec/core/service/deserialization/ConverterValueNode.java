package ru.clevertec.core.service.deserialization;

import ru.clevertec.core.ContainerData;
import ru.clevertec.core.node.Node;
import ru.clevertec.core.node.ValueNode;

public class ConverterValueNode implements NodeConverter{
    @Override
    public <T> T convert(Node node, ContainerData<T> containerData) {
        ValueNode valueNode = (ValueNode) node;
        Class<T> containerClass = containerData.getContainerClass();

        return (T) parseValue(valueNode.getValue(), containerClass);
    }


}
