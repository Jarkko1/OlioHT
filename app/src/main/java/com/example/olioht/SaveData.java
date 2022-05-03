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
/*
    private String readFile(String fileName) {
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
        /*
} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("OK2");
        }
        System.out.println("readFile END");
        return json;
    }*/

    /*
    private void writeFile(String json) {
        System.out.println("writeFile START");
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(json);
            System.out.println(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("FNF");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("writeFile END");
    }

    private boolean create(Context context, String fileName, String jsonString){
        String FILENAME = "storage.json";
        try {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }

    }

    public boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }
    */

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
