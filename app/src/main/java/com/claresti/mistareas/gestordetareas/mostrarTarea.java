package com.claresti.mistareas.gestordetareas;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import static com.claresti.mistareas.gestordetareas.R.id.agregarTarea;
import static com.claresti.mistareas.gestordetareas.R.id.nombre;
import static com.claresti.mistareas.gestordetareas.R.id.tarea;

public class mostrarTarea extends AppCompatActivity {

    //Declaracion variables elementos layout
    private LinearLayout colorf;
    private TextView fondoNombre;
    private TextView nombreTarea;
    private TextView nombreMateria;
    private TextView fechaView;
    private TextView descripcionView;
    private TextView maestro;

    //Objeto tarea
    private ObjTarea tarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_mostrar_tarea);

        // Asignacion elementos layout
        colorf=(LinearLayout)findViewById(nombre);
        fondoNombre = (TextView)findViewById(R.id.fondoNombre);
        nombreTarea = (TextView)findViewById(R.id.nombreT);
        nombreMateria = (TextView)findViewById(R.id.materiaT);
        maestro = (TextView)findViewById(R.id.maestroT);
        fechaView = (TextView)findViewById(R.id.fechaT);
        descripcionView = (TextView)findViewById(R.id.descripcionT);

        // Obtencion del objeto tarea enviado por el comunicador
        tarea = ObjComunicadorTarea.getTarea();

        //Creacion del formato de la fecha que se mostrará
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tarea.getFechaEntrega());
        String[] dias = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Nobiembre", "Diciembre"};
        String fechaMuestra = dias[calendar.get(Calendar.DAY_OF_WEEK) - 1] + " " + calendar.get(Calendar.DAY_OF_MONTH) + " de " + meses[calendar.get(Calendar.MONTH)] + " del " + calendar.get(Calendar.YEAR);

        // Asignacion de los valores del objeto tarea a los elementos del layout
        colorf.setBackgroundColor(Color.parseColor(tarea.getMateria().getColor().getExadecimal()));
        fondoNombre.setText(tarea.getNombre());
        nombreTarea.setText(tarea.getNombre());
        nombreMateria.setText(tarea.getMateria().getNombre());
        fechaView.setText(fechaMuestra);
        descripcionView.setText(tarea.getDescripcion());
        maestro.setText(tarea.getMateria().getProfesor());
    }

    public void modificarTarea(View view){
        ObjComunicadorTarea.setTarea(tarea);
        Intent i = new Intent(mostrarTarea.this, agregarTarea.class);
        i.putExtra("editar", "1");
        startActivity(i);
        finish();
    }

}
