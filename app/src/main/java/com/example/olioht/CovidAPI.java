package com.example.olioht;

import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class CovidAPI {
    /*
    private static CovidAPI C = null;

    public static CovidAPI getInstance() {
        if (C == null) {
            C = new CovidAPI();
        }
        return(C);
    }
    */

    /* baseUrl -muuttuja sisältää kaikille API-osoitteille yhteisen osan URL-osoitetta. */
    private static String baseUrl = "https://sampo.thl.fi/pivot/prod/fi/epirapo/covid19case/fact_epirapo_covid19case.json";

    public static ArrayList<Area> getAreaList(){
        ArrayList<Area> cityList = new ArrayList<>();
        ArrayList<Area> hcdList = new ArrayList<>();
        String urlString = "https://sampo.thl.fi/pivot/prod/fi/epirapo/covid19case/fact_epirapo_covid19case.json?row=hcdmunicipality2020-445222&filter=measure-444833";
        String json = getJSON(urlString);
        Area A;
        hcdList = jsonToAreaList(json);
        for (int i = 0; i < hcdList.size(); i++) {
            String currentId = hcdList.get(i).getId();
            urlString = "https://sampo.thl.fi/pivot/prod/fi/epirapo/covid19case/fact_epirapo_covid19case.json?row=hcdmunicipality2020-" + currentId + "&filter=measure-444833";
            json = getJSON(urlString);
            cityList.addAll(jsonToAreaList(json));
        }
        System.out.println("###Sairaanhoitopiirit:###");
        for (int i = 0; i < hcdList.size(); i++) {
            A = hcdList.get(i);
            System.out.println(A.getId() + " " + A.getLabel());
        }
        System.out.println("###Kunnat:###");
        for (int i = 0; i < cityList.size(); i++) {
            A = cityList.get(i);
            System.out.println(A.getId() + " " + A.getLabel());
        }
        return(cityList);
    }

    public static ArrayList<CovidData> getAreaCovidData(String areaId) {
        ArrayList<CovidData> CovidList = new ArrayList<>();
        String urlParam = "?row=hcdmunicipality2020-" + areaId + "&filter=measure-444833";
        String urlString = baseUrl + urlParam;
        String json = getJSON(urlString);

        /* do something with the json here */

        return(CovidList);
    }

    private static ArrayList<Area> jsonToAreaList(String json) {
        ArrayList<Area> areaList = null;
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject jsonDataset = jsonObject.getJSONObject("dataset");
                JSONObject jsonDimension = jsonDataset.getJSONObject("dimension");
                JSONObject jsonHcdmunicipality2020 = jsonDimension.getJSONObject("hcdmunicipality2020");
                JSONObject jsonCategory = jsonHcdmunicipality2020.getJSONObject("category");
                JSONObject jsonIndex = jsonCategory.getJSONObject("index");
                JSONObject jsonLabel = jsonCategory.getJSONObject("label");
                Iterator keys = jsonLabel.keys();
                int i = 0;
                System.out.println("##!!##");
                areaList = new ArrayList<>();
                while (keys.hasNext()) {
                    String currentId = (String) keys.next();
                    String currentLabel = jsonLabel.getString(currentId);
                    areaList.add(new Area(currentId, currentLabel, String.valueOf(i + 1)));
                    System.out.println(i + " " + currentId + " " + currentLabel);
                    i++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return(areaList);
    }

    /* Tämä siis hakee itse datan. urlString muuttujaa muokkaamalla voi rajata dataa */
    private static String getJSON(String URLString) {
        String response = null;
        try {
            //URL url = new URL("http://www.hel.fi/palvelukarttaws/rest/v4/department/");
            //String urlString = "https://sampo.thl.fi/pivot/prod/fi/epirapo/covid19case/fact_epirapo_covid19case.json?column=dateweek20200101-509030&filter=measure-444833";
            //String urlString = "https://sampo.thl.fi/pivot/prod/fi/epirapo/covid19case/fact_epirapo_covid19case.json";
            //String urlString = "http://www.hel.fi/palvelukarttaws/rest/v4/department/";
            URL url = new URL(URLString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            response = sb.toString();
            in.close();
        } catch (ProtocolException e) {
            e.printStackTrace();
            System.out.println("ERROR 1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("ERROR 2");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR 3");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR 4");
        }
        return(response);
    }
}
