package com.geekerstar.poi.entity;


public class PoiEntity {
    private String id;
    private String breast;
    private String adipocytes;
    private String negative;
    private String staining;
    private String supportive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBreast() {
        return breast;
    }

    public void setBreast(String breast) {
        this.breast = breast;
    }

    public String getAdipocytes() {
        return adipocytes;
    }

    public void setAdipocytes(String adipocytes) {
        this.adipocytes = adipocytes;
    }

    public String getNegative() {
        return negative;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }

    public String getStaining() {
        return staining;
    }

    public void setStaining(String staining) {
        this.staining = staining;
    }

    public String getSupportive() {
        return supportive;
    }

    public void setSupportive(String supportive) {
        this.supportive = supportive;
    }

    @Override
    public String toString() {
        return "PoiEntity{" +
                "id='" + id + '\'' +
                ", breast='" + breast + '\'' +
                ", adipocytes='" + adipocytes + '\'' +
                ", negative='" + negative + '\'' +
                ", staining='" + staining + '\'' +
                ", supportive='" + supportive + '\'' +
                '}';
    }
}
