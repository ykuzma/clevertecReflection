package ru.clevertec.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ObjectConverter {

    int i;

    public <T> T convert(Node node, Class<T> object) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor<T> constructor = object.getConstructor();

        T t = constructor.newInstance();
        Field[] fields = object.getDeclaredFields();

        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            Map<String, Node> nodeFields = objectNode.getFields();
            for (Map.Entry<String, Node> fieldNode : nodeFields.entrySet()) {
                String key = fieldNode.getKey();
                Field field = Arrays.stream(fields)
                        .filter(f -> f.getName().equals(key))
                        .findFirst()
                        .orElseThrow();
                field.setAccessible(true);
                Node value = fieldNode.getValue();
                if (value.isValue()) {
                    Object valueNode = parse(((ValueNode) value).getValue(), field);
                    field.set(t, valueNode);
                }
            }
        }

        return t;
    }


    private Object parse(String value, Field field) {
        value  = value.trim();

        if (field.getType().equals(String.class)) {
            return value.substring(1, value.length() - 1);
        } else if (field.getType().equals(boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (value.equals("null")) {
            return null;
        } else if (field.getType().equals(int.class)) {
            return Integer.parseInt(value);
        } else if (field.getType().equals(double.class)) {
            return Double.parseDouble(value);
        } else if (field.getType().equals(UUID.class)) {

            return UUID.fromString(value.substring(1, value.length() - 1));
        }
        throw new IllegalArgumentException();
    }


}
