package com.claresti.mistareas.gestordetareas;

public class ObjUsuario {

    private String nombre;
    private int primera;
    private String img;

    public ObjUsuario(String nombre, int primera, String img) {
        this.nombre = nombre;
        this.primera = primera;
        this.img = img;
    }

    public ObjUsuario(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrimera() {
        return primera;
    }

    public void setPrimera(int primera) {
        this.primera = primera;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
