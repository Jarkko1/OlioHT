package com.example.olioht;

public class Data {

    private String idnum; // id-number
    private String index; // index number
    private String label; // label, for example week number and date
    private String value; // number of cases

    public Data(String dataIdnum, String dataIndex, String dataLabel, String dataValue) {
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