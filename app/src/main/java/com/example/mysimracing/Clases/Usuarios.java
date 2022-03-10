package com.example.mysimracing.Clases;



import java.io.Serializable;

public class Usuarios implements Serializable {

     String nombre;
     String nickname;
     String correo;
     String password;
     String role;

    public Usuarios() {
    }

    public Usuarios(String nombre, String nickname, String correo, String password, String role) {
        this.nombre = nombre;
        this.nickname = nickname;
        this.correo = correo;
        this.password = password;
        this.role = role;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "nombre='" + nombre + '\'' +
                ", nickname='" + nickname + '\'' +
                ", correo='" + correo + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
