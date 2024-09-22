package ru.clevertec.core.service;

public interface ConverterFactory {

    ConverterToJson getConverter(Object object);
}
