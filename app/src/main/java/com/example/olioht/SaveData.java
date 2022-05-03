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
import java.io.FileOutputStream;
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
    private String pinnedFileName = "pinned.json";
    private String settingsFileName = "settings.json";

    public static SaveData newInstance(Context context) {
        if (SD == null) {
            SD = new SaveData(context);
        }
        return(SD);
    }
    public static SaveData getInstance() {
        return(SD);
    }

    private SaveData(Context context) {
        this.context = context;
    }

    public void writePinned() {
        D = DataCenter.getInstance();
        String json = "";
        ArrayList<String> pinnedAreaIdnums = D.getPinnedAreaIdnums();
        try {
            JSONObject jsonPinned = new JSONObject();
            JSONArray jsonPinnedArray = new JSONArray(pinnedAreaIdnums);
            jsonPinned.put("Pinned", jsonPinnedArray);
            json = jsonPinned.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        writeFile(json);
    }

    public ArrayList<String> readPinned() {
        String json;
        ArrayList<String> pinnedAreaIdnums = new ArrayList<>();
        json = readFile();
        try {
            JSONObject jsonPinned = new JSONObject(json);
            JSONArray jsonPinnedArray = jsonPinned.getJSONArray("Pinned");
            if (jsonPinnedArray != null) {
                for (int i = 0; i < jsonPinnedArray.length(); i++) {
                    pinnedAreaIdnums.add(jsonPinnedArray.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pinnedAreaIdnums;
    }

    public void writeSettings() {

    }

    public int readSettings() {
        return 0;
    }

    private String readFile() {
        String json = "";
        String line = "";
        try {
            InputStream inputStream = context.openFileInput(pinnedFileName);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ( (line = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(line);
                }

                inputStream.close();
                json = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private void writeFile(String json) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(pinnedFileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(json);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
