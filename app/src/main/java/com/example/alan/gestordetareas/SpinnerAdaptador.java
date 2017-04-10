package com.example.alan.gestordetareas;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SpinnerAdaptador extends BaseAdapter{

    LayoutInflater inflator;
    String[] colores;

    public SpinnerAdaptador(Context context, String[] colores) {
        inflator = LayoutInflater.from(context);
        this.colores = colores;
    }

    @Override
    public int getCount() {
        return colores.length;
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
        convertView = inflator.inflate(R.layout.spinner_colores, null);
        TextView text = (TextView) convertView.findViewById(R.id.txt_colorFondo);
        text.setBackgroundColor(Color.parseColor(colores[position]));
        return convertView;
    }
}
