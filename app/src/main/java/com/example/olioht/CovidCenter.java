package com.example.olioht;

import java.util.ArrayList;

public class CovidCenter {

    private static CovidCenter C = null;

    private ArrayList<Area> areaList = null;  /* lista alueista ja niiden tunnuksista.
    Käyttö: areaList.get(i).getId() tai areaList.get(i).getLabel(); */

    private ArrayList<AreaCovidData> pinnedAreaCovidData = null;

    private ArrayList<CovidData> covidDataList = null;

    public static CovidCenter getInstance() {
        if (C == null) {
            C = new CovidCenter();
        }
        return(C);
    }

    private CovidCenter() {
        areaList = CovidAPI.getAreaList();
        AreaCovidData areaCovidData = null;
        pinnedAreaCovidData = new ArrayList<>();
        System.out.println("Here we are.");
        areaCovidData = CovidAPI.getAreaCovidData(areaList.get(0));
        System.out.println("Here we are now.");
        if (areaCovidData == null) {
            System.out.println("Weird.");
        } else {
            System.out.println("Ok, good.");
            Area A = areaCovidData.getArea();
            if (A == null) {
                System.out.println("Weirder.");
            } else {
                System.out.println("Is it really working?.");
            }
        }
        for (int i = 0; i < 3; i++) {
            pinnedAreaCovidData.add(areaCovidData);
        }
        for (int i = 0; i < 3; i++) {
            Area A = pinnedAreaCovidData.get(i).getArea();
            System.out.println(A.getIdnum());
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

    public ArrayList<String> getPinnedAreaCovidData() {
        ArrayList<String> pinnedStringList = new ArrayList<>();
        int a;
        for (int i = 0; i < pinnedAreaCovidData.size(); i++) {
            Area A = pinnedAreaCovidData.get(i).getArea();
            if (A != null) {
                pinnedStringList.add(A.getLabel());
            }
        }
        return(pinnedStringList);
    }

    public void test() {
        areaList = null;
        AreaCovidData areaCovidData = null;
        areaCovidData = CovidAPI.getAreaCovidData(areaList.get(0));
    }
}
