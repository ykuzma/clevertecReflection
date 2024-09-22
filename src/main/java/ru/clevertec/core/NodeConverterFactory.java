package ru.clevertec.core;

import ru.clevertec.core.node.Node;

import java.util.Map;

public class NodeConverterFactory {
    private ConverterObjectNode objectNode;

    public NodeConverter getNodeConverter(Node node, ContainerData<?> containerData) {
        Class<?> containerClass = containerData.getContainerClass();
        if(node.isObject() && !containerClass.equals(Map.class)) {
            return getObjectNode();
        }
        return null;
    }

    private ConverterObjectNode getObjectNode(){
        if(objectNode == null) objectNode = new ConverterObjectNode(this);
        return objectNode;
    }
}
