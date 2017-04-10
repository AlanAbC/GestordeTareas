package com.example.alan.gestordetareas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AdminBD db; //Variable del administrador de la base de datos
    private FloatingActionButton agregar; //Variable del boton agregar
    //Menu, Declaracion de variables
    private DrawerLayout drawerLayout;
    final List<MenuItem> items = new ArrayList<>();
    private Menu menu;
    private ImageView btnMenu;
    private NavigationView nav;
    //Fin menu, declaracion de variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        //Cambiar el color en la barra de notificaciones (Solo funciona de lollipop hacia arriba)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
        }
        //Fin cambio de color de barra de notificaciones
        //Inserta un lisener en el boton agregar para cambiar de ventana
        agregar = (FloatingActionButton)findViewById(R.id.agregar);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,agregarTarea.class);
                startActivity(i);
            }
        });
        //FIn insertar listener boton agregar
        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        menuNav();
        // Fin menu
        //Creacion del objeto usuario y comprobacion de primera ves o no en el sistema
        //cambia el nombre de la base de datos y el valor de usuPrimera a 1 en caso de que sea la primera vez
        //en caso contrario solo agrega nombre del usuario al menu y carga las tareas
        db = new AdminBD(this);
        ObjUsuario usuario = db.selectUsuario();
        if(usuario.getPrimera() == 0){
            //Creacion de la ventana de inicio
            final RelativeLayout inicio = (RelativeLayout)findViewById(R.id.lPrimeraVez);
            inicio.setVisibility(View.VISIBLE);
            Button actualizar = (Button)findViewById(R.id.actualizarUsuario);
            actualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ObjUsuario usuarioActualizar = new ObjUsuario();
                    EditText usuNombre = (EditText)findViewById(R.id.nombreUsuario);
                    String nombre = usuNombre.getText().toString();
                    usuarioActualizar.setNombre(nombre);
                    int respuesta = db.updateUsuario(usuarioActualizar);
                    if(respuesta == 1){
                        msg("Se a registrado correctamente");
                        inicio.setVisibility(View.GONE);
                        TextView nombreMenuUsuario = (TextView)findViewById(R.id.menuNombreUsuario);
                        nombreMenuUsuario.setText(nombre);
                    }else{
                        msg("Ocurrio un error, intente nuevamente");
                    }
                }
            });
        }else {
            //Codigo para poner en el Menu el nombre de usuario
            View header = nav.getHeaderView(0);
            TextView nombreUsuario = (TextView) header.findViewById(R.id.menuNombreUsuario);
            nombreUsuario.setText(usuario.getNombre());
            //Fin Codigo para poner el nombre de usuario en el menu
            cargarTareas(); //LLamada a funcion para llenar las tareas
        }
        //FIn creacion y comprobacion de primera vez
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

                }else if(pos == 1){
                    Intent i = new Intent(MainActivity.this, Materias.class);
                    startActivity(i);
                }else if(pos == 2){
                    Intent i = new Intent(MainActivity.this, completadas.class);
                    startActivity(i);
                }else if(pos == 3) {
                    Intent i = new Intent(MainActivity.this, horario.class);
                    startActivity(i);
                }else if(pos == 4){
                    Intent i = new Intent(MainActivity.this, agregarTarea.class);
                    startActivity(i);
                }else if(pos == 5){
                    Intent i = new Intent(MainActivity.this, agregar_materia.class);
                    startActivity(i);
                }else if(pos == 6){
                    Intent i = new Intent(MainActivity.this, acerca.class);
                    startActivity(i);
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

    /**
     * Funcion para cambiar de ventana a la ventada de agregar tarea
     * @param view
     */
    public void agregarTarea(View view){
        Intent i = new Intent(MainActivity.this, agregarTarea.class);
        startActivity(i);
    }

    /**
     * Funcion para llenar los listview con las tareas pendientes
     */
    public void cargarTareas(){

    }

    /**
     * Funcion para crear un mensaje en pantalla
     * @param msg
     */
    public void msg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
