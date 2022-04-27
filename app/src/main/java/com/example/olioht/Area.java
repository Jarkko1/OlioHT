package com.example.olioht;

public class Area {
    /*
    Alue-luokka; sisältää alueen tunnuksen ja nimen (sekä indeksin...?).
    Alue voi olla kunta tai sairaanhoitopiiri.
     */
    private String id;
    private String label;
    private String index;

    public Area(String areaId, String areaLabel, String areaIndex) {
        id = areaId;
        label = areaLabel;
        index = areaIndex;
    }

    public void setId(String cityId) {
        this.id = cityId;
    }

    public void setLabel(String cityLabel) {
        this.id = cityLabel;
    }

    public String getId() {
        return(this.id);
    }

    public String getLabel() {
        return(this.label);
    }

    public String getIndex() {
        return(this.index);
    }
}