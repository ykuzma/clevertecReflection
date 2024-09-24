package ru.clevertec.core.service.serialization;

public class ConverterObjectAsString implements ConverterToJson{


    @Override
    public StringBuilder convertToJson(Object object) {
        StringBuilder sb = new StringBuilder();
        sb.append(QUOTES)
                .append(object.toString())
                .append(QUOTES);
        return sb;
    }
}
