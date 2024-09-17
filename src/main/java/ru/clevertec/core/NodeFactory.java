package ru.clevertec.core;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NodeFactory {

    public Node create(char bit){

        return switch (bit) {
            case '{' -> new ObjectNode();
            case '[' -> new ArrayNode();
            default -> new ValueNode();
        };
    }

}
