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

    private static String baseUrlFi = "https://sampo.thl.fi/pivot/prod/fi/epirapo/covid19case/fact_epirapo_covid19case.json";
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
    private static Settings S;

    public static ArrayList<Area> getAreaList(){
        /* gets a list of searchable areas from the THL API */
        ArrayList<Area> cityList = new ArrayList<>();
        ArrayList<Area> hcdList = new ArrayList<>();

        S = Settings.getInstance();
        int language = S.getLanguage();
        String baseUrl;
        if (language == 0) {
            baseUrl = baseUrlEn;
        } else if (language == 1) {
            baseUrl = baseUrlFi;
        } else {
            baseUrl = baseUrlEn;
        }

        String urlString = baseUrl + qmk + rpm + hcdMunicipality + allAreas + aps + fpm +
                measure + covidFilter;
        System.out.println(urlString);

        String json = getJSON(urlString);
        hcdList = jsonToAreaList(json);
        if (hcdList != null) {
            for (int i = 0; i < hcdList.size(); i++) {
                String currentId = hcdList.get(i).getIdnum();
                urlString = baseUrl + qmk + rpm + hcdMunicipality + currentId + aps + fpm +
                        measure + covidFilter;
                json = getJSON(urlString);
                cityList.addAll(jsonToAreaList(json));
            }
        }
        return (cityList);
    }

    public static AreaData getAreaCovidData(Area area) {
        /* gets covid data for the specified area from the THL API */
        AreaData areaData = null;
        if (area != null) {
            String areaIdnum = area.getIdnum();
            String dataIdnum;
            String dataIndex;
            String dataLabel;
            String dataValue;
            ArrayList<Data> covidDataList = null;

            S = Settings.getInstance();
            int language = S.getLanguage();
            String baseUrl;
            if (language == 0) {
                baseUrl = baseUrlEn;
            } else if (language == 1) {
                baseUrl = baseUrlFi;
            } else {
                baseUrl = baseUrlEn;
            }

            String urlString = baseUrl + qmk + rpm + hcdMunicipality + areaIdnum + aps +
                    cpm + dateWeek + allTimes + aps + fpm + measure + covidFilter;
            System.out.println(urlString);
            String json = getJSON(urlString);
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
                    dataIdnum = null;
                    dataIndex = null;
                    dataLabel = null;
                    dataValue = null;
                    dataIdnum = (String) indexKeys.next();
                    if (jsonIndex.has(dataIdnum)) {
                        dataIndex = jsonIndex.getString(dataIdnum);
                    }

                    if (jsonLabel.has(dataIdnum)) {
                        dataLabel = jsonLabel.getString(dataIdnum);
                    }

                    if (jsonValue.has(dataIndex)) {
                        dataValue = jsonValue.getString(dataIndex);
                    }

                    if (dataValue != null && dataIdnum.equals(allTimes) == false) {
                        covidDataList.add(new Data(dataIdnum, dataIndex, dataLabel, dataValue));
                    }
                }
                Collections.reverse(covidDataList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            areaData = new AreaData(area, covidDataList);
        }
        return (areaData);
    }

    private static ArrayList<Area> jsonToAreaList(String json) {
        /* Parses json file containing searchable areas into a list of areas */
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
                    //System.out.println(i + " " + currentId + " " + currentLabel);
                    i++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return(areaList);
    }

    private static String getJSON(String urlString) {
        /* Gets json file from the THL API */
        System.out.println(urlString);
        String response = null;
        try {
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