package com.example.olioht;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Settings {

    private static Settings S = null;
    private int language = 1;

    public static Settings getInstance() {
        if (S == null) {
            S = new Settings();
        }
        return(S);
    }

    private Settings() {
        language = 1;

    }

    public void setLanguage(int languageNumber) {
        language = languageNumber;
    }

}
