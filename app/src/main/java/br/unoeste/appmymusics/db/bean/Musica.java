package br.unoeste.appmymusics.db.bean;


import br.unoeste.appmymusics.db.bean.Genero;

public class Musica {

    private int id, ano;
    private String titulo, interprete;
    private Genero genero;
    private double duracao;

    public Musica() {
        this(0,0,"","",null,0);
    }

    public Musica(int ano, String titulo, String interprete, Genero genero, double duracao) {
        this(0,ano,titulo,interprete,genero,duracao);
    }

    public Musica(int id, int ano, String titulo, String interprete, Genero genero, double duracao) {
        this.id = id;
        this.ano = ano;
        this.titulo = titulo;
        this.interprete = interprete;
        this.genero = genero;
        this.duracao = duracao;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInterprete() {
        return interprete;
    }

    public void setInterprete(String interprete) {
        this.interprete = interprete;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public double getDuracao() {
        return duracao;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }

    @Override
    public String toString() {
        return  "" + String.format("%s - %s - %s MIN - %s - %s (%s)", this.getTitulo(), this.getInterprete(), this.getDuracao(),this.getAno(), this.getGenero().getNome(), getId());
    }


}
