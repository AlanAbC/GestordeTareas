package com.example.alan.gestordetareas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class agregar_materia extends AppCompatActivity {

    private AdminBD db; //Variable del administrador de la base de datos
    //Variables de los elementos del Layout
    private EditText nombre;
    private EditText abreviacion;
    private EditText profesor;
    private Spinner spinner_colores;
    private CheckBox lunes;
    private CheckBox martes;
    private CheckBox miercoles;
    private CheckBox jueves;
    private CheckBox viernes;
    private CheckBox sabado;
    private int flagLunes=0;
    private int flagMartes=0;
    private int flagMiercoles=0;
    private int flagJueves=0;
    private int flagViernes=0;
    private int flagSabado=0;
    private RelativeLayout conLunes;
    private RelativeLayout conMartes;
    private RelativeLayout conMiercoles;
    private RelativeLayout conJueves;
    private RelativeLayout conViernes;
    private RelativeLayout conSabado;
    //Fin Elementos del Layout
    //Menu, Declaracion de variables
    private DrawerLayout drawerLayout;
    private final List<MenuItem> items = new ArrayList<>();
    private Menu menu;
    private ImageView btnMenu;
    private NavigationView nav;
    //Fin menu, declaracion de variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_agregar_materia);
        //Cambiar el color en la barra de notificaciones (Solo funciona de lollipop hacia arriba)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.agregar));
        }
        //Fin cambio de color de barra de notificaciones
        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        menuNav();
        // Fin menu
        //Codigo para crear el objeto de la base de datos y
        //agregar el nombre de usuario al menu
        db = new AdminBD(this);
        ObjUsuario usuario = db.selectUsuario();
        //Codigo para poner en el Menu el nombre de usuario
        View header = nav.getHeaderView(0);
        TextView nombreUsuario = (TextView) header.findViewById(R.id.menuNombreUsuario);
        nombreUsuario.setText(usuario.getNombre());
        //Fin Codigo para poner el nombre de usuario en el menu
        nombre = (EditText)findViewById(R.id.nombreT);
        abreviacion = (EditText)findViewById(R.id.abreviacionT);
        profesor = (EditText)findViewById(R.id.maestroT);
        spinner_colores = (Spinner)findViewById(R.id.colorS);
        llenarColores();
        lunes = (CheckBox)findViewById(R.id.checkLunes);
        conLunes = (RelativeLayout)findViewById(R.id.conLunes);
        martes = (CheckBox)findViewById(R.id.checkMartes);
        conMartes = (RelativeLayout)findViewById(R.id.conMartes);
        miercoles = (CheckBox)findViewById(R.id.checkMiercoles);
        conMiercoles = (RelativeLayout)findViewById(R.id.conMiercoles);
        jueves = (CheckBox)findViewById(R.id.checkJueves);
        conJueves = (RelativeLayout)findViewById(R.id.conJueves);
        viernes = (CheckBox)findViewById(R.id.checkViernes);
        conViernes = (RelativeLayout)findViewById(R.id.conViernes);
        sabado = (CheckBox)findViewById(R.id.checkSabado);
        conSabado = (RelativeLayout)findViewById(R.id.conSabado);
        lunes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(flagLunes == 0){
                    conLunes.setVisibility(View.VISIBLE);
                    flagLunes = 1;
                }else{
                    conLunes.setVisibility(View.GONE);
                    flagLunes = 0;
                }
            }
        });
        martes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(flagMartes == 0){
                    conMartes.setVisibility(View.VISIBLE);
                    flagMartes = 1;
                }else{
                    conMartes.setVisibility(View.GONE);
                    flagMartes = 0;
                }
            }
        });
        miercoles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(flagMiercoles == 0){
                    conMiercoles.setVisibility(View.VISIBLE);
                    flagMiercoles = 1;
                }else{
                    conMiercoles.setVisibility(View.GONE);
                    flagMiercoles = 0;
                }
            }
        });
        jueves.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(flagJueves == 0){
                    conJueves.setVisibility(View.VISIBLE);
                    flagJueves = 1;
                }else{
                    conJueves.setVisibility(View.GONE);
                    flagJueves = 0;
                }
            }
        });
        viernes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(flagViernes == 0){
                    conViernes.setVisibility(View.VISIBLE);
                    flagViernes = 1;
                }else{
                    conViernes.setVisibility(View.GONE);
                    flagViernes = 0;
                }
            }
        });
        sabado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(flagSabado == 0){
                    conSabado.setVisibility(View.VISIBLE);
                    flagSabado = 1;
                }else{
                    conSabado.setVisibility(View.GONE);
                    flagSabado = 0;
                }
            }
        });
    }

    /**
     * Funcion encargada del menu
     * Muestra, oculta y redirecciona el menu
     */
    public void menuNav() {
        for(int i = 0; i < menu.size(); i++){
            items.add(menu.getItem(i));
        }
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                int pos = items.indexOf(item);
                if(pos == 0){
                    finish();
                }else if(pos == 1){
                    Intent i = new Intent(agregar_materia.this, Materias.class);
                    startActivity(i);
                    finish();
                }else if(pos == 2){
                    Intent i = new Intent(agregar_materia.this, completadas.class);
                    startActivity(i);
                    finish();
                }else if(pos == 3) {
                    Intent i = new Intent(agregar_materia.this, horario.class);
                    startActivity(i);
                    finish();
                }else if(pos == 4){
                    Intent i = new Intent(agregar_materia.this, agregarTarea.class);
                    startActivity(i);
                    finish();
                }else if(pos == 5){

                }else if(pos == 6){
                    Intent i = new Intent(agregar_materia.this, acerca.class);
                    startActivity(i);
                    finish();
                }
                drawerLayout.closeDrawer(nav);
                item.setChecked(false);
                return false;
            }
        });
        btnMenu = (ImageView)findViewById(R.id.Btnmenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(nav);
            }
        });
    }

    public void llenarColores(){
        ArrayList<ObjColor> colores = db.selectColores();
        String[] c = new String[colores.size()];
        for(int i = 0; i < colores.size(); i++){
            c[i] = colores.get(i).getExadecimal();
        }
        spinner_colores.setAdapter(new SpinnerAdaptador(getApplicationContext(), c));
    }
}
