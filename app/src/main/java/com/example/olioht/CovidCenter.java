package com.example.olioht;

import java.util.ArrayList;

public class CovidCenter {

    private static CovidCenter C = null;

    private ArrayList<Area> areaList = null;  /* lista alueista ja niiden tunnuksista.
    Käyttö: areaList.get(i).getId() tai areaList.get(i).getLabel(); */

    private ArrayList<AreaCovidData> pinnedAreaList = null;

    private ArrayList<CovidData> covidDataList = null;

    public static CovidCenter getInstance() {
        if (C == null) {
            C = new CovidCenter();
        }
        return(C);
    }

    private CovidCenter() {
        areaList = CovidAPI.getAreaList();
    }

    public void searchData() {
    /* tällä olisi tarkoitus hakea covid-tapausten määrät alueella */
    }

    private String convertLabelToId(String label) {
        /* tällä voi muuntaa alueen nimen (vaikka "Helsinki")
        sitä vastaavaksi id numeroksi */
        String id = null;
        for (int i = 0; i < areaList.size(); i++) {
            if (areaList.get(i).getLabel() == label) {
                id = areaList.get(i).getId();
            }
        }
        return(id);
    }

    public ArrayList<Area> getAreaList() {
        return(areaList);
    }

    public void test() {
        areaList = null;
        covidDataList = CovidAPI.getAreaCovidData("445234");
    }
}
