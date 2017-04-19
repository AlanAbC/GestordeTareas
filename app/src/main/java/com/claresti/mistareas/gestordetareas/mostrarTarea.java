package com.claresti.mistareas.gestordetareas;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class mostrarTarea extends AppCompatActivity {
    LinearLayout colorf;
    TextView fondoNombre;
    TextView nombreTarea;
    TextView nombreMateria;
    TextView fechaView;
    TextView descripcionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_mostrar_tarea);
        colorf=(LinearLayout)findViewById(R.id.nombre);
        fondoNombre = (TextView)findViewById(R.id.fondoNombre);
        nombreTarea = (TextView)findViewById(R.id.nombreT);
        nombreMateria = (TextView)findViewById(R.id.materiaT);
        fechaView = (TextView)findViewById(R.id.fechaT);
        descripcionView = (TextView)findViewById(R.id.descripcionT);
        String color = (String) getIntent().getExtras().getSerializable("color");
        String nombre = (String) getIntent().getExtras().getSerializable("nombre");
        String materia = (String) getIntent().getExtras().getSerializable("materia");
        long fecha = (long) getIntent().getExtras().getSerializable("fecha");
        String descripcion = (String) getIntent().getExtras().getSerializable("descripcion");
        Calendar calendar = Calendar.getInstance();
        Date fechaen = new Date(fecha);
        calendar.setTime(fechaen);
        String[] dias = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Nobiembre", "Diciembre"};
        String fechaMuestra = dias[calendar.get(Calendar.DAY_OF_WEEK) - 1] + " " + calendar.get(Calendar.DAY_OF_MONTH) + " de " + meses[calendar.get(Calendar.MONTH)] + " del " + calendar.get(Calendar.YEAR);
        //String fechaMuestra = calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
        colorf.setBackgroundColor(Color.parseColor(color));
        fondoNombre.setText(nombre);
        nombreTarea.setText(nombre);
        nombreMateria.setText(materia);
        fechaView.setText(fechaMuestra);
        descripcionView.setText(descripcion);
    }


}
