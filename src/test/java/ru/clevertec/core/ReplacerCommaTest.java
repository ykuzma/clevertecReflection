package ru.clevertec.core;

import org.junit.jupiter.api.Test;
import ru.clevertec.core.parser.temp.ReplacerComma;
import ru.clevertec.util.TestHelper;

import java.io.IOException;

class ReplacerCommaTest {

    @Test
    void replaceComma() throws IOException {

        ReplacerComma replacerComma = new ReplacerComma();
        String[] strings = replacerComma.replaceComma(new TestHelper().getTimeJsonAsString());
        System.out.println(strings);
    }
}