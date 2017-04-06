package com.example.alan.gestordetareas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

public class agregar_materia extends AppCompatActivity {
    CheckBox lunes;
    int flagLunes=0;
    RelativeLayout conLunes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_agregar_materia);
        lunes=(CheckBox)findViewById(R.id.checkLunes);
        conLunes=(RelativeLayout)findViewById(R.id.conLunes);
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
    }
}
