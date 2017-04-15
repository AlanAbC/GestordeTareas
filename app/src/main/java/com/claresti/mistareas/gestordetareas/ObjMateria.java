package com.claresti.mistareas.gestordetareas;

public class ObjMateria {

    private int id;
    private String nombre;
    private String abv;
    private String profesor;
    private ObjColor color;

    public ObjMateria(int id, String nombre, String abv, String profesor, ObjColor color) {
        this.id = id;
        this.nombre = nombre;
        this.abv = abv;
        this.profesor = profesor;
        this.color = color;
    }

    public ObjMateria(){

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

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public ObjColor getColor() {
        return color;
    }

    public void setColor(ObjColor color) {
        this.color = color;
    }
}
