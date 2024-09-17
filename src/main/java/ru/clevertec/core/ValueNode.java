package ru.clevertec.core;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValueNode extends Node{

    private String value;

    public ValueNode(){
        super(NodeType.VALUE);
    }

}
