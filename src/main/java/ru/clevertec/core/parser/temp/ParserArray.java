package ru.clevertec.core.parser.temp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.clevertec.core.node.ArrayNode;
import ru.clevertec.core.node.Node;

@RequiredArgsConstructor
public class ParserArray implements ParserJson{
    private final ReplacerComma replacerComma;
    private final ParserFactory parserFactory;


    @Override
    public Node parse(String json) {
        ArrayNode arrayNode = new ArrayNode();
        String[] strings = replacerComma.replaceComma(json);

        for (String s: strings){
            ParserJson parser = parserFactory.getParser(s);
            Node value = parser.parse(s);
            arrayNode.getNodes().add(value);
        }

        return arrayNode;
    }
}
