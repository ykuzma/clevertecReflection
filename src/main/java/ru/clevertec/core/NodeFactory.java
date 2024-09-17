package ru.clevertec.core;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@NoArgsConstructor
public class NodeFactory {
    int countQuote = 0;

    public Node parse(char start, char[] json) {
        int index = 0;
        Node node = create(start);
        Node temp = null;
        for (int i = 0; i < json.length; i++){

            if(json[i] == '[' || json[i] == '{') {
                temp = parse(json[i], Arrays.copyOfRange(json, i + 1, json.length) );
            } else if (json[i] == ']' || json[i] == '}') {
                parseField(node, temp,  Arrays.copyOfRange(json, index, i));
                return node;
            } else if (json[i] == ',' && countQuote == 0) {
                parseField(node, temp,  Arrays.copyOfRange(json, index, i));
                index = i + 1;
            }

        }

        return node;
    }

    private void parseField(Node root, Node value, char[] field){
        if (Arrays.toString(field).contains(":")) {
            parseFieldWithKey(root, value, field);
        }else {
            parseFieldOnlyValue(root, value, field);
        }

    }
    private void parseFieldWithKey(Node root, Node value, char[] field){
        String s = new String(field);
        String[] split = s.split(":");
        String temp = split[0].trim();
        String key = temp.substring(1, temp.length() - 1);
        if(value == null) {

            ValueNode valueNode = new ValueNode();
            valueNode.setValue(split[1]);
            ((ObjectNode) root).getFields().put(key, valueNode);
        } else if (root.isObject()) {
            ((ObjectNode) root).getFields().put(key, value);
        }

    }

    private void parseFieldOnlyValue(Node root, Node value, char[] field){
        if(value == null) {
            ValueNode valueNode = new ValueNode();
            valueNode.setValue(new String(field));
            ((ArrayNode) root).getNodes().add(valueNode);
        } else if (root.isArray()) {
            ((ArrayNode) root).getNodes().add(value);
        }

    }


    public Node create(char bit){

        return switch (bit) {
            case '{' -> new ObjectNode();
            case '[' -> new ArrayNode();
            default -> new ValueNode();
        };
    }

}
