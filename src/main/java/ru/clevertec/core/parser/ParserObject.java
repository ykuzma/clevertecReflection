package ru.clevertec.core.parser;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.core.node.Node;
import ru.clevertec.core.node.ObjectNode;
import ru.clevertec.util.ReplacerComma;
import ru.clevertec.util.StringCleanerImpl;

@NoArgsConstructor
@AllArgsConstructor
public class ParserObject implements ParserJson{
    private ReplacerComma replacerComma;
    private ParserFactory parserFactory;
    @Override
    public Node parse(String json) {
        ObjectNode objectNode = new ObjectNode();

        String[] strings = replacerComma.replaceComma(json);
        for (String s: strings) {
            String[] split = s.split(":", 2);
            ParserJson parser = parserFactory.getParser(split[1]);
            Node value = parser.parse(split[1]);
            objectNode.getFields().put(new StringCleanerImpl().clean(split[0]), value);
        }
        return objectNode;
    }
}
