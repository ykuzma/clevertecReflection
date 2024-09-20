package ru.clevertec.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flower {

    private int id;
    private String commonName;
    private String family;
    private boolean shadePreferred;
    private double price;


}
