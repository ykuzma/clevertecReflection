package ru.clevertec.util;

public interface ConverterToJson {

    StringBuilder convertToJson(Object object) throws IllegalAccessException;

    default StringBuilder fieldNAmeToStringBuilder(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append('"')
                .append(name)
                .append('"');
        return sb;
    }
}
