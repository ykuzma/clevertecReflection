package ru.clevertec.core.node;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ArrayNode extends Node {

    private List<Node> nodes = new ArrayList<>();
    public ArrayNode() {
        super(NodeType.ARRAY);
    }


}
