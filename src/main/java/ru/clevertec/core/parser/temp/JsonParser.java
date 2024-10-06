package ru.clevertec.core.parser.temp;

import ru.clevertec.core.node.Node;

public interface JsonParser {
    char ARRAY_START = '[';
    char ARRAY_END = ']';
    char OBJECT_START = '{';
    char OBJECT_END = '}';
    char QUOTES = '"';
    char COMMA = ',';
    char BACKSLASH = '\\';

    Node parse(char start, char[]json);
}
