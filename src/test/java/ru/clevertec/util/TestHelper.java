package ru.clevertec.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestHelper {

    private final String FLOWERS_PATH = "src/test/resources/1.json";

    public String getJsonAsString() throws IOException {
        String json;
        try (BufferedReader br = new BufferedReader(new FileReader(FLOWERS_PATH))) {
            StringBuilder sb = new StringBuilder();
            while (br.ready()){
                sb.append(br.readLine());
            }
            json = sb.toString();
        }


        return json;
    }
}
