package com.example.mysimracing.Clases;

public class Inscripciones {
    String nombre, nick, correo;

    public Inscripciones() {
    }

    public Inscripciones(String nombre, String nick, String correo) {
        this.nombre = nombre;
        this.nick = nick;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
