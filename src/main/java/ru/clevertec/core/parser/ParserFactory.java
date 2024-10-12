package ru.clevertec.core.parser;

public interface ParserFactory {

    ParserJson getParser(String json);
}
