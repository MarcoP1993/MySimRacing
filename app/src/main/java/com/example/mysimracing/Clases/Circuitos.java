package com.example.mysimracing.Clases;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Circuitos implements Serializable {

    private String id;
    private String nombre;
    private String pais;
    private String categoria;
    private String horaCarrera;
    private String diaCarrera;
    private String imagenCircuito;

    public Circuitos() {
    }

    public Circuitos(String id, String nombre, String pais, String categoria, String horaCarrera, String diaCarrera, String imagenCircuito) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        this.categoria = categoria;
        this.horaCarrera = horaCarrera;
        this.diaCarrera = diaCarrera;
        this.imagenCircuito = imagenCircuito;
    }

    public Circuitos(String nombre, String pais, String categoria, String horaCarrera, String diaCarrera) {
        this.nombre = nombre;
        this.pais = pais;
        this.categoria = categoria;
        this.horaCarrera = horaCarrera;
        this.diaCarrera = diaCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getHoraCarrera() {
        return horaCarrera;
    }

    public void setHoraCarrera(String horaCarrera) {
        this.horaCarrera = horaCarrera;
    }

    public String getDiaCarrera() {
        return diaCarrera;
    }

    public void setDiaCarrera(String diaCarrera) {
        this.diaCarrera = diaCarrera;
    }

    public String getImagenCircuito() {
        return imagenCircuito;
    }

    public void setImagenCircuito(String imagenCircuito) {
        this.imagenCircuito = imagenCircuito;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Circuitos{" +
                "nombre='" + nombre + '\'' +
                ", pais='" + pais + '\'' +
                ", categoria='" + categoria + '\'' +
                ", hora Carrera='" + horaCarrera + '\'' +
                ", dia Carrera='" + diaCarrera + '\'' +
                ", imagen Circuito=" + imagenCircuito +
                '}';
    }
}
