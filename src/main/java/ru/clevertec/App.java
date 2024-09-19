package ru.clevertec;

import ru.clevertec.core.Node;
import ru.clevertec.factory.NodeFactory;
import ru.clevertec.core.ObjectConverter;
import ru.clevertec.domain.Flower;
import ru.clevertec.parsing.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class App {
    public static void main(String[] args) throws IOException,
            InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException {

        String hello = "hello";

        char c = hello.charAt(0);
        int i = hello.codePointAt(0);
        System.out.println(c == i);


        String filePath = "src/main/resources/1.json";
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

        JsonParser nf = new JsonParser(new NodeFactory());

        Converter converter = new Converter(nf, new ObjectConverter());
        Flower convert = converter.mappingJsonToDomain(json, Flower.class);

        System.out.println(convert);





    }
}
