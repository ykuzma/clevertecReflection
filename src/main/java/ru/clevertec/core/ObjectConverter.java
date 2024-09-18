package ru.clevertec.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

public class ObjectConverter {

    public <T> T convert(Node node, Class<T> object) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor<T> constructor = object.getConstructor();

        T t = constructor.newInstance();
        Field[] fields = object.getDeclaredFields();

        if(node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            Map<String, Node> nodeFields = objectNode.getFields();
            for (Map.Entry<String, Node> name: nodeFields.entrySet()) {
                String key = name.getKey();
                Field field = Arrays.stream(fields)
                        .filter(f ->  f.getName().equals(key))
                        .findFirst()
                        .orElseThrow();
                field.setAccessible(true);

                Node value = name.getValue();
                if(value.isValue()) {
                    field.set(t, ((ValueNode) value).getValue());
                }
            }
        }

        return t;
    }

}
