package com.example.alan.gestordetareas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdminBD extends SQLiteOpenHelper {

    public static final int DataBaseVersion = 1;
    public static final String DataBaseName = "GestorTarea.db";

    public AdminBD(Context context) {
        super(context, DataBaseName, null, DataBaseVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String baseDatos = "Create table IF NOT EXISTS Usuario (\n" +
                "\tusuPrimera integer NOT NULL Primary Key,\n" +
                "\tusuNombre Varchar(70),\n" +
                "\tusuImg Varchar(50));\n" +
                "\n" +
                "Create table IF NOT EXISTS Colores (\n" +
                "\tcolId Integer NOT NULL Primary Key AUTOINCREMENT,\n" +
                "\tColNombre varchar(20),\n" +
                "\tcolExa varchar(6));\n" +
                "\n" +
                "Create table IF NOT EXISTS Materias (\n" +
                "\tmatId integer NOT NULL Primary Key AUTOINCREMENT,\n" +
                "\tmatNombre varchar(50),\n" +
                "\tmatAbv varchar(10),\n" +
                "\tmatProfesor varchar(70),\n" +
                "\tcolId Integer NOT NULL);\n" +
                "\n" +
                "Create table IF NOT EXISTS Horario (\n" +
                "\thorId Integer NOT NULL Primary Key AUTOINCREMENT,\n" +
                "\tmatId Integer NOT NULL,\n" +
                "\thorEntrada varchar(10),\n" +
                "\thorSalida varchar(10),\n" +
                "\thorSalon varchar(30),\n" +
                "\thorDia varchar(40));\n" +
                "\n" +
                "Create table IF NOT EXISTS Tareas (\n" +
                "\ttarId Integer NOT NULL Primary Key AUTOINCREMENT,\n" +
                "\ttarNombre varchar(30),\n" +
                "\ttarFechaEntrega DateTime,\n" +
                "\ttarFechaCreacion DateTime,\n" +
                "\ttarDescripcion varchar(150),\n" +
                "\tmatId Integer NOT NULL);\n" +
                "Insert into Colores values\n" +
                "\t(1, 'Rosa', '#F03453'),\n" +
                "\t(2, 'Azul', '#375AE4'),\n" +
                "\t(3, 'Verde', '#37E482'),\n" +
                "\t(4, 'Verde 2', '#93E437'),\n" +
                "\t(5, 'Morado', '#C737E4'),\n" +
                "\t(6, 'Naranja', '#E48837'),\n" +
                "\t(7, 'Azul 2', '#37DEE4'),\n" +
                "\t(8, 'Azul 3', '#3748E4'),\n" +
                "\t(9, 'Rojo', '#E43737'),\n" +
                "\t(10, 'Verde 3', '#51E437'),\n" +
                "\t(11, 'Azul 4', '#37A8E4'),\n" +
                "\t(12, 'Morado 2', '#A837E4'),\n" +
                "\t(13, 'Verde 4', '#074D16'),\n" +
                "\t(14, 'Azul 5', '#074D48'),\n" +
                "\t(15, 'Rojo 2', '#4D1307'),\n" +
                "\t(16, 'Morado 3', '#4D0745'),\n" +
                "\t(17, 'Azul 6', '#10074D'),\n" +
                "\t(18, 'Azul 7', '#074C4D');\n" +
                "\n" +
                "Insert into Usuario values\n" +
                "\t(0, 'USARNAME', 'imgmenu');";

        db.execSQL(baseDatos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Funcion que regresa un Objeto de tipo usuario con la informacion de la base de datos
     * @return ObjUsuario usuario
     */
    public ObjUsuario selectUsuario(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Usuario", null, null, null, null, null, null);
        cursor.moveToFirst();
        ObjUsuario usuario = new ObjUsuario(
                cursor.getString(cursor.getColumnIndex("usuNombre")),
                cursor.getInt(cursor.getColumnIndex("usuPrimera")),
                cursor.getString(cursor.getColumnIndex("usuImg")));
        db.close();
        cursor.close();
        return usuario;
    }

    /**
     * Funcion que actualiza al usuario y cambia el valor de primera vez
     * @param usuario
     * @return 0 si hay algun error y 1 en caso de que se actualice correctamente
     */
    public int updateUsuario(ObjUsuario usuario){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("usuNombre", usuario.getNombre());
            v.put("usuPrimera", 1);
            db.update("Usuario", v, "usuPrimera = 0", null);
            db.close();
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    /**
     * Funcion que regresa todos los colores de la base de datos
     * @return Array List de objetos tipo ObjColor
     */
    public ArrayList<ObjColor> selectColores(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Colores", null, null, null ,null, null ,null);
        ArrayList<ObjColor> colores = new ArrayList<ObjColor>();
        if(cursor.moveToFirst()){
            ObjColor color = new ObjColor(
                    cursor.getInt(cursor.getColumnIndex("colId")),
                    cursor.getString(cursor.getColumnIndex("colNombre")),
                    cursor.getString(cursor.getColumnIndex("colExa")));
            colores.add(color);
        }
        db.close();
        cursor.close();
        return colores;
    }

    /**
     * Funcion que inserta una materia en la base de datos
     * @param materia
     * @return 0 si pasa un error y 1 en caso de que todo salga bien
     */
    public int insertMateria(ObjMateria materia){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("matNombre", materia.getNombre());
            v.put("matAbv", materia.getAbv());
            v.put("matProfesor", materia.getProfesor());
            v.put("colId", materia.getColor().getId());
            db.insert("Materias", null, v);
            db.close();
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    /**
     * Funcion que actualiza la informacion de una materia de la base de datos
     * @param materia
     * @return 0 en caso de error 1 en caso correcto
     */
    public int updatetMateria(ObjMateria materia){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("matNombre", materia.getNombre());
            v.put("matAbv", materia.getAbv());
            v.put("matProfesor", materia.getProfesor());
            v.put("colId", materia.getColor().getId());
            db.update("Materias", v, "matId = " + materia.getId(), null);
            db.close();
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    /**
     * Funcion que elimina una materia de la base de datos
     * @param id
     * @return 0 en caso de un error 1 en caso de que sea correcto
     */
    public int deleteMateria(int id){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete("Materias", "matId = " + id, null);
            db.close();
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    /**
     * Funcion que regresa un array list de materias
     * @return ArrayList<ObjMaterias> materias
     */
    public ArrayList<ObjMateria> selectMaterias(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Materias, Colores WHERE Materias.colId = Colores.colId", null);
        ArrayList<ObjMateria> materias = new ArrayList<ObjMateria>();
        if(cursor.moveToFirst()){
            do{
                ObjColor color = new ObjColor(cursor.getInt(cursor.getColumnIndex("colID")),
                        cursor.getString(cursor.getColumnIndex("colNombre")),
                        cursor.getString(cursor.getColumnIndex("colExa")));
                ObjMateria materia = new ObjMateria(
                        cursor.getInt(cursor.getColumnIndex("matId")),
                        cursor.getString(cursor.getColumnIndex("matNombre")),
                        cursor.getString(cursor.getColumnIndex("matAbv")),
                        cursor.getString(cursor.getColumnIndex("matProfesor")),
                        color);
                materias.add(materia);
            }while(cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return materias;
    }

    /**
     * Funcion que inserta un registro en la tabla Horario de la base de datos
     * @param horario
     * @return 0 en caso de error 1 en caso correcto
     */
    public int insertHorario(ObjHorario horario){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("matId", horario.getMateria().getId());
            v.put("horEntrada", horario.getEntrada());
            v.put("horSalida", horario.getSalida());
            v.put("horSalon", horario.getSalon());
            v.put("horDia", horario.getDia());
            db.insert("Horario", null, v);
            db.close();
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    /**
     * Funcion que actualiza un horario de la base de datos
     * @param horario
     * @return 0 en caso de error y 1 en caso correcto
     */
    public int updateHorario(ObjHorario horario){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("matId", horario.getMateria().getId());
            v.put("horEntrada", horario.getEntrada());
            v.put("horSalida", horario.getSalida());
            v.put("horSalon", horario.getSalon());
            v.put("horDia", horario.getDia());
            db.update("Horario", v, "horId = " + horario.getId(), null);
            db.close();
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    /**
     * Funcion que elimina un registro de horario
     * @param id
     * @return 0 en caso de error y 1 en caso correcto
     */
    public int deleteHorario(int id){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete("Horario", "horId = " + id, null);
            db.close();
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    /**
     * Funcion que regresa un array list con todos los horarios de la base de datos
     * @return Array List de objetos tipos ObjHorario
     */
    public ArrayList<ObjHorario> selectHorarios(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Horaio", null);
        ArrayList<ObjMateria> materias = selectMaterias();
        ArrayList<ObjHorario> horarios = new ArrayList<ObjHorario>();
        if(cursor.moveToFirst()){
            do{
                for(ObjMateria materia : materias){
                    if((cursor.getInt(cursor.getColumnIndex("matId")) == materia.getId())){
                        ObjHorario horario = new ObjHorario(
                                cursor.getInt(cursor.getColumnIndex("horId")),
                                materia,
                                cursor.getString(cursor.getColumnIndex("horEntrada")),
                                cursor.getString(cursor.getColumnIndex("horSalida")),
                                cursor.getString(cursor.getColumnIndex("horSalon")),
                                cursor.getString(cursor.getColumnIndex("horDia")));
                        horarios.add(horario);
                    }
                }
            }while(cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return horarios;
    }

    /**
     * Funcion que regresa una lista con todas las tareas que hay en la base de datos
     * @return Array List de Objetos tipo ObjTarea
     * @throws ParseException
     */
    public ArrayList<ObjTarea> selectTareas() throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Tareas", null);
        ArrayList<ObjMateria> materias = selectMaterias();
        ArrayList<ObjTarea> tareas = new ArrayList<ObjTarea>();
        if(cursor.moveToFirst()){
            SimpleDateFormat sdf = new SimpleDateFormat();
            do{
                for(ObjMateria materia : materias){
                    if((cursor.getInt(cursor.getColumnIndex("matId")) == materia.getId())){
                        ObjTarea tarea = new ObjTarea(
                                cursor.getInt(cursor.getColumnIndex("tarId")),
                                cursor.getString(cursor.getColumnIndex("tarNombre")),
                                sdf.parse(cursor.getString(cursor.getColumnIndex("tarFechaEntrega"))),
                                sdf.parse(cursor.getString(cursor.getColumnIndex("tarFechaCreacion"))),
                                cursor.getString(cursor.getColumnIndex("tarDescripcion")),
                                materia);
                        tareas.add(tarea);
                    }
                }
            }while(cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return tareas;
    }

    /**
     * funcion para insertar una nueva tarea a la base de datos
     * @param tarea
     * @return 0 en caso de error y 1 en caso correcto
     */
    public int insertTarea(ObjTarea tarea){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("tarNombre", tarea.getNombre());
            v.put("tarFechaEntrega", tarea.getFechaEntrega().toString());
            v.put("tarFechaCreacion", tarea.getFechaCreacion().toString());
            v.put("tarDescripcion", tarea.getDescripcion());
            v.put("matId", tarea.getMateria().getId());
            db.insert("Tareas", null, v);
            db.close();
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    /**
     * funcion para actualizar una tarea de la base de datos
     * @param tarea
     * @return 0 en caso de error y 1 en caso correcto
     */
    public int updateTarea(ObjTarea tarea){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("tarNombre", tarea.getNombre());
            v.put("tarFechaEntrega", tarea.getFechaEntrega().toString());
            v.put("tarFechaCreacion", tarea.getFechaCreacion().toString());
            v.put("tarDescripcion", tarea.getDescripcion());
            v.put("matId", tarea.getMateria().getId());
            db.update("Tareas", v, "tarId = " + tarea.getId(), null);
            db.close();
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    /**
     * Funcion para eliminar una tarea
     * @param id
     * @return 0 en caso de error y 1 en caso correcto
     */
    public int deleteTarea(int id){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete("Tareas", "tarId = " + id, null);
            db.close();
            return 1;
        }catch(Exception e){
            return 0;
        }
    }
}
