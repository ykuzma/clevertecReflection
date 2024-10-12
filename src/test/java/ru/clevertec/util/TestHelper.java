package ru.clevertec.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestHelper {

    private final String FLOWERS_PATH = "src/test/resources/1.json";
    private final String UUID_PATH = "src/test/resources/Uuid.json";
    private final String TIME_PATH = "src/test/resources/time.json";
    private final String INNER_PATH = "src/test/resources/innerObjects.json";
    private final String LIST_PATH = "src/test/resources/List.json";
    private final String MAP_PATH = "src/test/resources/Map.json";
    private final String INNER_MAP_PATH = "src/test/resources/InnerMapAndList.json";
    private final String CUSTOMERS_PATH = "src/test/resources/customer.json";
    private final String LIL_PATH = "src/test/resources/ListInList.json";

    private String getJson(String path) throws IOException {
        String json;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            while (br.ready()){
                sb.append(br.readLine());
            }
            json = sb.toString();
        }
        return json;
    }
    public String getListInListJsonAsString() throws IOException {
        return getJson(LIL_PATH);
    }
    public String getFlowersJsonAsString() throws IOException {
        return getJson(FLOWERS_PATH);
    }
    public String getCustomerJsonAsString() throws IOException {
        return getJson(CUSTOMERS_PATH);
    }

    public String getInnerMapAsString() throws IOException {
        return getJson(INNER_MAP_PATH);
    }
    public String getMapJsonAsString() throws IOException {
        return getJson(MAP_PATH);
    }

    public String getUuidJsonAsString() throws IOException {
        return getJson(UUID_PATH);
    }

    public String getListAsString() throws IOException {
        return getJson(LIST_PATH);
    }
    public String getTimeJsonAsString() throws IOException {
        return getJson(TIME_PATH);
    }

    public String getInnerJsonAsString() throws IOException {
        return getJson(INNER_PATH);
    }
}
