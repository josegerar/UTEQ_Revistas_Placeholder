package com.example.uteq.revistasplaceholder.model;

import java.util.ArrayList;

public class Articulo {

    private String title;
    private ArrayList<Autor> autors;
    private ArrayList<Galery> galeries;

    public Articulo() {
    }

    public Articulo(String title, ArrayList<Autor> autors, ArrayList<Galery> galeries) {
        this.title = title;
        this.autors = autors;
        this.galeries = galeries;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Autor> getAutors() {
        return autors;
    }

    public void setAutors(ArrayList<Autor> autors) {
        this.autors = autors;
    }

    public ArrayList<Galery> getGaleries() {
        return galeries;
    }

    public void setGaleries(ArrayList<Galery> galeries) {
        this.galeries = galeries;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "title='" + title + '\'' +
                ", autors=" + autors +
                ", galeries=" + galeries +
                '}';
    }
}
