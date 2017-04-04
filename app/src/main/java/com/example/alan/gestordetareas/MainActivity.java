package com.example.alan.gestordetareas;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    int width;
    FloatingActionButton agregar;
    int height;
    FloatingActionButton agregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        agregar=(FloatingActionButton)findViewById(R.id.agregar);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,agregarTarea.class);
                startActivity(i);
            }
        });
    }

    public void agregarTarea(View view){
        Intent i = new Intent(MainActivity.this, agregarTarea.class);
        startActivity(i);
    }
}
