package ru.clevertec.core.service;

public class ConverterObjectAsString implements ConverterToJson{


    @Override
    public StringBuilder convertToJson(Object object) {
        StringBuilder sb = new StringBuilder();
        sb.append('"')
                .append(object.toString())
                .append('"');
        return sb;
    }
}
