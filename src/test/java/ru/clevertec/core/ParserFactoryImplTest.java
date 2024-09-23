package ru.clevertec.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ParserFactoryImplTest {

    ParserFactory parserFactory;

    ParserFactoryImplTest(){
        parserFactory = new ParserFactoryImpl();
    }

    @Test
    void shouldGetParserArray() {
        //given
        String json = "[]";

        //when
        ParserJson parser = parserFactory.getParser(json);
        //then
        assertThat(parser).isInstanceOf(ParserArray.class);
    }
}