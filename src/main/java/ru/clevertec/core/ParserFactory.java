package ru.clevertec.core;

import ru.clevertec.core.node.Node;

public interface ParserFactory {

    ParserJson getParser(String json);
}
