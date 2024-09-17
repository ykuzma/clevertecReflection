package ru.clevertec.core;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArrayNode extends Node{

    private List<Node> nodes;
    protected ArrayNode() {
        super(NodeType.ARRAY);
    }


}
