package ru.clevertec.domain;

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
}
