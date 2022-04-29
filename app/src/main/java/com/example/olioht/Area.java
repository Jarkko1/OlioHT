package com.example.olioht;

public class Area {
    /*
    Alue-luokka; sisältää alueen tunnuksen ja nimen (sekä indeksin...?).
    Alue voi olla kunta tai sairaanhoitopiiri.
     */
    private String idnum;
    private String label;
    private String index;

    public Area(String areaId, String areaLabel, String areaIndex) {
        this.idnum = areaId;
        this.label = areaLabel;
        this.index = areaIndex;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}