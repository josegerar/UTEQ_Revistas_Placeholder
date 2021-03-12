package com.example.uteq.revistasplaceholder.model;

public class Autor {

    private String nombres;
    private String filiacion;
    private String email;

    public Autor() {
    }

    public Autor(String nombres, String filiacion, String email) {
        this.nombres = nombres;
        this.filiacion = filiacion;
        this.email = email;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getFiliacion() {
        return filiacion;
    }

    public void setFiliacion(String filiacion) {
        this.filiacion = filiacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nombres='" + nombres + '\'' +
                ", filiacion='" + filiacion + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
