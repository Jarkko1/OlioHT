package com.example.olioht;

public class CovidData {

    private String idnum; // id-numero
    private String index; // indeksi
    private String label; // teksti, esim 2022 viikko 18
    private String value; // tapausten lukumäärä

    public CovidData(String dataIdnum, String dataIndex, String dataLabel, String dataValue) {
        idnum = dataIdnum;
        index = dataIndex;
        label = dataLabel;
        value = dataValue;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }
}