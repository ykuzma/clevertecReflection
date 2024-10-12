package ru.clevertec.core.node;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValueNode extends Node {

    private String value;

    public ValueNode() {
        super(NodeType.VALUE);
    }





}
