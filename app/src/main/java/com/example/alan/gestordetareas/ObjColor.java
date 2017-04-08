package com.example.alan.gestordetareas;

public class ObjColor {

    private int id;
    private String nombre;
    private String exadecimal;

    public ObjColor(int id, String nombre, String exadecimal) {
        this.id = id;
        this.nombre = nombre;
        this.exadecimal = exadecimal;
    }

    public ObjColor(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExadecimal() {
        return exadecimal;
    }

    public void setExadecimal(String exadecimal) {
        this.exadecimal = exadecimal;
    }
}
