package ru.clevertec.core.parser;

import ru.clevertec.core.node.Node;

public interface ParserJson {

    Node parse(String json);
}
