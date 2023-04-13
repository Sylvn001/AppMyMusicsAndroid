package br.unoeste.appmymusics.api;

import java.util.ArrayList;

public class Mu {
    public String id;
    public String name;
    public String url;
    public int lang;
    public String text;
    public ArrayList<Translate> translate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Translate> getTranslate() {
        return translate;
    }

    public void setTranslate(ArrayList<Translate> translate) {
        this.translate = translate;
    }
}
