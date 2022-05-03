package com.example.olioht;

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
import java.util.Collections;
import java.util.Iterator;

public class DataAPI {
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
    private static String baseUrlEn = "https://sampo.thl.fi/pivot/prod/en/epirapo/covid19case/fact_epirapo_covid19case.json";
    private static String qmk = "?";
    private static String aps = "&";
    private static String hpn = "-";
    private static String rpm = "row=";
    private static String cpm = "column=";
    private static String fpm = "filter=";
    private static String hcdMunicipality = "hcdmunicipality2020-";
    private static String dateWeek = "dateweek20200101-";
    private static String measure = "measure-";
    private static String allAreas = "445222";
    private static String allTimes = "509030";
    private static String covidFilter = "444833";


    public static ArrayList<Area> getAreaList(){
        ArrayList<Area> cityList = new ArrayList<>();
        ArrayList<Area> hcdList = new ArrayList<>();
        String urlString = baseUrl + qmk + rpm + hcdMunicipality + allAreas + aps + fpm +
                measure + covidFilter;
        System.out.println(urlString);
        String json = getJSON(urlString);
        Area A;
        hcdList = jsonToAreaList(json);
        if (hcdList != null) {
            for (int i = 0; i < hcdList.size(); i++) {
                String currentId = hcdList.get(i).getIdnum();
                urlString = baseUrl + qmk + rpm + hcdMunicipality + currentId + aps + fpm +
                        measure + covidFilter;
                json = getJSON(urlString);
                cityList.addAll(jsonToAreaList(json));
            }
            System.out.println("###Sairaanhoitopiirit:###");
            for (int i = 0; i < hcdList.size(); i++) {
                A = hcdList.get(i);
                System.out.println(A.getIdnum() + " " + A.getLabel());
            }
            System.out.println("###Kunnat:###");
            for (int i = 0; i < cityList.size(); i++) {
                A = cityList.get(i);
                System.out.println(A.getIdnum() + " " + A.getLabel());
            }
        }
        return (cityList);
    }

    public static AreaData getAreaCovidData(Area area) {
        String areaIdnum = area.getIdnum();
        String dataIdnum; String dataIndex; String dataLabel; String dataValue;
        ArrayList<Data> covidDataList = null; //new ArrayList<>();
        String urlString = baseUrl + qmk + rpm + hcdMunicipality + areaIdnum + aps +
                cpm + dateWeek + allTimes + aps + fpm + measure + covidFilter;
        System.out.println(urlString);
        String json = getJSON(urlString);
        //System.out.println(json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonDataset = jsonObject.getJSONObject("dataset");
            JSONObject jsonDimension = jsonDataset.getJSONObject("dimension");
            JSONObject jsonHcdmunicipality2020 = jsonDimension.getJSONObject("dateweek20200101");
            JSONObject jsonCategory = jsonHcdmunicipality2020.getJSONObject("category");
            JSONObject jsonIndex = jsonCategory.getJSONObject("index");
            JSONObject jsonLabel = jsonCategory.getJSONObject("label");
            JSONObject jsonValue = jsonDataset.getJSONObject("value");
            Iterator indexKeys = jsonIndex.keys();
            Iterator labelKeys = jsonLabel.keys();
            covidDataList = new ArrayList<>();
            while (indexKeys.hasNext()) {
                dataIdnum = null; dataIndex = null; dataLabel = null; dataValue = null;
                dataIdnum = (String) indexKeys.next();
                if (jsonIndex.has(dataIdnum)) { dataIndex = jsonIndex.getString(dataIdnum); };
                if (jsonLabel.has(dataIdnum)) { dataLabel = jsonLabel.getString(dataIdnum); };
                if (jsonValue.has(dataIndex)) { dataValue = jsonValue.getString(dataIndex); };
                if (dataValue != null) {
                    covidDataList.add(new Data(dataIdnum, dataIndex, dataLabel, dataValue));
                }
                //System.out.println(dataIdnum + " " + dataIndex + " " + dataLabel + " " + dataValue);
            }
            Collections.reverse(covidDataList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AreaData areaData = new AreaData(area, covidDataList);
        return(areaData);
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
    private static String getJSON(String urlString) {
        System.out.println(urlString);
        String response = null;
        try {
            //URL url = new URL("http://www.hel.fi/palvelukarttaws/rest/v4/department/");
            //String urlString = "https://sampo.thl.fi/pivot/prod/fi/epirapo/covid19case/fact_epirapo_covid19case.json?column=dateweek20200101-509030&filter=measure-444833";
            //String urlString = "https://sampo.thl.fi/pivot/prod/fi/epirapo/covid19case/fact_epirapo_covid19case.json";
            //String urlString = "http://www.hel.fi/palvelukarttaws/rest/v4/department/";
            URL url = new URL(urlString);
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
            System.out.println(urlString);

        }
        return(response);
    }
}
