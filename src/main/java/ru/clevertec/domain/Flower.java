package ru.clevertec.domain;

import java.util.Objects;

public class Flower {

    private int id;
    private String commonName;
    private String family;
    private boolean shadePreferred;

    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public boolean isShadePreferred() {
        return shadePreferred;
    }

    public void setShadePreferred(boolean shadePreferred) {
        this.shadePreferred = shadePreferred;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flower flower = (Flower) o;
        return id == flower.id && shadePreferred == flower.shadePreferred && Objects.equals(commonName, flower.commonName) && Objects.equals(family, flower.family);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commonName, family, shadePreferred);
    }
}
