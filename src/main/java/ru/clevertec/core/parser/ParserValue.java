package ru.clevertec.core.parser;

import ru.clevertec.core.node.Node;
import ru.clevertec.core.node.ValueNode;
import ru.clevertec.util.StringCleanerImpl;

public class ParserValue implements ParserJson{
    @Override
    public Node parse(String json) {
        ValueNode valueNode = new ValueNode();
        valueNode.setValue(new StringCleanerImpl().clean(json));
        return valueNode;
    }
}
