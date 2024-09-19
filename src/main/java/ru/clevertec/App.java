package ru.clevertec;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.core.Node;
import ru.clevertec.domain.UuidFlower;
import ru.clevertec.factory.NodeFactory;
import ru.clevertec.core.ObjectConverter;
import ru.clevertec.domain.Flower;
import ru.clevertec.parsing.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class App {
    public static void main(String[] args) throws IOException,
            InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException {


        String filePath = "src/test/resources/Uuid.json";
        String json = null;
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder builder = new StringBuilder();
            while (br.ready()) {
                builder.append(br.readLine());
            }
            json = builder.toString();
        }

        String s = "{" +
                "\"name\": \"ashton\"," +
                "\"title\":\"king\"," +
                "\"race\": \"HUMAN\"," +
                "\"profession\": \"CLERIC\"," +
                "\"birthday\" : 988059600000," +
                "\"banned\":false," +
                "\"experience\": 63986" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        UuidFlower uuidFlower = objectMapper.readValue(json, UuidFlower.class);
        System.out.println(new ObjectMapper().writeValueAsString(uuidFlower));

        JsonParser nf = new JsonParser(new NodeFactory());

        Converter converter = new Converter(nf, new ObjectConverter());
        Flower convert = converter.mappingJsonToDomain(json, Flower.class);

        System.out.println(convert);

    }
}
