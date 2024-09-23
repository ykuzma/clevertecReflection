package ru.clevertec.core;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ParserFactoryImpl implements ParserFactory {

    private ParserArray parserArray;

    @Override
    public ParserJson getParser(String json) {
        json = json.trim();
        if (json.matches("^\\[.*\\]$")) {
            return getParserArray();
        }
        return null;
    }

    private ParserArray getParserArray() {
        if (parserArray == null) parserArray = new ParserArray();
        return parserArray;
    }
}
