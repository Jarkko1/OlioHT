package com.example.olioht;

public class Area {

    private String idnum; // id-number
    private String label; // label / area name
    private String index; // index number

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