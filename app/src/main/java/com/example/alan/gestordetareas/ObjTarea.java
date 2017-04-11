package com.example.alan.gestordetareas;

import java.util.Date;

public class ObjTarea {

    private int id;
    private String nombre;
    private Date fechaEntrega;
    private Date fechaCreacion;
    private String descripcion;
    private ObjMateria materia;
    private int completado;

    public ObjTarea(int id, String nombre, Date fechaEntrega, Date fechaCreacion, String descripcion, ObjMateria materia, int completado) {
        this.id = id;
        this.nombre = nombre;
        this.fechaEntrega = fechaEntrega;
        this.fechaCreacion = fechaCreacion;
        this.descripcion = descripcion;
        this.materia = materia;
        this.completado = completado;
    }

    public ObjTarea(){

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

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ObjMateria getMateria() {
        return materia;
    }

    public void setMateria(ObjMateria materia) {
        this.materia = materia;
    }

    public int getCompletado() {
        return completado;
    }

    public void setCompletado(int completado) {
        this.completado = completado;
    }
}
