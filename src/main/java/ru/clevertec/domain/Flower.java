package ru.clevertec.domain;

import lombok.Data;

import java.util.List;

@Data
public class Flower {
    List<String> vase;

    public Flower(List<String> vase) {
        this.vase = vase;
    }
}
