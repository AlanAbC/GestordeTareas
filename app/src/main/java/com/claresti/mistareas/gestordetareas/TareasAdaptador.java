package com.claresti.mistareas.gestordetareas;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TareasAdaptador extends BaseAdapter {

    private LayoutInflater inflator;
    private ArrayList<ObjTarea> tareas;
    private Context context;
    private FragmentManager fragmentManager;
    private AdminBD db;
    private Activity activity;

    public TareasAdaptador(Context context, ArrayList<ObjTarea> tareas, FragmentManager fragmentManager, Activity activity) {
        inflator = LayoutInflater.from(context);
        this.tareas = tareas;
        this.context = context;
        this.fragmentManager = fragmentManager;
        db = new AdminBD(context);
        this.activity = activity;
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
        final ObjTarea tarea = tareas.get(position);
        convertView = inflator.inflate(R.layout.tarea, null);
        final RelativeLayout layout = (RelativeLayout)convertView.findViewById(R.id.layout);
        TextView colorBarra = (TextView) convertView.findViewById(R.id.colorMat);
        TextView nombreTarea = (TextView) convertView.findViewById(R.id.tarea);
        TextView nombreMateria = (TextView) convertView.findViewById(R.id.materia);
        final ImageButton cambiarEstado = (ImageButton) convertView.findViewById(R.id.echa);
        final ImageButton eliminar = (ImageButton) convertView.findViewById(R.id.borrar);
        colorBarra.setBackgroundColor(Color.parseColor(tarea.getMateria().getColor().getExadecimal()));
        nombreTarea.setText(tarea.getNombre());
        nombreMateria.setText(tarea.getMateria().getNombre());
        cambiarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click:", "Se preciono el boton de cambiar estado de: " + tarea.getNombre());
                int estado = 0;
                if(tarea.getCompletado() == 0){
                    estado = 1;
                }
                tarea.setCompletado(estado);
                int flag = db.updateTarea(tarea);
                if(flag == 1){
                    Intent i = new Intent(activity, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(i);
                    activity.finish();
                    Toast.makeText(context, "Se cambió el estado correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click:", "Se preciono el boton de eliminar de: " + tarea.getNombre());
                DialogoAdvertenciaTareas advertencia = new DialogoAdvertenciaTareas();
                Bundle variable = new Bundle();
                variable.putInt("id", tarea.getId());
                advertencia.setArguments(variable);
                advertencia.show(fragmentManager, "Advertencia");
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), mostrarTarea.class);
                ObjComunicadorTarea.setTarea(tarea);
                /*
                i.putExtra("color", tarea.getMateria().getColor().getExadecimal());
                i.putExtra("nombre", tarea.getNombre());
                i.putExtra("materia", tarea.getMateria().getNombre());
                i.putExtra("fecha", tarea.getFechaEntrega().getTime());
                i.putExtra("descripcion", tarea.getDescripcion());
                */
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int accion = event.getActionMasked();
                switch(accion){
                    case MotionEvent.ACTION_DOWN:
                        layout.setBackgroundColor(Color.parseColor("#EEEEEE"));
                        cambiarEstado.setBackgroundColor(Color.parseColor("#EEEEEE"));
                        eliminar.setBackgroundColor(Color.parseColor("#EEEEEE"));
                        break;
                    case MotionEvent.ACTION_UP:
                        layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        cambiarEstado.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        eliminar.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        break;
                    case MotionEvent.ACTION_OUTSIDE:
                        layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        cambiarEstado.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        eliminar.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        break;
                    case MotionEvent.ACTION_HOVER_ENTER:
                        layout.setBackgroundColor(Color.parseColor("#EEEEEE"));
                        cambiarEstado.setBackgroundColor(Color.parseColor("#EEEEEE"));
                        eliminar.setBackgroundColor(Color.parseColor("#EEEEEE"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        cambiarEstado.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        eliminar.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        break;
                }
                return false;
            }
        });
        return convertView;
    }
}
