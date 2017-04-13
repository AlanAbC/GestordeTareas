package com.example.alan.gestordetareas;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MateriasAdaptador extends BaseAdapter {

    private LayoutInflater inflator;
    private ArrayList<ObjMateria> materias;
    private Context context;
    private AdminBD db;
    private FragmentManager fragmentManager;

    public MateriasAdaptador(Context context, ArrayList<ObjMateria> materias, FragmentManager fragmentManager) {
        inflator = LayoutInflater.from(context);
        this.materias = materias;
        this.context = context;
        db = new AdminBD(context);
        this.fragmentManager = fragmentManager;
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
        //ImageButton editar = (ImageButton) convertView.findViewById(R.id.editar);
        ImageButton eliminar = (ImageButton) convertView.findViewById(R.id.borrar);
        colorBarra.setBackgroundColor(Color.parseColor(materia.getColor().getExadecimal()));
        nombreMateria.setText(materia.getNombre());
        nombreMaestro.setText(materia.getProfesor());
        /*
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Si funciona:", "Boton de editar");
            }
        });
        */
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
