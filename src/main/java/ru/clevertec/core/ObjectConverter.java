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
                    Object valueNode = parse(((ValueNode) value).getValue(), field.getType());
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

        if (node.isArray()) {
            for (Node n : ((ArrayNode) node).getNodes()) {
                if (n.isObject()) {
                    containerData.getAddInContainer().invoke(list,
                            convert(n, (Class) containerData.getTypeElementContainer()[0]));
                }
            }
        }else {
            for (Map.Entry<String, Node> n : ((ObjectNode) node).getFields().entrySet()) {
                String key = n.getKey();
                Node value = n.getValue();
                if (value.isObject()) {
                    containerData.getAddInContainer().invoke(list,
                            parse(key, (Class<?>) containerData.getTypeElementContainer()[0]),
                            convert(value,  (Class<?>) containerData.getTypeElementContainer()[1]));
                } else if (value.isValue()) {
                    containerData.getAddInContainer().invoke(list,
                            parse(key, (Class<?>) containerData.getTypeElementContainer()[0]),
                            parse(((ValueNode)value).getValue(),  (Class<?>) containerData.getTypeElementContainer()[1]));
                }
            }
        }
        return list;
    }


    private Object parse(String value, Class<?> clazz) {
        value = value.trim();

        if (clazz.equals(String.class)) {
            return value;
        } else if (clazz.equals(boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (value.equals("null")) {
            return null;
        } else if (clazz.equals(int.class)) {
            return Integer.parseInt(value);
        } else if (clazz.equals(double.class)) {
            return Double.parseDouble(value);
        } else if (clazz.equals(UUID.class)) {

            return UUID.fromString(value);
        } else if (clazz.equals(OffsetDateTime.class)) {
            return OffsetDateTime.parse(value);
        }
        throw new IllegalArgumentException();
    }


}
