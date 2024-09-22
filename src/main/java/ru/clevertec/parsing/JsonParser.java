package ru.clevertec.parsing;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.clevertec.core.ArrayNode;
import ru.clevertec.core.Node;
import ru.clevertec.factory.NodeFactory;
import ru.clevertec.core.ObjectNode;
import ru.clevertec.core.ValueNode;

import java.util.Arrays;

@Data
@RequiredArgsConstructor
public class JsonParser {

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
            String value = String.valueOf(field).trim();
            if(value.matches("^\".*\"$")) {
                value = value.substring(1, value.length() - 1);
            }
            valueNode.setValue(value);
            ((ArrayNode) parent).getNodes().add(valueNode);
        } else{
            ((ArrayNode) parent).getNodes().add(child);
        }

    }

    private void addElementInObjectNode(Node parent, Node child, char[] field) {
        String s = new String(field);
        String[] split = s.split(":",2);
        String temp = split[0].trim();
        String key = temp.substring(1, temp.length() - 1);
        if (child == null) {
            String value = split[1].trim();
            if(value.matches("^\".*\"$")) {
                value = value.substring(1, value.length() - 1);
            }
            ValueNode valueNode = new ValueNode();
            valueNode.setValue(value);
            ((ObjectNode) parent).getFields().put(key, valueNode);
        } else if (parent.isObject()) {
            ((ObjectNode) parent).getFields().put(key, child);
        }

    }



}
