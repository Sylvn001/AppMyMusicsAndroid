package br.unoeste.appmymusics.api;

import java.util.ArrayList;

public class Letra {
    public String type;
    public Art art;
    public ArrayList<Mu> mus;
    public boolean badwords;

    @Override
    public String toString() {
        return "Letra{" +
                "type='" + type + '\'' +
                ", art=" + art +
                ", mus=" + mus +
                ", badwords=" + badwords +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Art getArt() {
        return art;
    }

    public void setArt(Art art) {
        this.art = art;
    }

    public ArrayList<Mu> getMus() {
        return mus;
    }

    public void setMus(ArrayList<Mu> mus) {
        this.mus = mus;
    }

    public boolean isBadwords() {
        return badwords;
    }

    public void setBadwords(boolean badwords) {
        this.badwords = badwords;
    }
}
