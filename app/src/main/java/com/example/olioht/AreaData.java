package com.example.olioht;

import java.util.ArrayList;

public class AreaData {

    private Area area = null; // the area
    private ArrayList<Data> cases = null; // list of numbers cases by week

    public AreaData(Area a, ArrayList<Data> c) {
        area = a;
        cases = c;
    }

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
