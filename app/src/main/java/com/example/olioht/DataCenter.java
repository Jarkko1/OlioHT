package com.example.olioht;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class DataCenter {

    private static DataCenter C = null;

    private ArrayList<Area> areaList = null; // List of all areas
    private ArrayList<String> areaLabels = null; // Area labels as strings for the search page
    private ArrayList<AreaData> pinnedAreaData = new ArrayList<>(); // Areas pinned to the home page
    private AreaData currentAreaData = null; // area from search / home page
    private SaveData SD;

    public static DataCenter getInstance() {
        if (C == null) {
            C = new DataCenter();
        }
        return(C);
    }

    private DataCenter() {
        SD = SaveData.getInstance();
        areaList = DataAPI.getAreaList();
        areaLabels = areaLabelsList();
        if (SD == null) {System.out.println("Voi ei");}
        ArrayList<String> pinnedAreaIdnums = SD.readPinned();
        System.out.println("TÄRKEÄ" + pinnedAreaIdnums.size());
        for (int i = 0; i < pinnedAreaIdnums.size(); i++) {
            System.out.println(pinnedAreaIdnums.get(i));
            Area area = convertIdnumToArea(pinnedAreaIdnums.get(i));
            if (area != null) {
                AreaData areaData = DataAPI.getAreaCovidData(area);
                if (areaData != null) {
                    pinnedAreaData.add(areaData);
                }
            } else {
                System.out.println("MItä");
            }
        }
    }

    /* gets area covid data with area label from search */
    public void searchData(String areaLabel) {
        Area area = convertLabelToArea(areaLabel);
        currentAreaData = DataAPI.getAreaCovidData(area);
    }

    /* Converts area label to the corresponding area id number */
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

    /* Converts area label to the corresponding area */
    private Area convertLabelToArea(String areaLabel) {
        /* tällä voi muuntaa alueen nimen (vaikka "Helsinki")
        sitä vastaavaksi id numeroksi */
        Area area = null;
        for (int i = 0; i < areaList.size(); i++) {
            if (areaList.get(i).getLabel() == areaLabel) {
                area = areaList.get(i);
                break;
            }
        }
        return(area);
    }

    /* Converts area id number to the corresponding area */
    private Area convertIdnumToArea(String areaIdnum) {
        /* tällä voi muuntaa alueen nimen (vaikka "Helsinki")
        sitä vastaavaksi id numeroksi */
        Area area = null;
        for (int i = 0; i < areaList.size(); i++) {
            if (areaIdnum.equals(areaList.get(i).getIdnum())) {
                area = areaList.get(i);
                break;
            }
        }
        return(area);
    }

    public ArrayList<Area> getAreaList() {
        return(areaList);
    }

    public ArrayList<AreaData> getPinnedAreaCovidData() {
        return(pinnedAreaData);
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

    public AreaData getCurrentAreaData() {
        return currentAreaData;
    }

    public void setCurrentAreaData(AreaData currentAreaData) {
        this.currentAreaData = currentAreaData;
    }

    /* check if current area is already pinned to home page */
    public boolean isThisPinned(AreaData areaData) {
        boolean pinned = false;
        if (pinnedAreaData != null) {
            for (int i = 0; i < pinnedAreaData.size(); i++) {
                AreaData pinnedAreaData = this.pinnedAreaData.get(i);
                String thisAreaIdnum = areaData.getArea().getIdnum();
                String pinnedAreaIdnum = pinnedAreaData.getArea().getIdnum();
                if (thisAreaIdnum.equals(pinnedAreaIdnum)) {
                    pinned = true;
                    break;
                }
            }
        }
        return pinned;
    }

    public void removePinnedAreaData(AreaData areaData) {
        for (int i = 0; i < pinnedAreaData.size(); i++) {
            String areaIdnum = areaData.getArea().getIdnum();
            String pinnedAreaIdnum = pinnedAreaData.get(i).getArea().getIdnum();
            if (areaIdnum.equals(pinnedAreaIdnum)) {
                pinnedAreaData.remove(i);
                break;
            }
        }
        SD.writePinned();
        return;
    }

    public void addPinnedAreaData(AreaData areaData) {
        pinnedAreaData.add(areaData);
        SD.writePinned();
        return;
    }

    /* refreshes all data */
    public void refreshData() {
        /* refresh areaList */
        areaList = getAreaList();

        /* refresh pinnedAreaData */
        for (int i = 0; i < pinnedAreaData.size(); i++) {
            Area area = pinnedAreaData.get(i).getArea();
            pinnedAreaData.set(i, DataAPI.getAreaCovidData(area));
        }

        /* refresh currentAreaData */
        if (currentAreaData != null) {
            currentAreaData = DataAPI.getAreaCovidData(currentAreaData.getArea());
        }

        /* refresh area labels list */
        areaLabels = areaLabelsList();
        return;
    }

    /* returns the id numbers of all pinned areas */
    public ArrayList<String> getPinnedAreaIdnums() {
        ArrayList<String> pinnedAreaIdnums = new ArrayList<>();
        for (int i = 0; i < pinnedAreaData.size(); i++) {
            pinnedAreaIdnums.add(pinnedAreaData.get(i).getArea().getIdnum());
        }
        return pinnedAreaIdnums;
    }
}
