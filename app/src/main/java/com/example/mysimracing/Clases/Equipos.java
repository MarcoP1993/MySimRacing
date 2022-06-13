package com.example.mysimracing.Clases;

import java.io.Serializable;
import java.util.Objects;

public class Equipos implements Serializable {

    private String id;
    private String nombreEquipo;
    private String pais;
    private String jefe_equipo;
    private String web;
    private String discord;
    private String logo_equipo;

    public Equipos() {
    }

    public Equipos(String id, String nombreEquipo, String pais, String jefe_equipo, String web, String discord, String logo_equipo) {
        this.id = id;
        this.nombreEquipo = nombreEquipo;
        this.pais = pais;
        this.jefe_equipo = jefe_equipo;
        this.web = web;
        this.discord = discord;
        this.logo_equipo = logo_equipo;
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

    public String getLogo_equipo() {
        return logo_equipo;
    }

    public void setLogo_equipo(String logo_equipo) {
        this.logo_equipo = logo_equipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipos equipos = (Equipos) o;
        return nombreEquipo.equals(equipos.nombreEquipo);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreEquipo);
    }

    @Override
    public String toString() {
        return "Equipos{" +
                "nombreEquipo='" + nombreEquipo + '\'' +
                ", pais='" + pais + '\'' +
                ", jefe_equipo='" + jefe_equipo + '\'' +
                ", web='" + web + '\'' +
                ", discord='" + discord + '\'' +
                ", logo_equipo='" + logo_equipo + '\'' +
                '}';
    }
}
