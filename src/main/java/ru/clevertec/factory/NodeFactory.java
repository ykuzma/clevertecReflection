package ru.clevertec.factory;

import ru.clevertec.core.ArrayNode;
import ru.clevertec.core.Node;
import ru.clevertec.core.ObjectNode;

public class NodeFactory {

    public Node create(char bit) {

        return switch (bit) {
            case '{' -> new ObjectNode();
            case '[' -> new ArrayNode();
            default -> throw new RuntimeException();
        };
    }
}
