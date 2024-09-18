package ru.clevertec.core;

public class NodeFactory {

    public Node create(char bit) {

        return switch (bit) {
            case '{' -> new ObjectNode();
            case '[' -> new ArrayNode();
            default -> new ValueNode();
        };
    }
}
