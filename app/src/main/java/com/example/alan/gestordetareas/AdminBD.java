package com.example.alan.gestordetareas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AdminBD extends SQLiteOpenHelper {

    public static final int DataBaseVersion = 1;
    public static final String DataBaseName = "GestorTarea";

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
        Cursor cursor = db.rawQuery("SELECT * FROM Usuario", null);
        cursor.moveToFirst();
        ObjUsuario usuario = new ObjUsuario(cursor.getString(1), cursor.getInt(0), cursor.getString(2));
        db.close();
        cursor.close();
        return usuario;
    }

    /**
     * Funcion que actualiza al usuario y cambia el valor de primera vez
     * @param nombre
     * @return 0 si hay algun error y 1 en caso de que se actualice correctamente
     */
    public int updateUsuario(String nombre){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("usuNombre", nombre);
            v.put("usuPrimera", 1);
            db.update("Usuario", v, "usuPrimera = 0", null);
            db.close();
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    /**
     * Funcion que inserta una materia en la base de datos
     * @param nombre
     * @param avb
     * @param profesor
     * @param color
     * @return 0 si pasa un error y 1 en caso de que todo salga bien
     */
    public int insertMateria(String nombre, String avb, String profesor, int color){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("matNombre", nombre);
            v.put("matAbv", avb);
            v.put("matProfesor", profesor);
            v.put("colId", color);
            db.insert("Materias", null, v);
            db.close();
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    /**
     * Funcion que actualiza la informacion de una materia de la base de datos
     * @param id
     * @param nombre
     * @param avb
     * @param profesor
     * @param color
     * @return 0 en caso de error 1 en caso correcto
     */
    public int updatetMateria(int id, String nombre, String avb, String profesor, int color){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("matNombre", nombre);
            v.put("matAbv", avb);
            v.put("matProfesor", profesor);
            v.put("colId", color);
            db.update("Materias", v, "matId = " + id, null);
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
                ObjColor color = new ObjColor(cursor.getInt(5), cursor.getString(6), cursor.getString(7));
                ObjMateria materia = new ObjMateria(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), color);
                materias.add(materia);
            }while(cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return materias;
    }

    /**
     * Funcion que inserta un registro en la tabla Horario de la base de datos
     * @param materia
     * @param entrada
     * @param salida
     * @param salon
     * @param dia
     * @return 0 en caso de error 1 en caso correcto
     */
    public int insertHorario(int materia, String entrada, String salida, String salon, String dia){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("matId", materia);
            v.put("horEntrada", entrada);
            v.put("horSalida", salida);
            v.put("horSalon", salon);
            v.put("horDia", dia);
            db.insert("Horario", null, v);
            db.close();
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    /**
     * Funcion que actualiza un horario de la base de datos
     * @param id
     * @param materia
     * @param entrada
     * @param salida
     * @param salon
     * @param dia
     * @return 0 en caso de error y 1 en caso correcto
     */
    public int updateHorario(int id, int materia, String entrada, String salida, String salon, String dia){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("matId", materia);
            v.put("horEntrada", entrada);
            v.put("horSalida", salida);
            v.put("horSalon", salon);
            v.put("horDia", dia);
            db.update("Horario", v, "horId = " + id, null);
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


}
