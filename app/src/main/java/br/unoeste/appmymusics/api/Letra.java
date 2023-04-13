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
}
