package com.example.alan.gestordetareas;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TareasAdaptador extends BaseAdapter {

    LayoutInflater inflator;
    ArrayList<ObjTarea> tareas;

    public TareasAdaptador(Context context, ArrayList<ObjTarea> tareas) {
        inflator = LayoutInflater.from(context);
        this.tareas = tareas;
    }

    @Override
    public int getCount() {
        return tareas.size();
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
        ObjTarea tarea = tareas.get(position);
        convertView = inflator.inflate(R.layout.tarea, null);
        TextView colorBarra = (TextView) convertView.findViewById(R.id.colorMat);
        TextView nombreTarea = (TextView) convertView.findViewById(R.id.tarea);
        TextView nombreMateria = (TextView) convertView.findViewById(R.id.materia);
        colorBarra.setBackgroundColor(Color.parseColor(tarea.getMateria().getColor().getExadecimal()));
        nombreTarea.setText(tarea.getNombre());
        nombreMateria.setText(tarea.getMateria().getNombre());
        return convertView;
    }
}
