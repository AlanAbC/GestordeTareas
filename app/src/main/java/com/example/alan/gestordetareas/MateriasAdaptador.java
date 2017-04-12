package com.example.alan.gestordetareas;

import android.content.Context;
import android.graphics.Color;
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

    LayoutInflater inflator;
    ArrayList<ObjMateria> materias;

    public MateriasAdaptador(Context context, ArrayList<ObjMateria> materias) {
        inflator = LayoutInflater.from(context);
        this.materias = materias;
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
        ObjMateria materia = materias.get(position);
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
                Log.i("Si funciona:", "Boton de editar");
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Si funciona:", "Boton de eliminar");
            }
        });
        return convertView;
    }
}
