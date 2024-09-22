package ru.clevertec.parsing;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.clevertec.core.node.ArrayNode;
import ru.clevertec.core.node.Node;
import ru.clevertec.core.node.NodeFactory;
import ru.clevertec.core.node.ObjectNode;
import ru.clevertec.core.node.ValueNode;
import ru.clevertec.util.StringCleaner;

import java.util.Arrays;

@Data
@RequiredArgsConstructor
public class JsonParser {

    private final StringCleaner stringCleaner;
    private final NodeFactory nodeFactory;
    private int countQuote = 0;
    private int offset = 0;


    public Node parse(char start, char[] json) {
        int index = 0;
        Node parent = nodeFactory.create(start);
        Node child  = null;
        for (int i = 0; i < json.length; i++) {
            if (json[i] == '[' || json[i] == '{') {
                child = parse(json[i], Arrays.copyOfRange(json, i + 1, json.length));
                i += offset + 1;
            } else if (json[i] == ']' || json[i] == '}') {
                addElementInNode(parent, child, Arrays.copyOfRange(json, index, i));
                offset = i;
                return parent;
            } else if (json[i] == ',' && countQuote % 2 == 0) {
                addElementInNode(parent, child, Arrays.copyOfRange(json, index, i));
                index = i + 1;
            } else if (json[i] == '"' && (i == 0 ||String.valueOf(json).codePointBefore(i) != '\\')) {

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
        } else{
            ((ArrayNode) parent).getNodes().add(child);
        }

    }

    private void addElementInObjectNode(Node parent, Node child, char[] field) {
        String s = new String(field);
        String[] split = s.split(":",2);

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
