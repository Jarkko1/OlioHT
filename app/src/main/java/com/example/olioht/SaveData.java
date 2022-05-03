package com.example.olioht;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SaveData {
    // Convert JsonObject to String Format
    private static SaveData SD = null;

    private DataCenter D;
    private Settings S;
    private int language;
    private Context context;
    private String fileName = "storage.json";

    public static SaveData getInstance(Context context) {
        if (SD == null) {
            SD = new SaveData(context);
        }
        return(SD);
    }

    private SaveData(Context context) {
        this.context = context;
        S = Settings.getInstance();
        D = DataCenter.getInstance();
        writeFile("JOOPAJOO");
        readFile();
    }

    private String readFile() {
        System.out.println("readFile START");
        String json = "";
        String line = "";
        try {
            InputStream inputStream = context.openFileInput(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                System.out.println("rf: " + line);
            }
            inputStream.close();
            /*DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document parseDoc documentBuilder.parse(inputStream);*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("OK2");
        }
        System.out.println("readFile END");
        return json;
    }

    private void writeFile(String json) {
        System.out.println("writeFile START");
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("FNF");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("writeFile END");
    }

    private void newFile() {
        try {
            /* Top level JSONObject */
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            /* Settings JSONObject */
            JSONObject jsonSettings = new JSONObject();
            jsonSettings.put("Language", language);

            /* Pinned JSONObject */
            JSONObject jsonPinned = new JSONObject();
            ArrayList<String> pinnedAreaIdnums = D.getPinnedAreaIdnums();
            JSONArray jsonPinnedArray = new JSONArray(pinnedAreaIdnums);
            jsonPinned.put("Pinned", jsonPinnedArray);

            jsonArray.put(jsonSettings);
            jsonArray.put(jsonPinned);
            jsonObject.put("Data", jsonArray);

            String userString = jsonObject.toString();
            System.out.println(userString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*
        // Define the File Path and its Name
        File file = new File(context.getFilesDir(),FILE_NAME);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(userString);
        bufferedWriter.close();*/
    }
/*
    private String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;


        }
    }
*/
    

}
