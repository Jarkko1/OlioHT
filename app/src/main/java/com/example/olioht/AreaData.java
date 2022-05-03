package com.example.olioht;

import java.util.ArrayList;

public class AreaData {

    private Area area = null;
    private ArrayList<Data> cases = null;

    public AreaData(Area a, ArrayList<Data> c) {
        area = a;
        cases = c;
    }
/*
    public AreaCovidData () {
        area = null;
        cases = null;
    }
*/
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public ArrayList<Data> getCases() {
        return cases;
    }

    public void setCases(ArrayList<Data> cases) {
        this.cases = cases;
    }
}
