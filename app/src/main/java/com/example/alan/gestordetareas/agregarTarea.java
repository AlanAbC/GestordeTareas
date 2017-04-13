package com.example.alan.gestordetareas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class agregarTarea extends AppCompatActivity {

    private AdminBD db; //Variable del administrador de la base de datos
    //Menu, Declaracion de variables
    private DrawerLayout drawerLayout;
    final List<MenuItem> items = new ArrayList<>();
    private Menu menu;
    private ImageView btnMenu;
    private NavigationView nav;
    //Fin menu, declaracion de variables
    private EditText nombre;
    private Spinner spinnerMaterias;
    private DatePicker fechaEntrega;
    private EditText descripcion;
    private FloatingActionButton agregar;
    private int materiaSeleccionada = 0;
    private ArrayList<ObjMateria> materias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_agregar_tarea);
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
        spinnerMaterias  =(Spinner)findViewById(R.id.materiaS);
        fechaEntrega = (DatePicker)findViewById(R.id.fechaS);
        descripcion = (EditText)findViewById(R.id.descripcionT);
        llenarMaterias();
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
                    Intent i = new Intent(agregarTarea.this, Materias.class);
                    startActivity(i);
                    finish();
                }else if(pos == 2){
                    Intent i = new Intent(agregarTarea.this, completadas.class);
                    startActivity(i);
                    finish();
                }else if(pos == 3) {
                    Intent i = new Intent(agregarTarea.this, horario.class);
                    startActivity(i);
                    finish();
                }else if(pos == 4){

                }else if(pos == 5){
                    Intent i = new Intent(agregarTarea.this, agregar_materia.class);
                    startActivity(i);
                    finish();
                }else if(pos == 6){
                    Intent i = new Intent(agregarTarea.this, acerca.class);
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

    public void llenarMaterias(){
        materias = db.selectMaterias();
        if(materias.size() > 0){
            String[] nombreMaterias = new String[materias.size()];
            for(int i = 0; i < materias.size(); i++){
                nombreMaterias[i] = materias.get(i).getNombre();
            }
            ArrayAdapter adaptadorMaterias = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, nombreMaterias);
            spinnerMaterias.setAdapter(adaptadorMaterias);

            spinnerMaterias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    materiaSeleccionada = position;
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public void msg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void agregarTarea(View view){
        if(materias.size() > 0) {
            if (!nombre.getText().toString().equals("")) {
                String f = fechaEntrega.getYear() + "/" + (fechaEntrega.getMonth() + 1) + "/" + fechaEntrega.getDayOfMonth() + " 23:59";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                Date fe = sdf.parse(f, new ParsePosition(0));
                Date feAc = new Date();
                if (fe.after(feAc)) {
                    if (!descripcion.getText().toString().equals("")) {
                        ObjTarea tareaNueva = new ObjTarea();
                        tareaNueva.setMateria(materias.get(materiaSeleccionada));
                        tareaNueva.setCompletado(0);
                        tareaNueva.setDescripcion(descripcion.getText().toString());
                        tareaNueva.setFechaCreacion(feAc);
                        tareaNueva.setFechaEntrega(fe);
                        tareaNueva.setNombre(nombre.getText().toString());
                        int flag = db.insertTarea(tareaNueva);
                        if(flag == 1){
                            msg("Tarea creada correctamente :)");
                            Intent i = new Intent(agregarTarea.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            msg("Ocurrio un erro vuelva a intentar");
                        }
                    } else {
                        msg("Ingresa una descripcion de la tarea");
                    }
                } else {
                    msg("No puedes ingresar una fecha que ya paso");
                }
            } else {
                msg("Ingres un nombre a la tarea");
            }
        }else{
            msg("No puedes agregar una tarea porque no tienes materias");
        }
    }
}
