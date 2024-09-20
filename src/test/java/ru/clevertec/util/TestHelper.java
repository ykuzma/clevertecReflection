package ru.clevertec.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestHelper {

    private final String FLOWERS_PATH = "src/test/resources/1.json";
    private final String UUID_PATH = "src/test/resources/Uuid.json";
    private final String TIME_PATH = "src/test/resources/time.json";
    private final String INNER_PATH = "src/test/resources/innerObjects.json";

    public String getFlowersJsonAsString() throws IOException {
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

    public String getUuidJsonAsString() throws IOException {
        String json;
        try (BufferedReader br = new BufferedReader(new FileReader(UUID_PATH))) {
            StringBuilder sb = new StringBuilder();
            while (br.ready()){
                sb.append(br.readLine());
            }
            json = sb.toString();
        }
        return json;
    }
    public String getTimeJsonAsString() throws IOException {
        String json;
        try (BufferedReader br = new BufferedReader(new FileReader(TIME_PATH))) {
            StringBuilder sb = new StringBuilder();
            while (br.ready()){
                sb.append(br.readLine());
            }
            json = sb.toString();
        }
        return json;
    }

    public String getInnerJsonAsString() throws IOException {
        String json;
        try (BufferedReader br = new BufferedReader(new FileReader(INNER_PATH))) {
            StringBuilder sb = new StringBuilder();
            while (br.ready()){
                sb.append(br.readLine());
            }
            json = sb.toString();
        }
        return json;
    }
}
