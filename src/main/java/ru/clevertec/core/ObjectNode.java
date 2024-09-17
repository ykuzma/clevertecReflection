package ru.clevertec.core;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
public class ObjectNode extends Node {
    private Map<String, Node> fields;

    public Set<Map.Entry<String, Node>> fields(){
        return fields.entrySet();
    }


}
