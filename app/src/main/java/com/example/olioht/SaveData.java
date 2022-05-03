package com.example.olioht;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SaveData {
    // Convert JsonObject to String Format
    private static SaveData SD = null;

    private DataCenter D;
    private Settings S;
    private int language;

    public static SaveData getInstance() {
        if (SD == null) {
            SD = new SaveData();
        }
        return(SD);
    }

    private SaveData() {
        S = Settings.getInstance();
        D = DataCenter.getInstance();
        newFile();
    }
    private void getFile() {
        try {









        }
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

    private String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;


        }
    }

    

}
