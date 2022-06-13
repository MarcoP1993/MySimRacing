package com.example.mysimracing.Clases;

import java.io.Serializable;

public class Sanciones implements Serializable {

    private String equipo;
    private String piloto;
    private String circuito;
    private String tiempo;
    private String descripcion;
    private String salas;

    public Sanciones() {
    }

    public Sanciones(String equipo, String piloto, String circuito, String tiempo, String descripcion, String salas) {
        this.equipo = equipo;
        this.piloto = piloto;
        this.circuito = circuito;
        this.tiempo = tiempo;
        this.descripcion = descripcion;
        this.salas = salas;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getPiloto() {
        return piloto;
    }

    public void setPiloto(String piloto) {
        this.piloto = piloto;
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
                "equipo='" + equipo + '\'' +
                ", piloto='" + piloto + '\'' +
                ", circuito='" + circuito + '\'' +
                ", tiempo='" + tiempo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", salas='" + salas + '\'' +
                '}';
    }
}
