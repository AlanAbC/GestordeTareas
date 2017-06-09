package com.claresti.mistareas.gestordetareas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AdminBD extends SQLiteOpenHelper {

    public static final int DataBaseVersion = 1;
    public static final String DataBaseName = "GestorTarea.db";

    public AdminBD(Context context) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String usuario = "Create table Usuario (" +
                    "usuPrimera integer NOT NULL PRIMARY KEY," +
                    "usuNombre TEXT," +
                    "usuImg TEXT)";
        String colores = "Create table Colores (" +
                    "colId Integer NOT NULL Primary Key AUTOINCREMENT," +
                    "colNombre TEXT," +
                    "colExa TEXT)";
        String materias = "Create table Materias (" +
                    "matId integer NOT NULL Primary Key AUTOINCREMENT," +
                    "matNombre TEXT," +
                    "matAbv TEXT," +
                    "matProfesor TEXT," +
                    "colId Integer NOT NULL)";
        String horarios = "Create table Horario (" +
                    "horId Integer NOT NULL Primary Key AUTOINCREMENT," +
                    "matId Integer NOT NULL," +
                    "horEntrada TEXT," +
                    "horSalida TEXT," +
                    "horSalon TEXT," +
                    "horDia TEXT)";
        String tareas = "Create table Tareas (" +
                    "tarId Integer NOT NULL Primary Key AUTOINCREMENT," +
                    "tarNombre TEXT," +
                    "tarFechaEntrega DateTime," +
                    "tarFechaCreacion DateTime," +
                    "tarDescripcion TEXT," +
                    "tarCompletado Integer," +
                    "matId Integer NOT NULL);";

        //Tablas
        db.execSQL(usuario);
        db.execSQL(colores);
        db.execSQL(materias);
        db.execSQL(horarios);
        db.execSQL(tareas);

        //Usuario
        ContentValues v = new ContentValues();
        v.put("usuPrimera", "0");
        v.put("usuNombre", "Usuario");
        v.put("usuImg", "imgmenu");
        db.insert("Usuario", null, v);

        //Colores
        ContentValues c1 = new ContentValues();
        c1.put("colNombre", "Rosa");
        c1.put("colExa", "#F03453");
        db.insert("Colores", null, c1);

        ContentValues c2 = new ContentValues();
        c2.put("colNombre", "Azul");
        c2.put("colExa", "#375AE4");
        db.insert("Colores", null, c2);

        ContentValues c3 = new ContentValues();
        c3.put("colNombre", "Verde");
        c3.put("colExa", "#37E482");
        db.insert("Colores", null, c3);

        ContentValues c4 = new ContentValues();
        c4.put("colNombre", "Verde");
        c4.put("colExa", "#93E437");
        db.insert("Colores", null, c4);

        ContentValues c5 = new ContentValues();
        c5.put("colNombre", "Morado");
        c5.put("colExa", "#C737E4");
        db.insert("Colores", null, c5);

        ContentValues c6 = new ContentValues();
        c6.put("colNombre", "Naranja");
        c6.put("colExa", "#E48837");
        db.insert("Colores", null, c6);

        ContentValues c7 = new ContentValues();
        c7.put("colNombre", "Azul");
        c7.put("colExa", "#37DEE4");
        db.insert("Colores", null, c7);

        ContentValues c8 = new ContentValues();
        c8.put("colNombre", "Azul");
        c8.put("colExa", "#3748E4");
        db.insert("Colores", null, c8);

        ContentValues c9 = new ContentValues();
        c9.put("colNombre", "Rojo");
        c9.put("colExa", "#E43737");
        db.insert("Colores", null, c9);

        ContentValues c10 = new ContentValues();
        c10.put("colNombre", "Verde");
        c10.put("colExa", "#51E437");
        db.insert("Colores", null, c10);

        ContentValues c11 = new ContentValues();
        c11.put("colNombre", "Azul");
        c11.put("colExa", "#37A8E4");
        db.insert("Colores", null, c11);

        ContentValues c12 = new ContentValues();
        c12.put("colNombre", "Morado");
        c12.put("colExa", "#A837E4");
        db.insert("Colores", null, c12);

        ContentValues c13 = new ContentValues();
        c13.put("colNombre", "Verde");
        c13.put("colExa", "#074D16");
        db.insert("Colores", null, c13);

        ContentValues c14 = new ContentValues();
        c14.put("colNombre", "Azul");
        c14.put("colExa", "#074D48");
        db.insert("Colores", null, c14);

        ContentValues c15 = new ContentValues();
        c15.put("colNombre", "Rojo");
        c15.put("colExa", "#4D1307");
        db.insert("Colores", null, c15);

        ContentValues c16 = new ContentValues();
        c16.put("colNombre", "Morado");
        c16.put("colExa", "#4D0745");
        db.insert("Colores", null, c16);

        ContentValues c17 = new ContentValues();
        c17.put("colNombre", "Azul");
        c17.put("colExa", "#10074D");
        db.insert("Colores", null, c17);

        ContentValues c18 = new ContentValues();
        c18.put("colNombre", "Azul");
        c18.put("colExa", "#074C4D");
        db.insert("Colores", null, c18);

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
        if(cursor.moveToFirst()){
            ObjUsuario usuario = new ObjUsuario(
                    cursor.getString(cursor.getColumnIndex("usuNombre")),
                    cursor.getInt(cursor.getColumnIndex("usuPrimera")),
                    cursor.getString(cursor.getColumnIndex("usuImg")));
            return usuario;
        }
        return null;
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
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    public String updateUsuarioImg(ObjUsuario usuario){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("usuNombre", usuario.getNombre());
            v.put("usuImg", usuario.getImg());
            db.update("Usuario", v, "usuPrimera = 1", null);
            return "1";
        }catch(Exception e){
            return e.toString();
        }
    }

    /**
     * Funcion que regresa todos los colores de la base de datos
     * @return Array List de objetos tipo ObjColor
     */
    public ArrayList<ObjColor> selectColores(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Colores", null, null, null, null, null, null);
        ArrayList<ObjColor> colores = new ArrayList<ObjColor>();
        if(cursor.moveToFirst()){
            do {
                ObjColor color = new ObjColor(
                        cursor.getInt(cursor.getColumnIndex("colId")),
                        cursor.getString(cursor.getColumnIndex("colNombre")),
                        cursor.getString(cursor.getColumnIndex("colExa")));
                colores.add(color);
            }while(cursor.moveToNext());
        }
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
            db.delete("Horario", "matId = " + id, null);
            db.delete("Tareas", "matId = " + id, null);
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
                ObjColor color = new ObjColor(cursor.getInt(cursor.getColumnIndex("colId")),
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
        return materias;
    }

    public ObjMateria findMateria(String nombre){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Materias, Colores WHERE Materias.colId = Colores.colId AND Materias.matNombre = '" + nombre + "'", null);
        if(cursor.moveToFirst()){
            ObjColor color = new ObjColor(cursor.getInt(cursor.getColumnIndex("colId")),
                    cursor.getString(cursor.getColumnIndex("colNombre")),
                    cursor.getString(cursor.getColumnIndex("colExa")));
            ObjMateria materia = new ObjMateria(
                    cursor.getInt(cursor.getColumnIndex("matId")),
                    cursor.getString(cursor.getColumnIndex("matNombre")),
                    cursor.getString(cursor.getColumnIndex("matAbv")),
                    cursor.getString(cursor.getColumnIndex("matProfesor")),
                    color);
            return materia;
        }
        return null;
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
        Cursor cursor = db.rawQuery("SELECT * FROM Horario", null);
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
        return horarios;
    }

    /**
     * Funcion que regresa una lista con todas las tareas que hay en la base de datos
     * @return Array List de Objetos tipo ObjTarea
     * @throws ParseException
     */
    public ArrayList<ObjTarea> selectTareas(){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Tareas", null);
            ArrayList<ObjMateria> materias = selectMaterias();
            ArrayList<ObjTarea> tareas = new ArrayList<ObjTarea>();
            if (cursor.moveToFirst()) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                do {
                    for (ObjMateria materia : materias) {
                        if ((cursor.getInt(cursor.getColumnIndex("matId")) == materia.getId())) {
                            ObjTarea tarea = new ObjTarea(
                                    cursor.getInt(cursor.getColumnIndex("tarId")),
                                    cursor.getString(cursor.getColumnIndex("tarNombre")),
                                    sdf.parse(cursor.getString(cursor.getColumnIndex("tarFechaEntrega"))),
                                    sdf.parse(cursor.getString(cursor.getColumnIndex("tarFechaCreacion"))),
                                    cursor.getString(cursor.getColumnIndex("tarDescripcion")),
                                    materia,
                                    cursor.getInt(cursor.getColumnIndex("tarCompletado")));
                            tareas.add(tarea);
                        }
                    }
                } while (cursor.moveToNext());
            }
            return tareas;
        }catch(Exception e){
            Log.e("Error al parseo", e.getMessage());
            ArrayList<ObjTarea> tareas = new ArrayList<ObjTarea>();
            return tareas;
        }
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
            v.put("tarFechaCreacion", Calendar.getInstance().getTime().toString());
            v.put("tarDescripcion", tarea.getDescripcion());
            v.put("matId", tarea.getMateria().getId());
            v.put("tarCompletado", tarea.getCompletado());
            db.insert("Tareas", null, v);
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
            v.put("tarDescripcion", tarea.getDescripcion());
            v.put("matId", tarea.getMateria().getId());
            v.put("tarCompletado", tarea.getCompletado());
            db.update("Tareas", v, "tarId = " + tarea.getId(), null);
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
            return 1;
        }catch(Exception e){
            return 0;
        }
    }
}
