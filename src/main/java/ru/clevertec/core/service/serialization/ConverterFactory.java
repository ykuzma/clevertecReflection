package ru.clevertec.core.service.serialization;

public interface ConverterFactory {

    ConverterToJson getConverter(Object object);
}
