package com.claresti.mistareas.gestordetareas;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class horario extends AppCompatActivity {

    private AdminBD db; //Variable del administrador de la base de datos
    private ObjUsuario usuario;
    //Menu, Declaracion de variables
    private DrawerLayout drawerLayout;
    final List<MenuItem> items = new ArrayList<>();
    private Menu menu;
    private ImageView btnMenu;
    private NavigationView nav;
    //Fin menu, declaracion de variables
    private Spinner spinnerDias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_horario);
        //Cambiar el color en la barra de notificaciones (Solo funciona de lollipop hacia arriba)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.horario));
        }

        //Codigo para crear el objeto de la base de datos y
        //agregar el nombre de usuario al menu
        db = new AdminBD(this);
        usuario = db.selectUsuario();

        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        menuNav();

        //Codigo para poner en el Menu el nombre de usuario
        View header = nav.getHeaderView(0);
        TextView nombreUsuario = (TextView) header.findViewById(R.id.menuNombreUsuario);
        nombreUsuario.setText(usuario.getNombre());
        //Fin Codigo para poner el nombre de usuario en el menu
        cargarHorarios();
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
                    Intent i = new Intent(horario.this, Materias.class);
                    startActivity(i);
                    finish();
                }else if(pos == 2){
                    Intent i = new Intent(horario.this, completadas.class);
                    startActivity(i);
                    finish();
                }else if(pos == 3) {

                }else if(pos == 4){
                    Intent i = new Intent(horario.this, agregarTarea.class);
                    startActivity(i);
                    finish();
                }else if(pos == 5){
                    Intent i = new Intent(horario.this, agregar_materia.class);
                    startActivity(i);
                    finish();
                }else if(pos == 6){
                    Intent i = new Intent(horario.this, acerca.class);
                    startActivity(i);
                    finish();
                }
                drawerLayout.closeDrawer(nav);
                item.setChecked(false);
                return false;
            }
        });
        //Bloque de codigo que da funcionalidad al boton de editar del header del menu
        View headerview = nav.getHeaderView(0);
        ImageView editar = (ImageView)headerview.findViewById(R.id.editar);
        RelativeLayout imgFondo = (RelativeLayout)headerview.findViewById(R.id.l_imgFondo);
        if(usuario.getImg().equals("imgmenu")){
            imgFondo.setBackgroundResource(R.drawable.imgmenu);
        }else{
            //Uri path = Uri.fromFile(new File(usuario.getImg()));
            Bitmap bitmap = BitmapFactory.decodeFile(usuario.getImg());
            BitmapDrawable bdrawable = new BitmapDrawable(getApplicationContext().getResources(),bitmap);
            imgFondo.setBackground(bdrawable);
        }
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(horario.this, EditarMenu.class);
                startActivity(i);
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

    public void cargarHorarios(){
        String[] diasSemana = {"Seleccione un dia" ,"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        final ArrayList<ObjHorario> horarios = db.selectHorarios();
        final ArrayList<ObjHorario> lunes = new ArrayList<ObjHorario>();
        final ArrayList<ObjHorario> martes = new ArrayList<ObjHorario>();
        final ArrayList<ObjHorario> miercoles = new ArrayList<ObjHorario>();
        final ArrayList<ObjHorario> jueves = new ArrayList<ObjHorario>();
        final ArrayList<ObjHorario> viernes = new ArrayList<ObjHorario>();
        final ArrayList<ObjHorario> sabado = new ArrayList<ObjHorario>();
        for(ObjHorario h : horarios){
            if(h.getDia() != null) {
                if (h.getDia().equals("Lunes")) {
                    lunes.add(h);
                }
                if (h.getDia().equals("Martes")) {
                    martes.add(h);
                }
                if (h.getDia().equals("Miercoles")) {
                    miercoles.add(h);
                }
                if (h.getDia().equals("Jueves")) {
                    jueves.add(h);
                }
                if (h.getDia().equals("Viernes")) {
                    viernes.add(h);
                }
                if (h.getDia().equals("Sabado")) {
                    sabado.add(h);
                }
            }
        }
        spinnerDias = (Spinner)findViewById(R.id.dia);
        ArrayAdapter adaptadorDias = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, diasSemana);
        spinnerDias.setAdapter(adaptadorDias);
        Calendar ahora = Calendar.getInstance();
        final String diaHoy = diasSemana[ahora.get(Calendar.DAY_OF_WEEK) - 1];
        ArrayAdapter miAdap = (ArrayAdapter) spinnerDias.getAdapter();
        int posicionSpinner = miAdap.getPosition(diaHoy);
        spinnerDias.setSelection(posicionSpinner);
        spinnerDias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TableLayout tabla = (TableLayout)findViewById(R.id.horario);
                if(position == 0){
                    limpiarTabla(tabla);
                }else if(position == 1){
                    limpiarTabla(tabla);
                    if(lunes.size() > 0){
                        for(ObjHorario h : lunes){
                            TableRow fila = new TableRow(getApplicationContext());
                            TextView materia = new TextView(getApplicationContext());
                            materia.setText(h.getMateria().getAbv());
                            materia.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            materia.setBackground(getResources().getDrawable(R.drawable.cell));
                            materia.setTextColor(Color.parseColor("#000000"));
                            TextView entrada = new TextView(getApplicationContext());
                            entrada.setText(h.getEntrada());
                            entrada.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            entrada.setBackground(getResources().getDrawable(R.drawable.cell));
                            entrada.setTextColor(Color.parseColor("#000000"));
                            TextView salida = new TextView(getApplicationContext());
                            salida.setText(h.getSalida());
                            salida.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            salida.setBackground(getResources().getDrawable(R.drawable.cell));
                            salida.setTextColor(Color.parseColor("#000000"));
                            TextView salon = new TextView(getApplicationContext());
                            salon.setText(h.getSalon());
                            salon.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);                            salon.setBackground(getResources().getDrawable(R.drawable.cell));
                            salon.setTextColor(Color.parseColor("#000000"));
                            fila.addView(materia);
                            fila.addView(entrada);
                            fila.addView(salida);
                            fila.addView(salon);
                            tabla.addView(fila);
                        }
                    }
                }else if(position == 2){
                    limpiarTabla(tabla);
                    if(martes.size() > 0){
                        for(ObjHorario h : martes){
                            TableRow fila = new TableRow(getApplicationContext());
                            TextView materia = new TextView(getApplicationContext());
                            materia.setText(h.getMateria().getAbv());
                            materia.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            materia.setBackground(getResources().getDrawable(R.drawable.cell));
                            materia.setTextColor(Color.parseColor("#000000"));
                            TextView entrada = new TextView(getApplicationContext());
                            entrada.setText(h.getEntrada());
                            entrada.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            entrada.setBackground(getResources().getDrawable(R.drawable.cell));
                            entrada.setTextColor(Color.parseColor("#000000"));
                            TextView salida = new TextView(getApplicationContext());
                            salida.setText(h.getSalida());
                            salida.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            salida.setBackground(getResources().getDrawable(R.drawable.cell));
                            salida.setTextColor(Color.parseColor("#000000"));
                            TextView salon = new TextView(getApplicationContext());
                            salon.setText(h.getSalon());
                            salon.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            salon.setBackground(getResources().getDrawable(R.drawable.cell));
                            salon.setTextColor(Color.parseColor("#000000"));
                            fila.addView(materia);
                            fila.addView(entrada);
                            fila.addView(salida);
                            fila.addView(salon);
                            tabla.addView(fila);
                        }
                    }
                }else if(position == 3){
                    limpiarTabla(tabla);
                    if(miercoles.size() > 0){
                        for(ObjHorario h : miercoles){
                            TableRow fila = new TableRow(getApplicationContext());
                            TextView materia = new TextView(getApplicationContext());
                            materia.setText(h.getMateria().getAbv());
                            materia.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            materia.setBackground(getResources().getDrawable(R.drawable.cell));
                            materia.setTextColor(Color.parseColor("#000000"));
                            TextView entrada = new TextView(getApplicationContext());
                            entrada.setText(h.getEntrada());
                            entrada.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            entrada.setBackground(getResources().getDrawable(R.drawable.cell));
                            entrada.setTextColor(Color.parseColor("#000000"));
                            TextView salida = new TextView(getApplicationContext());
                            salida.setText(h.getSalida());
                            salida.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            salida.setBackground(getResources().getDrawable(R.drawable.cell));
                            salida.setTextColor(Color.parseColor("#000000"));
                            TextView salon = new TextView(getApplicationContext());
                            salon.setText(h.getSalon());
                            salon.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            salon.setBackground(getResources().getDrawable(R.drawable.cell));
                            salon.setTextColor(Color.parseColor("#000000"));
                            fila.addView(materia);
                            fila.addView(entrada);
                            fila.addView(salida);
                            fila.addView(salon);
                            tabla.addView(fila);
                        }
                    }
                }else if(position == 4){
                    limpiarTabla(tabla);
                    if(jueves.size() > 0){
                        for(ObjHorario h : jueves){
                            TableRow fila = new TableRow(getApplicationContext());
                            TextView materia = new TextView(getApplicationContext());
                            materia.setText(h.getMateria().getAbv());
                            materia.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            materia.setBackground(getResources().getDrawable(R.drawable.cell));
                            materia.setTextColor(Color.parseColor("#000000"));
                            TextView entrada = new TextView(getApplicationContext());
                            entrada.setText(h.getEntrada());
                            entrada.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            entrada.setBackground(getResources().getDrawable(R.drawable.cell));
                            entrada.setTextColor(Color.parseColor("#000000"));
                            TextView salida = new TextView(getApplicationContext());
                            salida.setText(h.getSalida());
                            salida.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            salida.setBackground(getResources().getDrawable(R.drawable.cell));
                            salida.setTextColor(Color.parseColor("#000000"));
                            TextView salon = new TextView(getApplicationContext());
                            salon.setText(h.getSalon());
                            salon.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            salon.setBackground(getResources().getDrawable(R.drawable.cell));
                            salon.setTextColor(Color.parseColor("#000000"));
                            fila.addView(materia);
                            fila.addView(entrada);
                            fila.addView(salida);
                            fila.addView(salon);
                            tabla.addView(fila);
                        }
                    }
                }else if(position == 5){
                    limpiarTabla(tabla);
                    if(viernes.size() > 0){
                        for(ObjHorario h : viernes){
                            TableRow fila = new TableRow(getApplicationContext());
                            TextView materia = new TextView(getApplicationContext());
                            materia.setText(h.getMateria().getAbv());
                            materia.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            materia.setBackground(getResources().getDrawable(R.drawable.cell));
                            materia.setTextColor(Color.parseColor("#000000"));
                            TextView entrada = new TextView(getApplicationContext());
                            entrada.setText(h.getEntrada());
                            entrada.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            entrada.setBackground(getResources().getDrawable(R.drawable.cell));
                            entrada.setTextColor(Color.parseColor("#000000"));
                            TextView salida = new TextView(getApplicationContext());
                            salida.setText(h.getSalida());
                            salida.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            salida.setBackground(getResources().getDrawable(R.drawable.cell));
                            salida.setTextColor(Color.parseColor("#000000"));
                            TextView salon = new TextView(getApplicationContext());
                            salon.setText(h.getSalon());
                            salon.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            salon.setBackground(getResources().getDrawable(R.drawable.cell));
                            salon.setTextColor(Color.parseColor("#000000"));
                            fila.addView(materia);
                            fila.addView(entrada);
                            fila.addView(salida);
                            fila.addView(salon);
                            tabla.addView(fila);
                        }
                    }
                }else if(position == 6){
                    limpiarTabla(tabla);
                    if(sabado.size() > 0){
                        for(ObjHorario h : sabado){
                            TableRow fila = new TableRow(getApplicationContext());
                            TextView materia = new TextView(getApplicationContext());
                            materia.setText(h.getMateria().getAbv());
                            materia.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            materia.setBackground(getResources().getDrawable(R.drawable.cell));
                            materia.setTextColor(Color.parseColor("#000000"));
                            TextView entrada = new TextView(getApplicationContext());
                            entrada.setText(h.getEntrada());
                            entrada.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            entrada.setBackground(getResources().getDrawable(R.drawable.cell));
                            entrada.setTextColor(Color.parseColor("#000000"));
                            TextView salida = new TextView(getApplicationContext());
                            salida.setText(h.getSalida());
                            salida.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            salida.setBackground(getResources().getDrawable(R.drawable.cell));
                            salida.setTextColor(Color.parseColor("#000000"));
                            TextView salon = new TextView(getApplicationContext());
                            salon.setText(h.getSalon());
                            salon.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            salon.setBackground(getResources().getDrawable(R.drawable.cell));
                            salon.setTextColor(Color.parseColor("#000000"));
                            fila.addView(materia);
                            fila.addView(entrada);
                            fila.addView(salida);
                            fila.addView(salon);
                            tabla.addView(fila);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void limpiarTabla(TableLayout tabla) {
        if(tabla.getChildCount() > 1){
            for(int i = 1; i < tabla.getChildCount(); i++){
                View hijo = tabla.getChildAt(i);
                if(hijo instanceof TableRow){
                    ((ViewGroup)hijo).removeAllViews();
                }
            }
        }
    }
}
