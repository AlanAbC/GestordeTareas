package com.claresti.mistareas.gestordetareas;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MateriasAdaptador extends BaseAdapter {

    private LayoutInflater inflator;
    private ArrayList<ObjMateria> materias;
    private Context context;
    private AdminBD db;
    private FragmentManager fragmentManager;
    private Activity activity;

    public MateriasAdaptador(Context context, ArrayList<ObjMateria> materias, FragmentManager fragmentManager, Activity activity) {
        inflator = LayoutInflater.from(context);
        this.materias = materias;
        this.context = context;
        db = new AdminBD(context);
        this.fragmentManager = fragmentManager;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return materias.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ObjMateria materia = materias.get(position);
        convertView = inflator.inflate(R.layout.materia, null);
        TextView colorBarra = (TextView) convertView.findViewById(R.id.colorFondoMateria);
        TextView nombreMateria = (TextView) convertView.findViewById(R.id.materia);
        TextView nombreMaestro = (TextView) convertView.findViewById(R.id.maestro);
        ImageButton editar = (ImageButton) convertView.findViewById(R.id.editar);
        ImageButton eliminar = (ImageButton) convertView.findViewById(R.id.borrar);
        colorBarra.setBackgroundColor(Color.parseColor(materia.getColor().getExadecimal()));
        nombreMateria.setText(materia.getNombre());
        nombreMaestro.setText(materia.getProfesor());
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, agregar_materia.class);
                i.putExtra("editar", "1");
                ObjComunicadorMateria.setMateria(materia);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(i);
                activity.finish();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Si funciona:", "Boton de eliminar");
                DialogoAdvertenciaMateria advertencia = new DialogoAdvertenciaMateria();
                Bundle variable = new Bundle();
                variable.putInt("id", materia.getId());
                advertencia.setArguments(variable);
                advertencia.show(fragmentManager, "Advertencia");
            }
        });
        return convertView;
    }
}
