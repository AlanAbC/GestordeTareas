package com.claresti.mistareas.gestordetareas;

/**
 * Created by pizano on 8/06/17.
 */

public class ObjComunicadorTarea {

    private static ObjTarea tarea = null;

    public static void setTarea(ObjTarea objTarea){
        tarea = objTarea;
    }

    public static ObjTarea getTarea(){
        return tarea;
    }
}
