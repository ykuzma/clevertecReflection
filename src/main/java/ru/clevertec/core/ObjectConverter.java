package ru.clevertec.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ObjectConverter {

    int i;

    public <T> T convert(Node node, Class<T> object) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {

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
                } else {

                    field.set(t, convert(value, field.getType()));
                }
            }
        } else {
            ArrayNode arrayNode = (ArrayNode) node;
            List<Node> nodes = arrayNode.getNodes();

        }

        return t;
    }

    public <T> T convert(Node node, ContainerData<T> containerData) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<T> clazz = containerData.getContainerClass();
        Class<?> implementation = containerData.getContainerClassImpl();
        T list = clazz.cast(implementation.getConstructor().newInstance());

        for (Node n : ((ArrayNode) node).getNodes()) {
            if (n.isObject()) {
                containerData.getAddInContainer().invoke(list,
                        convert(n, (Class) containerData.getTypeElementContainer()));
            }
        }
        return list;
    }


    private Object parse(String value, Field field) {
        value = value.trim();

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
        } else if (field.getType().equals(OffsetDateTime.class)) {
            return OffsetDateTime.parse(value.substring(1, value.length() - 1));
        }
        throw new IllegalArgumentException();
    }


}
