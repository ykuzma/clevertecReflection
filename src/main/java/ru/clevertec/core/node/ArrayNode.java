package ru.clevertec.core.node;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArrayNode extends Node {

    private List<Node> nodes = new ArrayList<>();
    public ArrayNode() {
        super(NodeType.ARRAY);
    }


}
