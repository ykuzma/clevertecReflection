package ru.clevertec.core;

import ru.clevertec.core.node.Node;

public interface JsonParser2 {
    char ARRAY_START = '[';
    char ARRAY_END = ']';
    char OBJECT_START = '{';
    char OBJECT_END = '}';
    char QUOTES = '"';
    char COMMA = ',';
    char BACKSLASH = '\\';

    int parse(Node node, char[] json, int start);
    int parse(Node node, char[] json);

}
