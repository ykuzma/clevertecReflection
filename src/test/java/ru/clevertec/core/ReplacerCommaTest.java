package ru.clevertec.core;

import org.junit.jupiter.api.Test;
import ru.clevertec.util.TestHelper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ReplacerCommaTest {

    @Test
    void replaceComma() throws IOException {

        ReplacerComma replacerComma = new ReplacerComma();
        String[] strings = replacerComma.replaceComma(new TestHelper().getTimeJsonAsString());
        System.out.println(strings);
    }
}