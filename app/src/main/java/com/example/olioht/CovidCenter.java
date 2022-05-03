package com.example.olioht;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CovidCenter {

    private static CovidCenter C = null;

    private ArrayList<Area> areaList = null;  /* lista alueista ja niiden tunnuksista.
    Käyttö: areaList.get(i).getId() tai areaList.get(i).getLabel(); */

    private ArrayList<AreaCovidData> pinnedAreaCovidData = null;

    private ArrayList<CovidData> covidDataList = null;

    private ArrayList<String> areaLabels = null;

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

    public void searchData() {
    /* tällä olisi tarkoitus hakea covid-tapausten määrät alueella */
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

    public void test() {
        areaList = null;
        AreaCovidData areaCovidData = null;
        areaCovidData = CovidAPI.getAreaCovidData(areaList.get(0));
    }
}
