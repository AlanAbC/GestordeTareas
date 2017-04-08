package com.example.alan.gestordetareas;

public class ObjHorario {

    private int id;
    private ObjMateria materia;
    private String entrada;
    private String salida;
    private String salon;
    private String dia;

    public ObjHorario(int id, ObjMateria materia, String entrada, String salida, String salon, String dia) {
        this.id = id;
        this.materia = materia;
        this.entrada = entrada;
        this.salida = salida;
        this.salon = salon;
        this.dia = dia;
    }

    public ObjHorario(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ObjMateria getMateria() {
        return materia;
    }

    public void setMateria(ObjMateria materia) {
        this.materia = materia;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
