package com.claresti.mistareas.gestordetareas;

/**
 * Created by pizano on 8/06/17.
 */

public class ObjComunicadorMateria {

    public static ObjMateria materia = null;

    public static void setMateria(ObjMateria objMateria){
        materia = objMateria;
    }

    public static ObjMateria getMateria(){
        return materia;
    }

}
