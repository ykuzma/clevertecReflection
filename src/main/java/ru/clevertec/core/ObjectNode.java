package ru.clevertec.core;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;


@Getter
@Setter
public class ObjectNode extends Node {

    private Map<String, Node> fields;

    public ObjectNode() {
        super(NodeType.OBJECT);
    }

    public Set<Map.Entry<String, Node>> fields(){
        return fields.entrySet();
    }


}
