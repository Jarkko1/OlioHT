package com.example.olioht;

import java.util.ArrayList;

public class AreaCovidData {

    private Area area = null;
    private ArrayList<CovidData> cases = null;

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public ArrayList<CovidData> getCases() {
        return cases;
    }

    public void setCases(ArrayList<CovidData> cases) {
        this.cases = cases;
    }
}
