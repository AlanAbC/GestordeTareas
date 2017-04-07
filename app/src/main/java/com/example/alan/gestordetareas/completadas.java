package com.example.alan.gestordetareas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class completadas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_completadas);
    }
}
