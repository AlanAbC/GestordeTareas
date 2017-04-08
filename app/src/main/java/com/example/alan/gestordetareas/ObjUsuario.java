package com.example.alan.gestordetareas;

public class ObjUsuario {

    private String nombre;
    private int primera;
    private int img;

    public ObjUsuario(String nombre, int primera, int img) {
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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
