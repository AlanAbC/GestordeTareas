package com.example.alan.gestordetareas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

public class agregar_materia extends AppCompatActivity {
    CheckBox lunes;
    CheckBox martes;
    CheckBox miercoles;
    CheckBox jueves;
    CheckBox viernes;
    CheckBox sabado;
    int flagLunes=0;
    int flagMartes=0;
    int flagMiercoles=0;
    int flagJueves=0;
    int flagViernes=0;
    int flagSabado=0;
    RelativeLayout conLunes;
    RelativeLayout conMartes;
    RelativeLayout conMiercoles;
    RelativeLayout conJueves;
    RelativeLayout conViernes;
    RelativeLayout conSabado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_agregar_materia);
        lunes=(CheckBox)findViewById(R.id.checkLunes);
        conLunes=(RelativeLayout)findViewById(R.id.conLunes);
        martes=(CheckBox)findViewById(R.id.checkMartes);
        conMartes=(RelativeLayout)findViewById(R.id.conMartes);
        miercoles=(CheckBox)findViewById(R.id.checkMiercoles);
        conMiercoles=(RelativeLayout)findViewById(R.id.conMiercoles);
        jueves=(CheckBox)findViewById(R.id.checkJueves);
        conJueves=(RelativeLayout)findViewById(R.id.conJueves);
        viernes=(CheckBox)findViewById(R.id.checkViernes);
        conViernes=(RelativeLayout)findViewById(R.id.conViernes);
        sabado=(CheckBox)findViewById(R.id.checkSabado);
        conSabado=(RelativeLayout)findViewById(R.id.conSabado);
        lunes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(flagLunes==0){
                    conLunes.setVisibility(View.VISIBLE);
                    flagLunes=1;
                }else{
                    conLunes.setVisibility(View.GONE);
                    flagLunes=0;
                }
            }
        });
        martes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(flagMartes==0){
                    conMartes.setVisibility(View.VISIBLE);
                    flagMartes=1;
                }else{
                    conMartes.setVisibility(View.GONE);
                    flagMartes=0;
                }
            }
        });
        miercoles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(flagMiercoles==0){
                    conMiercoles.setVisibility(View.VISIBLE);
                    flagMiercoles=1;
                }else{
                    conMiercoles.setVisibility(View.GONE);
                    flagMiercoles=0;
                }
            }
        });
        jueves.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(flagJueves==0){
                    conJueves.setVisibility(View.VISIBLE);
                    flagJueves=1;
                }else{
                    conJueves.setVisibility(View.GONE);
                    flagJueves=0;
                }
            }
        });
        viernes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(flagViernes==0){
                    conViernes.setVisibility(View.VISIBLE);
                    flagViernes=1;
                }else{
                    conViernes.setVisibility(View.GONE);
                    flagViernes=0;
                }
            }
        });
        sabado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(flagSabado==0){
                    conSabado.setVisibility(View.VISIBLE);
                    flagSabado=1;
                }else{
                    conSabado.setVisibility(View.GONE);
                    flagSabado=0;
                }
            }
        });
    }
}
