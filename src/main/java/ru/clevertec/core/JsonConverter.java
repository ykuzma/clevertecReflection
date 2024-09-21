package ru.clevertec.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class JsonConverter {
    private final StringBuilder stringBuilder;
    int index = 0;

    public String convertToJson(Object object) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        if (startConvert(object)) {

        } else {
            Field[] declaredFields = object.getClass().getDeclaredFields();
            for (Field f : declaredFields) {
                f.setAccessible(true);
                String name = f.getName();

                if (isValueType(f.getType())) {
                    Object cast = f.get(object);
                    sb.append('"')
                            .append(name)
                            .append('"')
                            .append(':');
                    sb.append('"')
                            .append(cast.toString())
                            .append('"');
                    stringBuilder.insert(index, sb);
                }
            }
        }


        return stringBuilder.toString();
    }

    private boolean isValueType(Class<?> clazz) {
        return clazz.equals(int.class)
                || clazz.equals(String.class)
                || clazz.equals(UUID.class);
    }

    private boolean startConvert(Object object) {
        boolean b = false;
        if (object instanceof Collection || object.getClass().isArray()) {
            addSquareBrackets();
            b = true;
        } else {
            addCurlyBraces();
        }
        index++;
        return b;
    }

    private void addSquareBrackets() {
        stringBuilder.append("[]");

    }

    private void addCurlyBraces() {
        stringBuilder.append("{}");
    }
}
