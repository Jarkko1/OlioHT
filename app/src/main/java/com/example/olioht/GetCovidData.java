package com.example.olioht;

import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GetCovidData {
    /* tässä olis tarkoitus lajitella dataa, vielä kesken*/
    public void readJSON(View V) {
        /* This method sorts COVID-19 data. */
        String json = getJSON();
        System.out.println("JSON" + json);

        /*
        if (json != null) {
            for (int i; )

        }
         */
    }
    /* Tämä siis hakee itse datan. urlString muuttujaa muokkaamalla voi rajata dataa */
    public String getJSON() {
        /* This method interacts with the COVID-19 data API and returns a list of strings. */
        String response = null;
        /* urlString muuttujaa muokkaamalla voi hakea eri dataa */
        String urlString = "https://sampo.thl.fi/pivot/prod/fi/epirapo/covid19case/fact_epirapo_covid19case.json?row=hcdmunicipality2020-445171.&column=dateweek20200101-509030&filter=measure-444833";
        try {
            //URL url = new URL("http://www.hel.fi/palvelukarttaws/rest/v4/department/");
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
        }
        return(response);
    }
}
