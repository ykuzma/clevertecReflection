package ru.clevertec.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InnerObjectsWithMapAndList {
    private int id;
    private UuidFlower uuid;
    private TimeFlower time;
    private Flower flower;
    private List<String> vase;
    private Map<String, Boolean> country;
}
