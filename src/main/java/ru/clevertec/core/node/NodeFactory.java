package ru.clevertec.core.node;

public class NodeFactory {

    public Node create(char bit) {

        return switch (bit) {
            case '{' -> new ObjectNode();
            case '[' -> new ArrayNode();
            default -> throw new RuntimeException();
        };
    }
}
