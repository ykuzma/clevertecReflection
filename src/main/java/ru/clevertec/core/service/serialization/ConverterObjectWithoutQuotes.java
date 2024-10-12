package ru.clevertec.core.service.serialization;

public class ConverterObjectWithoutQuotes implements ConverterToJson{
    @Override
    public StringBuilder convertToJson(Object object){
        StringBuilder sb = new StringBuilder();
        sb.append(object.toString());
        return sb;
    }
}
