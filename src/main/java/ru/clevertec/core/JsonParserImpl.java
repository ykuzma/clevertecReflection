package ru.clevertec.core;

import lombok.RequiredArgsConstructor;
import ru.clevertec.core.node.ArrayNode;
import ru.clevertec.core.node.Node;
import ru.clevertec.core.node.NodeFactory;
import ru.clevertec.core.node.ObjectNode;
import ru.clevertec.core.node.ValueNode;
import ru.clevertec.util.StringCleaner;

import java.util.Arrays;

@RequiredArgsConstructor
public class JsonParserImpl implements JsonParser {

    private final StringCleaner stringCleaner;
    private final NodeFactory nodeFactory;
    private int countQuote = 0;
    private int offset = 0;


    @Override
    public Node parse(char start, char[] json) {
        int indexStartElement = 0;
        Node parent = nodeFactory.getInstance(start);
        Node child = null;
        for (int i = 0; i < json.length; i++) {
            if ((json[i] == ARRAY_START || json[i] == OBJECT_START) && countQuote % 2 == 0) {
                child = parse(json[i], Arrays.copyOfRange(json, i + 1, json.length));
                i += offset;
            } else if ((json[i] == ARRAY_END || json[i] == OBJECT_END) && countQuote % 2 == 0) {
                addElementInNode(parent, child, Arrays.copyOfRange(json, indexStartElement, i));
                offset = i + 1;
                return parent;
            } else if (json[i] == COMMA && countQuote % 2 == 0) {
                addElementInNode(parent, child, Arrays.copyOfRange(json, indexStartElement, i));
                child = null;
                indexStartElement = i + 1;
            } else if (json[i] == QUOTES && (i == 0 || String.valueOf(json).codePointBefore(i) != BACKSLASH)) {
                countQuote++;
            }
        }
        return parent;
    }

    private void addElementInNode(Node parent, Node child, char[] field) {
        if (parent.isArray()) {
            addElementInArrayNode(parent, child, field);
        } else {
            addElementInObjectNode(parent, child, field);
        }

    }

    private void addElementInArrayNode(Node parent, Node child, char[] field) {
        if (child == null) {
            ValueNode valueNode = new ValueNode();

            valueNode.setValue(stringCleaner.clean(String.valueOf(field)));
            ((ArrayNode) parent).getNodes().add(valueNode);
        } else {
            ((ArrayNode) parent).getNodes().add(child);
        }

    }

    private void addElementInObjectNode(Node parent, Node child, char[] field) {
        String s = new String(field);
        String[] split = s.split(":", 2);

        String key = stringCleaner.clean(split[0]);
        if (child == null) {

            ValueNode valueNode = new ValueNode();
            valueNode.setValue(stringCleaner.clean(split[1]));
            ((ObjectNode) parent).getFields().put(key, valueNode);
        } else if (parent.isObject()) {
            ((ObjectNode) parent).getFields().put(key, child);
        }

    }
}
