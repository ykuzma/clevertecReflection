package ru.clevertec.core;

import lombok.NoArgsConstructor;
import ru.clevertec.core.node.Node;

import java.util.Map;

@NoArgsConstructor
public class NodeConverterFactory {

    private ConverterObjectNode objectNode;
    private ConverterValueNode valueNode;
    private ConverterCollectionNode collectionNode;

    public NodeConverter getNodeConverter(Node node, ContainerData<?> containerData) {
        Class<?> containerClass = containerData.getContainerClass();
        if(node.isObject() && !containerClass.equals(Map.class)) {
            return getObjectNode();
        } else if (node.isValue()) {
            return getValueNode();
        } else if (node.isArray() && !containerClass.isArray()) {
            return getCollectionNode();
        }
        return null;
    }

    private ConverterCollectionNode getCollectionNode(){
        if(collectionNode == null) collectionNode = new ConverterCollectionNode(this, new ContainerBuilder<>());
        return collectionNode;
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
