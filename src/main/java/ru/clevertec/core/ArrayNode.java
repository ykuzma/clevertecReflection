package ru.clevertec.core;

import java.util.List;

public class ArrayNode extends Node{

    private List<Node> nodes;
    protected ArrayNode() {
        super(NodeType.ARRAY);
    }
}
