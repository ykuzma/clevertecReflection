package ru.clevertec.core.service.serialization;

public interface ConverterToJson {
    char ARRAY_START = '[';
    char ARRAY_END = ']';
    char OBJECT_START = '{';
    char OBJECT_END = '}';
    char QUOTES = '"';
    char COMMA = ',';
    char COLON = ':';

    StringBuilder convertToJson(Object object) throws IllegalAccessException;

    default StringBuilder fieldNAmeToStringBuilder(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(QUOTES)
                .append(name)
                .append(QUOTES);
        return sb;
    }
}
