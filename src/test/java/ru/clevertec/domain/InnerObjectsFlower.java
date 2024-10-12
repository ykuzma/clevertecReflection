package ru.clevertec.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InnerObjectsFlower {
    private int id;
    private UuidFlower uuid;
    private TimeFlower time;
    private Flower flower;

}
