package ru.clevertec.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValueNode extends Node {

    private Object value;

    public ValueNode() {
        super(NodeType.VALUE);
    }

    public void setValueAsString(String value) {

        this.value = parse(value);
    }

    private Object parse(String value) {
        value = value.trim();
               
        if (value.matches("\".*\"")) {
            return value.substring(1, value.length() - 1);
        } else if (value.equals("true") || value.equals("false")) {
            return Boolean.parseBoolean(value);
        } else if (value.equals("null")) {
            return null;
        } else if (value.matches("-?\\d")) {
            return Integer.parseInt(value);
        } else if (value.matches("-{0,1}\\d(\\.\\d)")) {
            return Double.parseDouble(value);
        }
        throw new IllegalArgumentException();
    }

}
