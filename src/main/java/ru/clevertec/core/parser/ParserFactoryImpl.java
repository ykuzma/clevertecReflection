package ru.clevertec.core.parser;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.util.ReplacerComma;

@NoArgsConstructor
@AllArgsConstructor
public class ParserFactoryImpl implements ParserFactory {

    private ParserArray parserArray;
    private ParserObject parserObject;
    private ParserOneLineObject oneLineObject;
    private ParserValue parserValue;

    @Override
    public ParserJson getParser(String json) {
        json = json.trim();
        if (json.matches("^\\[.*\\]$")) {
            return getParserArray();
        } else if (json.matches("^\\{.*\\}$")) {
            return getParserObject();
        } else if (json.matches("\".*[^\\(\\\\)]\"\\s*:\\s*.+")) {
            return getOneLineObject();
        } else if (json.matches("(\".*\")|true|false|null|(\\d+)|(\\d+\\.\\d+)")){
            return getParserValue();
        }

        throw  new IllegalArgumentException();
    }

    private ParserJson getParserValue() {
        if(parserValue == null) parserValue = new ParserValue();
        return parserValue;
    }

    private ParserJson getOneLineObject() {
        if(oneLineObject == null) oneLineObject = new ParserOneLineObject();
        return oneLineObject;
    }

    private ParserJson getParserObject() {
        if(parserObject == null) parserObject = new ParserObject(new ReplacerComma(), this);
        return parserObject;
    }

    private ParserJson getParserArray() {
        if (parserArray == null) parserArray = new ParserArray(new ReplacerComma(), this);
        return parserArray;
    }
}
