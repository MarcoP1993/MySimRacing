package com.example.mysimracing.Clases;

import android.graphics.Bitmap;
import android.widget.DatePicker;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Campeonatos implements Serializable {

    @Exclude
    private String id;

    private String nombreCampeonato;
    private String rango_fechas;
    private String juego;
    private String plataforma;
    private String tipoCampeonato;
    private String imagen;

    public Campeonatos() {
    }

    public Campeonatos(String nombreCampeonato, String rango_fechas, String juego, String plataforma, String tipoCampeonato) {
        this.nombreCampeonato = nombreCampeonato;
        this.rango_fechas = rango_fechas;
        this.juego = juego;
        this.plataforma = plataforma;
        this.tipoCampeonato = tipoCampeonato;
    }

    public Campeonatos(String nombreCampeonato, String rango_fechas, String juego, String plataforma, String tipoCampeonato, String imagen) {
        this.nombreCampeonato = nombreCampeonato;
        this.rango_fechas = rango_fechas;
        this.juego = juego;
        this.plataforma = plataforma;
        this.tipoCampeonato = tipoCampeonato;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCampeonato() {
        return nombreCampeonato;
    }

    public void setNombreCampeonato(String nombreCampeonato) {
        this.nombreCampeonato = nombreCampeonato;
    }

    public String getRango_fechas() {
        return rango_fechas;
    }

    public void setRango_fechas(String rango_fechas) {
        this.rango_fechas = rango_fechas;
    }

    public String getJuego() {
        return juego;
    }

    public void setJuego(String juego) {
        this.juego = juego;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getTipoCampeonato() {
        return tipoCampeonato;
    }

    public void setTipoCampeonato(String tipoCampeonato) {
        this.tipoCampeonato = tipoCampeonato;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campeonatos that = (Campeonatos) o;
        return nombreCampeonato.equals(that.nombreCampeonato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreCampeonato);
    }

    @Override
    public String toString() {
        return "Campeonatos{" +
                "Nombre Campeonato= " + nombreCampeonato + '\'' +
                ", fecha campeonato= " + rango_fechas +
                ", juego= " + juego + '\'' +
                ", plataforma= " + plataforma + '\'' +
                ", tipoCampeonato= " + tipoCampeonato + '\'' +
                '}';
    }
}
