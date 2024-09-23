package ru.clevertec.core;

import lombok.NoArgsConstructor;
import ru.clevertec.core.node.Node;

import java.util.Map;

@NoArgsConstructor
public class NodeConverterFactory {

    private ConverterObjectNode objectNode;
    private ConverterValueNode valueNode;

    public NodeConverter getNodeConverter(Node node, ContainerData<?> containerData) {
        Class<?> containerClass = containerData.getContainerClass();
        if(node.isObject() && !containerClass.equals(Map.class)) {
            return getObjectNode();
        } else if (node.isValue()) {
            return getValueNode();
        }
        return null;
    }

    private ConverterObjectNode getObjectNode(){
        if(objectNode == null) objectNode = new ConverterObjectNode(this, new ContainerBuilder<>());
        return objectNode;
    }

    private ConverterValueNode getValueNode(){
        if(valueNode == null) valueNode = new ConverterValueNode();
        return valueNode;
    }
}
