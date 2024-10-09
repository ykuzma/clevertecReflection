package ru.clevertec.core;

import org.junit.jupiter.api.Test;
import ru.clevertec.core.parser.temp.ParserArray;
import ru.clevertec.core.parser.temp.ParserFactory;
import ru.clevertec.core.parser.temp.ParserFactoryImpl;
import ru.clevertec.core.parser.temp.ParserJson;
import ru.clevertec.core.parser.temp.ParserOneLineObject;
import ru.clevertec.core.parser.temp.ParserValue;

import static org.assertj.core.api.Assertions.assertThat;

class ParserFactoryImplTest {

    ParserFactory parserFactory;

    ParserFactoryImplTest(){
        parserFactory = new ParserFactoryImpl();
    }

    @Test
    void shouldGetParserArray() {
        //given
        String json = "[\"hello\"    :\"]";

        //when
        ParserJson parser = parserFactory.getParser(json);
        //then
        assertThat(parser).isInstanceOf(ParserArray.class);
    }

    @Test
    void shouldGetParserOneLine() {
        //given
        String json = "\"hello\"    :\"";

        //when
        ParserJson parser = parserFactory.getParser(json);
        //then
        assertThat(parser).isInstanceOf(ParserOneLineObject.class);
    }

    @Test
    void shouldGetParserValue() {
        //given
        String json = "2";

        //when
        ParserJson parser = parserFactory.getParser(json);
        //then
        assertThat(parser).isInstanceOf(ParserValue.class);
    }
}