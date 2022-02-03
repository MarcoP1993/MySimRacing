package com.example.mysimracing.Clases;

import java.io.Serializable;
import java.util.Objects;

public class Equipos implements Serializable {

    private String nombreEquipo;
    private String pais;
    private String jefe_equipo;
    private String web;
    private String discord;

    public Equipos() {
    }

    public Equipos(String nombreEquipo, String pais, String jefe_equipo, String web, String discord) {
        this.nombreEquipo = nombreEquipo;
        this.pais = pais;
        this.jefe_equipo = jefe_equipo;
        this.web = web;
        this.discord = discord;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getJefe_equipo() {
        return jefe_equipo;
    }

    public void setJefe_equipo(String jefe_equipo) {
        this.jefe_equipo = jefe_equipo;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getDiscord() {
        return discord;
    }

    public void setDiscord(String discord) {
        this.discord = discord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipos equipos = (Equipos) o;
        return nombreEquipo.equals(equipos.nombreEquipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreEquipo);
    }

    @Override
    public String toString() {
        return "Equipos{" +
                "nombre Equipo='" + nombreEquipo + '\'' +
                ", pais='" + pais + '\'' +
                ", jefe de equipo='" + jefe_equipo + '\'' +
                ", web='" + web + '\'' +
                ", discord='" + discord + '\'' +
                '}';
    }
}
