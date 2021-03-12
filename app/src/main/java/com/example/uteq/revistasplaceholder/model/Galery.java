package com.example.uteq.revistasplaceholder.model;

public class Galery {

    private String galley_id;
    private String label;
    private String file_id;
    private String UrlViewGalley;

    public Galery() {
    }

    public Galery(String galley_id, String label, String file_id, String urlViewGalley) {
        this.galley_id = galley_id;
        this.label = label;
        this.file_id = file_id;
        UrlViewGalley = urlViewGalley;
    }

    public String getGalley_id() {
        return galley_id;
    }

    public void setGalley_id(String galley_id) {
        this.galley_id = galley_id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getUrlViewGalley() {
        return UrlViewGalley;
    }

    public void setUrlViewGalley(String urlViewGalley) {
        UrlViewGalley = urlViewGalley;
    }

    @Override
    public String toString() {
        return "Galery{" +
                "galley_id='" + galley_id + '\'' +
                ", label='" + label + '\'' +
                ", file_id='" + file_id + '\'' +
                ", UrlViewGalley='" + UrlViewGalley + '\'' +
                '}';
    }
}
