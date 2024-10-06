package ru.clevertec.core.service.deserialization;

import lombok.NoArgsConstructor;
import ru.clevertec.core.ContainerData;
import ru.clevertec.core.node.Node;

import java.util.Map;

@NoArgsConstructor
public class NodeConverterFactory {

    private ConverterObjectNode objectNode;
    private ConverterValueNode valueNode;
    private ConverterCollectionNode collectionNode;
    private ConverterMapNode mapNode;

    public NodeConverter getNodeConverter(Node node, ContainerData<?> containerData) {
        Class<?> containerClass = containerData.getContainerClass();
        if (node.isObject() && !containerClass.equals(Map.class)) {
            return getObjectNode();
        } else if (node.isValue()) {
            return getValueNode();
        } else if (node.isArray() && !containerClass.isArray()) {
            return getCollectionNode();
        } else if (containerClass.equals(Map.class)) {
            return getMapNode();
        }
        return null;
    }

    private ConverterMapNode getMapNode() {
        if (mapNode == null) mapNode = new ConverterMapNode(this);
        return mapNode;
    }

    private ConverterCollectionNode getCollectionNode() {
        if (collectionNode == null) collectionNode = new ConverterCollectionNode(this);
        return collectionNode;
    }

    private ConverterObjectNode getObjectNode() {
        if (objectNode == null) objectNode = new ConverterObjectNode(this);
        return objectNode;
    }

    private ConverterValueNode getValueNode() {
        if (valueNode == null) valueNode = new ConverterValueNode();
        return valueNode;
    }
}
