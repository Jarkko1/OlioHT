package com.example.olioht;

public class CovidData {
    /* Ajattelin että tällaiseen luokkaan voisi tallentaa yksittäisen viikon covid
    covid-tapaukset alueella. Näin ne saa datasta helposti.
    Vaatii vielä miettimistä.
     */
    private String caseAreaID;
    private int caseCount;
    private String caseTime;

    public CovidData(String areaId, int count, String time) {
        caseAreaID = areaId;
        caseCount = count;
        caseTime = time;
    }

    /* Metodeja pitää vielä, muokata (kopioitu suoraan Area-luokasta)

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
     */
}
