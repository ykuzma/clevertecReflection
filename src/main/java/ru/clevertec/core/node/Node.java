package ru.clevertec.core.node;

public abstract class Node {
    protected final NodeType nodeType;

    protected Node(NodeType type) {
        nodeType = type;
    }

    public boolean isArray() {
        return nodeType == NodeType.ARRAY;
    }

    public boolean isObject() {
        return nodeType == NodeType.OBJECT;
    }

    public boolean isValue() {
        return nodeType == NodeType.VALUE;
    }

}
