package ru.clevertec.core.parser;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.core.node.ArrayNode;
import ru.clevertec.core.node.Node;
import ru.clevertec.core.node.NodeFactory;
import ru.clevertec.core.node.ObjectNode;
import ru.clevertec.core.node.ValueNode;
import ru.clevertec.util.StringCleaner;

import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
public class Parser2 implements JsonParser2{
    private NodeFactory nodeFactory;
    private StringCleaner stringCleaner;

    @Override
    public int parse(Node node, char[] json) {
        return parse(node, json, 1);
    }

    @Override
    public int parse(Node parent, char[] json, int start) {
        int index = start;
        int countQuote = 0;
        int startElement = start;
        Node child = null;
        while (index < json.length) {

            if ((json[index] == ARRAY_START || json[index] == OBJECT_START) && countQuote % 2 == 0) {
                child = nodeFactory.getInstance(json[index]);
                index = parse(child, json, index + 1);
            } else if ((json[index] == ARRAY_END || json[index] == OBJECT_END) && countQuote % 2 == 0) {
                addElementInNode(parent, child, Arrays.copyOfRange(json, startElement, index));
                return index;
            } else if (json[index] == COMMA && countQuote % 2 == 0) {
                addElementInNode(parent, child, Arrays.copyOfRange(json, startElement, index));
                child = null;
                startElement = index + 1;
            } else if (json[index] == QUOTES && (String.valueOf(json).codePointBefore(index) != BACKSLASH)) {
                countQuote++;
            }
            index++;
        }
        return index;
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
        } else {
            ((ObjectNode) parent).getFields().put(key, child);
        }

    }
}
