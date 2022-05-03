package com.example.olioht;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CovidCenter {

    private static CovidCenter C = null;

    private ArrayList<Area> areaList = null; // List of all areas
    private ArrayList<String> areaLabels = null; // Area labels as strings for the search page
    private ArrayList<AreaCovidData> pinnedAreaCovidData = null; // Areas pinned to the home page
    private AreaCovidData currentAreaData = null;

    public static CovidCenter getInstance() {
        if (C == null) {
            C = new CovidCenter();
        }
        return(C);
    }

    private CovidCenter() {
        areaList = CovidAPI.getAreaList();
        areaLabels = areaLabelsList();
        pinnedAreaCovidData = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            pinnedAreaCovidData.add(CovidAPI.getAreaCovidData(areaList.get(i)));
        }
    }

    public void searchData(String areaLabel) {
        Area area = convertLabelToArea(areaLabel);
        currentAreaData = CovidAPI.getAreaCovidData(area);
    }

    private String convertLabelToIdnum(String label) {
        /* tällä voi muuntaa alueen nimen (vaikka "Helsinki")
        sitä vastaavaksi id numeroksi */
        String idnum = null;
        for (int i = 0; i < areaList.size(); i++) {
            if (areaList.get(i).getLabel() == label) {
                idnum = areaList.get(i).getIdnum();
            }
        }
        return(idnum);
    }

    private Area convertLabelToArea(String areaLabel) {
        /* tällä voi muuntaa alueen nimen (vaikka "Helsinki")
        sitä vastaavaksi id numeroksi */
        Area area = null;
        for (int i = 0; i < areaList.size(); i++) {
            if (areaList.get(i).getLabel() == areaLabel) {
                area = areaList.get(i);
            }
        }
        return(area);
    }

    public ArrayList<Area> getAreaList() {
        return(areaList);
    }

    public void addPinnedArea(AreaCovidData areaCovidData) {
        pinnedAreaCovidData.add(areaCovidData);
    }

    public ArrayList<AreaCovidData> getPinnedAreaCovidData() {
        return(pinnedAreaCovidData);
    }

    public AreaCovidData getSomeAreaCovidData() {
        Area area = areaList.get(19);
        AreaCovidData areaCovidData = CovidAPI.getAreaCovidData(area);
        return areaCovidData;
    }

    public ArrayList<String> areaLabelsList() {
        areaLabels = new ArrayList<String>();
        for (int i = 0; i < areaList.size(); i++) {
            areaLabels.add(areaList.get(i).getLabel());
        }
        if (areaLabels.size() > 0) {
            Collections.sort(areaLabels);
            Set<String> set = new LinkedHashSet<String>();
            set.addAll(areaLabels);
            areaLabels.clear();
            areaLabels.addAll(set);
        }
        return areaLabels;
    }
    public ArrayList<String> getAreaLabels() {
        return areaLabels;
    }

    public AreaCovidData getCurrentAreaData() {
        return currentAreaData;
    }

    public void setCurrentAreaData(AreaCovidData currentAreaData) {
        this.currentAreaData = currentAreaData;
    }

    public void test() {
        areaList = null;
        AreaCovidData areaCovidData = null;
        areaCovidData = CovidAPI.getAreaCovidData(areaList.get(0));
    }

    public boolean isThisPinned(AreaCovidData areaData) {
        boolean pinned = false;
        for (int i = 0; i < pinnedAreaCovidData.size(); i++) {
            AreaCovidData pinnedAreaData = pinnedAreaCovidData.get(i);
            String thisAreaIdnum = areaData.getArea().getIdnum();
            String pinnedAreaIdnum = pinnedAreaData.getArea().getIdnum();
            if (thisAreaIdnum.equals(pinnedAreaIdnum)) {
                pinned = true; break;
            }
        }
        return pinned;
    }

    public void removePinnedAreaData(AreaCovidData areaData) {
        for (int i = 0; i < pinnedAreaCovidData.size(); i++) {
            String areaIdnum = areaData.getArea().getIdnum();
            String pinnedAreaIdnum = pinnedAreaCovidData.get(i).getArea().getIdnum();
            if (areaIdnum.equals(pinnedAreaIdnum)) {
                pinnedAreaCovidData.remove(i);
                break;
            }
        }
        return;
    }

    public void addPinnedAreaData(AreaCovidData areaData) {
        /*boolean pinned;
        for (int i = 0; i < pinnedAreaCovidData.size(); i++) {
            String areaIdnum = areaData.getArea().getIdnum();
            String pinnedAreaIdnum = pinnedAreaCovidData.get(i).getArea().getIdnum();
            if (areaIdnum.equals(pinnedAreaIdnum)) {
                pinnedAreaCovidData.remove(i);
                break;
            }
        }*/
        pinnedAreaCovidData.add(areaData);
        return;
    }
}
