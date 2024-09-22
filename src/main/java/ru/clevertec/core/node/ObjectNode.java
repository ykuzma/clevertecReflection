package ru.clevertec.core.node;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


@Getter
@Setter
public class ObjectNode extends Node {

    private Map<String, Node> fields;

    public ObjectNode() {
        super(NodeType.OBJECT);
        fields = new LinkedHashMap<>();
    }

    public Set<Map.Entry<String, Node>> fields(){
        return fields.entrySet();
    }




}
