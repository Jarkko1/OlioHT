package com.example.olioht;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Settings {

    private static Settings S = null;
    private int language = 0; // 0 = en, 1 = fi

    public static Settings getInstance() {
        if (S == null) {
            S = new Settings();
        }
        return(S);
    }

    private Settings() {
        language = 0;
    }

    public void setLanguage(int languageNumber) {
        this.language = languageNumber;
    }

    public void setLanguageWithLangCode(String langCode) {
        if (langCode.equals("fi")) {
            this.language = 1;
        } else if (langCode.equals("en")) {
            this.language = 0;
        }
    }

    public int getLanguage() {return language;}
}
