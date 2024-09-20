package ru.clevertec.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DifferentFieldFlower {

    private UUID id;
    private int count;
    private LocalDate dateTime;
    private LocalDateTime localDateTime;
    private OffsetDateTime offsetDateTime;
    private Integer age;
    private BigDecimal fullPrice;
    private String commonName = "n{fff:ffa[m\"e";
    private String family;
    private boolean shadePreferred;
    private double price;
}
