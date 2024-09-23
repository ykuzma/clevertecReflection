package ru.clevertec.core.service.json;

public interface ConverterFactory {

    ConverterToJson getConverter(Object object);
}
