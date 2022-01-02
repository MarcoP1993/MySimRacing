package com.example.mysimracing.RecicledListas;

import java.io.Serializable;
import java.util.Objects;

public class EventosTorneo implements Serializable {
    private String nombreTorneo;
    private String ciudad;
    private String plataforma;
    private Boolean compitiendo;

    public EventosTorneo(String nombreTorneo, String ciudad, String plataforma, boolean compitiendo) {
        this.nombreTorneo = nombreTorneo;
        this.ciudad = ciudad;
        this.plataforma = plataforma;
        this.compitiendo = compitiendo;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public Boolean getCompitiendo() {
        return compitiendo;
    }

    public void setCompitiendo(Boolean compitiendo) {
        this.compitiendo = compitiendo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventosTorneo that = (EventosTorneo) o;
        return nombreTorneo.equals(that.nombreTorneo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreTorneo);
    }

    @Override
    public String toString() {
        return "EventosTorneo{" +
                "nombreTorneo='" + nombreTorneo + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", plataforma='" + plataforma + '\'' +
                ", compitiendo=" + compitiendo +
                '}';
    }
}
