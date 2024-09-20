package ru.clevertec;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.core.ObjectConverter;
import ru.clevertec.domain.Flower;
import ru.clevertec.domain.InnerObjectsFlower;
import ru.clevertec.domain.TimeFlower;
import ru.clevertec.domain.UuidFlower;
import ru.clevertec.factory.NodeFactory;
import ru.clevertec.parsing.JsonParser;
import ru.clevertec.util.TestHelper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ConverterTest {
    ObjectMapper objectMapper;
    TestHelper helper;
    Converter converter;

    public ConverterTest() {
        converter = new Converter(new JsonParser(new NodeFactory()), new ObjectConverter());
        helper = new TestHelper();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules()
                .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

    }

    @ParameterizedTest
    @MethodSource
    void mappingJsonToDomain(Class<?> clazz, String json) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        //given
        Object o1 = objectMapper.readValue(json, clazz);

        //when
        Object o = converter.mappingJsonToDomain(json, clazz);
        //then
        assertThat(o).isEqualTo(o1);
    }

    public static Stream<Arguments> mappingJsonToDomain() throws IOException {
        return Stream.of(
                Arguments.of(Flower.class, new TestHelper().getFlowersJsonAsString()),
                Arguments.of(UuidFlower.class, new TestHelper().getUuidJsonAsString())
        );
    }


    @Test
    void mappingJsonToTimeFlower() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        //given
        TimeFlower timeFlowerExpected = objectMapper.readValue(helper.getTimeJsonAsString(), TimeFlower.class);

        //when
        TimeFlower timeFlowerActual = converter.mappingJsonToDomain(helper.getTimeJsonAsString(), TimeFlower.class);
        //then
        assertThat(timeFlowerActual).isEqualTo(timeFlowerExpected);
    }

    @Test
    void mappingJsonToInnerFlower() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        //given
        InnerObjectsFlower innerFlowerExpected = objectMapper.readValue(helper.getInnerJsonAsString(), InnerObjectsFlower.class);

        //when
        InnerObjectsFlower innerFlowerActual = converter.mappingJsonToDomain(helper.getInnerJsonAsString(), InnerObjectsFlower.class);
        //then
        assertThat(innerFlowerActual).isEqualTo(innerFlowerExpected);
    }
}