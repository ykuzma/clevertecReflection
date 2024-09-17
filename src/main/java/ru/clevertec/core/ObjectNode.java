package ru.clevertec.core;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;


public class ObjectNode extends Node {

    private Map<String, Node> fields;

    public ObjectNode() {
        super(NodeType.OBJECT);
    }

    public Set<Map.Entry<String, Node>> fields(){
        return fields.entrySet();
    }


}
