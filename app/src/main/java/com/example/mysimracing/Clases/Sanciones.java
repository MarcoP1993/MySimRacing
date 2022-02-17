package com.example.mysimracing.Clases;

public class Sanciones {

    private String equipo_piloto;
    private String circuito;
    private String tiempo;
    private String descripcion;
    private String salas;

    public Sanciones() {
    }

    public Sanciones(String equipo_piloto, String circuito, String tiempo, String descripcion, String salas) {
        this.equipo_piloto = equipo_piloto;
        this.circuito = circuito;
        this.tiempo = tiempo;
        this.descripcion = descripcion;
        this.salas = salas;
    }

    public String getEquipo_piloto() {
        return equipo_piloto;
    }

    public void setEquipo_piloto(String equipo_piloto) {
        this.equipo_piloto = equipo_piloto;
    }

    public String getCircuito() {
        return circuito;
    }

    public void setCircuito(String circuito) {
        this.circuito = circuito;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSalas() {
        return salas;
    }

    public void setSalas(String salas) {
        this.salas = salas;
    }

    @Override
    public String toString() {
        return "Sanciones{" +
                "equipo_piloto='" + equipo_piloto + '\'' +
                ", circuito='" + circuito + '\'' +
                ", tiempo='" + tiempo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", salas='" + salas + '\'' +
                '}';
    }
}
