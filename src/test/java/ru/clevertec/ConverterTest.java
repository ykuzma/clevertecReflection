package ru.clevertec;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.clevertec.core.ObjectConverter;
import ru.clevertec.domain.Flower;
import ru.clevertec.factory.NodeFactory;
import ru.clevertec.parsing.JsonParser;
import ru.clevertec.util.TestHelper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class ConverterTest {
    ObjectMapper objectMapper;
    TestHelper helper;
    Converter converter;

    public ConverterTest() {
        converter = new Converter(new JsonParser(new NodeFactory()), new ObjectConverter());
        helper = new TestHelper();
        objectMapper = new ObjectMapper();
    }

    @Test
    void mappingJsonToDomain() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        //given
        String json = helper.getJsonAsString();
        Flower expectedFlower = objectMapper.readValue(json, Flower.class);
        //when
        Flower actualFlower = converter.mappingJsonToDomain(json, Flower.class);
        //then
        assertThat(actualFlower).isEqualTo(expectedFlower);
    }
}